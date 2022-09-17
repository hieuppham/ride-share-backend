package vn.rideshare.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Box;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.UpdateDefinition;
import org.springframework.stereotype.Repository;
import vn.rideshare.client.dto.FindByIdRequest;
import vn.rideshare.client.dto.ride.*;
import vn.rideshare.model.EntityStatus;
import vn.rideshare.model.Ride;
import vn.rideshare.model.User;
import vn.rideshare.repository.RideCustomRepository;
import vn.rideshare.shared.CustomMongoTemplate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RideCustomRepositoryImpl implements RideCustomRepository {
    private static final String STATUS = "status";
    private static final String START_TIME = "startTime";
    private static final String END_TIME = "endTime";
    private static final String LAST_MODIFIED_DATE = "lastModifiedDate";
    private static final String USER_ID = "userId";
    private static final String CLASS = "_class";
    private static final String ROUTE = "route";
    private static final String VEHICLE_ID = "vehicleId";
    private static final long PENDING_TIME = 2l;
    @Autowired
    private CustomMongoTemplate mongoTemplate;

    @Override
    public List<FindRidesResponse> findRidesByBound(FindRidesByBoundRequest request) {
        List<AggregationOperation> aggregations = new ArrayList<>();

        Criteria criteria = new Criteria();
        criteria.orOperator(
                Criteria.where(STATUS).is(EntityStatus.PREPARE),
                Criteria.where(STATUS).is(EntityStatus.ACTIVE),
                Criteria.where(STATUS).is(EntityStatus.EXPIRED));
        criteria.and("path.geometry.coordinates")
                .within(new Box(
                        toPoint(request.getUpperRight()),
                        toPoint(request.getBottomLeft())

                ));
        MatchOperation match = new MatchOperation(criteria);
        aggregations.add(match);

        AggregationOperation project = new ProjectionOperation().andExclude(
                "uid",
                ROUTE,
                VEHICLE_ID,
                CLASS);
        aggregations.add(project);
        return mongoTemplate.aggregate(new TypedAggregation<>(Ride.class, aggregations), FindRidesResponse.class)
                .getMappedResults().stream().map(this::fillMissingFields).collect(Collectors.toList());
    }

    @Override
    public List<FindRidesResponse> findAllRides() {
        List<AggregationOperation> aggregations = new ArrayList<>();

        Criteria criteria = new Criteria();
        criteria.orOperator(Criteria.where(STATUS).is(EntityStatus.PREPARE),
                Criteria.where(STATUS).is(EntityStatus.ACTIVE),
                Criteria.where(STATUS).is(EntityStatus.EXPIRED));
        MatchOperation match = new MatchOperation(criteria);
        aggregations.add(match);

        AggregationOperation project = new ProjectionOperation().andExclude(
                USER_ID,
                ROUTE,
                VEHICLE_ID,
                CLASS);
        aggregations.add(project);
        return mongoTemplate.aggregate(new TypedAggregation<>(Ride.class, aggregations), FindRidesResponse.class)
                .getMappedResults().stream().map(this::fillMissingFields).collect(Collectors.toList());
    }

    @Override
    public List<FindRideDetailResponse> findRidesByUserId(String id) {
        List<AggregationOperation> aggregations = new ArrayList<>();

        Criteria criteria = new Criteria();
        criteria.and(USER_ID).is(id);
        MatchOperation match = new MatchOperation(criteria);
        aggregations.add(match);

        AggregationOperation project = new ProjectionOperation().andExclude(
                USER_ID,
                ROUTE,
                VEHICLE_ID,
                CLASS);
        aggregations.add(project);
        return mongoTemplate.aggregate(new TypedAggregation<>(Ride.class, aggregations), FindRideDetailResponse.class)
                .getMappedResults().stream().map(this::fillMissingFieldsDetail).collect(Collectors.toList());
    }

    @Override
    public FindRidesResponse findSingleRideById(FindByIdRequest request) {
        List<AggregationOperation> aggregations = new ArrayList<>();

        Criteria criteria = new Criteria("_id").is(request.getId()).and(END_TIME).gt(LocalDateTime.now());
        MatchOperation match = new MatchOperation(criteria);
        aggregations.add(match);

        AggregationOperation project = new ProjectionOperation().andExclude(
                USER_ID,
                ROUTE,
                VEHICLE_ID,
                CLASS);
        aggregations.add(project);
        return mongoTemplate.aggregate(new TypedAggregation<>(Ride.class, aggregations), FindRidesResponse.class)
                .getMappedResults().stream().map(this::fillMissingFields).collect(Collectors.toList()).get(0);
    }

    @Override
    public Ride existOneActiveRideByUserId(String id) {
        return mongoTemplate.findOne(Query.query(
                        Criteria.where(USER_ID).is(id)
                                .and(STATUS).is(EntityStatus.ACTIVE)
                                .and(END_TIME).gt(LocalDateTime.now())),
                Ride.class);
    }

    @Override
    public void findAndActivate() {
        Criteria criteria = Criteria
                .where(STATUS).is(EntityStatus.PREPARE)
                .and(START_TIME).lte(Instant.now());
        Query query = Query.query(criteria);
        UpdateDefinition update = Update.update(STATUS, EntityStatus.ACTIVE).set(LAST_MODIFIED_DATE, Instant.now());
        mongoTemplate.findAndModify(query, update, Ride.class);
    }

    @Override
    public void findAndPrepare() {
        Criteria criteria = Criteria
                .where(STATUS).is(EntityStatus.PENDING)
                .and(LAST_MODIFIED_DATE).lte(Instant.now().minusSeconds(PENDING_TIME * 60));
        Query query = Query.query(criteria);
        UpdateDefinition update = Update.update(STATUS, EntityStatus.PREPARE).set(LAST_MODIFIED_DATE, Instant.now());
        mongoTemplate.findAndModify(query, update, Ride.class);
    }

    @Override
    public void findAndExpire() {
        Query query = Query.query(Criteria.where(STATUS).is(EntityStatus.ACTIVE)
                .and(END_TIME).lte(LocalDateTime.now()));
        UpdateDefinition update = Update.update(STATUS, EntityStatus.EXPIRED).set(LAST_MODIFIED_DATE, Instant.now());
        mongoTemplate.findAndModify(query, update, Ride.class);
    }

    @Override
    public void findAndDisable() {
        Query query = Query.query(
                Criteria
                        .where(STATUS).is(EntityStatus.EXPIRED)
                        .and(END_TIME).lte(LocalDateTime.now().minusMinutes(PENDING_TIME)));
        UpdateDefinition update = Update.update(STATUS, EntityStatus.DISABLE).set(LAST_MODIFIED_DATE, Instant.now());
        mongoTemplate.findAndModify(query, update, Ride.class);
    }

    private Point toPoint(List<Double> coordinates) {
        return new Point(coordinates.get(0), coordinates.get(1));
    }

    private FindRideDetailResponse fillMissingFieldsDetail(FindRideDetailResponse ride) {
        Ride var1 = mongoTemplate.findById(ride.getId(), Ride.class);
        User var2 = mongoTemplate.findById(var1.getUserId(), User.class);
        ride.setDistance(var1.getRoute().getDistance());
        ride.setStartPointTitle(var1.getPath().getProperties().getStartPointTitle());
        ride.setEndPointTitle(var1.getPath().getProperties().getEndPointTitle());
        ride.setVehicle(var2.getVehicles()
                .stream()
                .filter(v -> v.getId() == var1.getVehicleId())
                .map(v -> {
                    VehicleDto dto = new VehicleDto();
                    dto.setType(v.getType());
                    return dto;
                }).collect(Collectors.toList()).get(0));
        return ride;
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
