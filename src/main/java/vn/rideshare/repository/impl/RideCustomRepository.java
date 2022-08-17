package vn.rideshare.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Box;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.geo.Shape;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import vn.rideshare.dto.BoundRequestDto;
import vn.rideshare.dto.RideRequestDto;
import vn.rideshare.dto.RideResponeDto;
import vn.rideshare.model.EntityStatus;
import vn.rideshare.model.Ride;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RideCustomRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    private static final Point DEFAULT_POINT = new Point(105.82009120109002, 21.003195984436573);

    private static final vn.rideshare.model.Criteria criteriaGenderOwnerMale = new vn.rideshare.model.Criteria("genderOwner", "male");
    private static final vn.rideshare.model.Criteria criteriaGenderOwnerFemale = new vn.rideshare.model.Criteria("genderOwner", "female");
    private static boolean containGenderOwnerMale;
    private static boolean containGenderOwnerFemale;

    public List<RideResponeDto> getAllRides(Boolean active) {
        List<AggregationOperation> aggregations = new ArrayList<>();

        if (active != null && active.booleanValue() == true) {
            Criteria criteria = new Criteria();
            criteria.and("status").is(EntityStatus.ACTIVE);
            AggregationOperation match = new MatchOperation(criteria);
            aggregations.add(match);
        }

        AggregationOperation lookUp = new LookupOperation.LookupOperationBuilder()
                .from("user")
                .localField("uid")
                .foreignField("uid")
                .as("user");
        aggregations.add(lookUp);

        AggregationOperation setFirstUser = new SetOperation("user",
                ArrayOperators.arrayOf("user").first());
        aggregations.add(setFirstUser);

        AggregationOperation project = new ProjectionOperation().andExclude("uid", "_class");
        aggregations.add(project);

        return this.mongoTemplate.aggregate(new TypedAggregation<>(Ride.class, aggregations), RideResponeDto.class)
                .getMappedResults();
    }

    public List<RideResponeDto> getByBound(BoundRequestDto boundRequestDto) {
        List<AggregationOperation> aggregations = new ArrayList<>();

        Criteria criteria = new Criteria();
        criteria.and("status").is(EntityStatus.ACTIVE);
        criteria.and("path.geometry.coordinates")
                .within(new Box(
                        toPoint(boundRequestDto.getBottomLeft()),
                        toPoint(boundRequestDto.getUpperRight())
                ));

        AggregationOperation match = new MatchOperation(criteria);
        aggregations.add(match);

        AggregationOperation lookUp = new LookupOperation.LookupOperationBuilder()
                .from("user")
                .localField("uid")
                .foreignField("uid")
                .as("user");
        aggregations.add(lookUp);

        AggregationOperation setFirstUser = new SetOperation("user",
                ArrayOperators.arrayOf("user").first());
        aggregations.add(setFirstUser);

        AggregationOperation project = new ProjectionOperation().andExclude("uid", "_class");
        aggregations.add(project);

        return this.mongoTemplate.aggregate(new TypedAggregation<>(Ride.class, aggregations), RideResponeDto.class)
                .getMappedResults();
    }

    public List<RideResponeDto> getRides(RideRequestDto rideRequestDto) {
        Point startPoint = toPoint(rideRequestDto.getStartCoordinates());
        Point endPoint = toPoint(rideRequestDto.getEndCoordinates());
        List<RideResponeDto> satisfiedForStartPoint = getSatisfiedRides(startPoint, rideRequestDto);
        List<RideResponeDto> satisfiedForEndPoint = getSatisfiedRides(endPoint, rideRequestDto);

        List<RideResponeDto> innerJoin = satisfiedForStartPoint
                .stream()
                .filter(start ->
                        satisfiedForEndPoint
                                .stream()
                                .anyMatch(end -> end.equals(start)))
                .collect(Collectors.toList());
        return innerJoin;
    }

    private Point toPoint(List<Object> coordinates) {
        double lng = Double.valueOf(coordinates.get(0).toString());
        double lat = Double.valueOf(coordinates.get(1).toString());
        return new Point(lng, lat);
    }

    private List<RideResponeDto> getSatisfiedRides(Point point, RideRequestDto rideRequestDto) {
        List<AggregationOperation> aggregations = new ArrayList<>();
        Criteria criteria = new Criteria();

        if (rideRequestDto.getCriterions().size() > 0) {
            containGenderOwnerMale = rideRequestDto.getCriterions().remove(criteriaGenderOwnerMale);
            containGenderOwnerFemale = rideRequestDto.getCriterions().remove(criteriaGenderOwnerFemale);
            criteria.and("criterions").all(rideRequestDto.getCriterions());
        }

//        if (rideRequestDto.getStatus() != null) {
//            criteria.and("status").is(rideRequestDto.getStatus());
//        }
        criteria.and("status").is(EntityStatus.ACTIVE);
        if (!rideRequestDto.getVehicleType().equals("both")) {
            criteria.and("vehicle.type").is(rideRequestDto.getVehicleType());
        }

        NearQuery nearQuery = NearQuery.near(point, Metrics.KILOMETERS)
                .spherical(true)
                .query(Query.query(criteria));
        if (rideRequestDto.getMaxDistance() > 0) {
            nearQuery.maxDistance(rideRequestDto.getMaxDistance());
        }
        AggregationOperation geoNear = new GeoNearOperation(nearQuery, "distanceCal")
                .useIndex("path");
        aggregations.add(geoNear);

        AggregationOperation lookUp = new LookupOperation.LookupOperationBuilder()
                .from("user")
                .localField("uid")
                .foreignField("uid")
                .as("user");
        aggregations.add(lookUp);

        AggregationOperation setFirstUser = new SetOperation("user",
                ArrayOperators.arrayOf("user").first());
        aggregations.add(setFirstUser);

        AggregationOperation sortByDistance = new SortOperation(Sort.by("distanceCal"));
        aggregations.add(sortByDistance);

        AggregationOperation project = new ProjectionOperation().andExclude("uid", "_class", "distanceCal");
        aggregations.add(project);

        if (containGenderOwnerMale || containGenderOwnerFemale) {
            AggregationOperation matchGenderOwner = new MatchOperation(
                    Criteria
                            .where("user.gender")
                            .is(containGenderOwnerMale ? "male" : "female"));
            aggregations.add(matchGenderOwner);
        }

        return this.mongoTemplate.aggregate(new TypedAggregation<>(Ride.class, aggregations), RideResponeDto.class)
                .getMappedResults();
    }

}
