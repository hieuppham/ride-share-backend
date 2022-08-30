package vn.rideshare.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Box;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import vn.rideshare.client.dto.ride.*;
import vn.rideshare.model.EntityStatus;
import vn.rideshare.model.Ride;
import vn.rideshare.model.User;
import vn.rideshare.repository.RideCustomRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RideCustomRepositoryImpl implements RideCustomRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<FindRidesResponse> findRidesByBound(FindRidesByBoundRequest request) {
        List<AggregationOperation> aggregations = new ArrayList<>();

        Criteria criteria = new Criteria();
        criteria.and("status").is(EntityStatus.ACTIVE);
        criteria.and("path.geometry.coordinates")
                .within(new Box(
                        toPoint(request.getUpperRight()),
                        toPoint(request.getBottomLeft())

                ));
        MatchOperation match = new MatchOperation(criteria);
        aggregations.add(match);

        AggregationOperation project = new ProjectionOperation().andExclude(
                "uid",
                "route",
                "vehicleId",
                "_class");
        aggregations.add(project);
        return mongoTemplate.aggregate(new TypedAggregation<>(Ride.class, aggregations), FindRidesResponse.class)
                .getMappedResults().stream().map(ride -> fillMissingFields(ride)).collect(Collectors.toList());
    }

    @Override
    public List<FindRidesResponse> findAllRides() {
        List<AggregationOperation> aggregations = new ArrayList<>();

        Criteria criteria = new Criteria();
        criteria.and("status").is(EntityStatus.ACTIVE);
        MatchOperation match = new MatchOperation(criteria);
        aggregations.add(match);

        AggregationOperation project = new ProjectionOperation().andExclude(
                "uid",
                "route",
                "vehicleId",
                "_class");
        aggregations.add(project);
        return mongoTemplate.aggregate(new TypedAggregation<>(Ride.class, aggregations), FindRidesResponse.class)
                .getMappedResults().stream().map(ride -> fillMissingFields(ride)).collect(Collectors.toList());
    }

    @Override
    public List<FindRidesResponse> findRidesByUserId(String id) {
        List<AggregationOperation> aggregations = new ArrayList<>();

        Criteria criteria = new Criteria();
        criteria.and("userId").is(id);
        MatchOperation match = new MatchOperation(criteria);
        aggregations.add(match);

        AggregationOperation project = new ProjectionOperation().andExclude(
                "uid",
                "route",
                "vehicleId",
                "_class");
        aggregations.add(project);
        return mongoTemplate.aggregate(new TypedAggregation<>(Ride.class, aggregations), FindRidesResponse.class)
                .getMappedResults().stream().map(ride -> fillMissingFields(ride)).collect(Collectors.toList());
    }

    private Point toPoint(List<Double> coordinates) {
        return new Point(coordinates.get(0), coordinates.get(1));
    }

    private FindRidesResponse fillMissingFields(FindRidesResponse ride) {
        Ride var1 = mongoTemplate.findById(ride.getId(), Ride.class);
        User var2 = mongoTemplate.findById(var1.getUserId(), User.class);
        ride.setDistance(var1.getRoute().getDistance());
        ride.setFullName(var2.getFullName());
        ride.setPhotoURL(var2.getPhotoURL());
        ride.setVehicleType(var2.getVehicles()
                .stream()
                .filter(v -> v.getId() == var1.getVehicleId()).findFirst().get().getType());
        return ride;
    }
}
