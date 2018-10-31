package com.benchmark.mongoDb.repo;

import com.benchmark.model.NameResponse;
import com.benchmark.mongoDb.domain.MongoSpatialMultiPoint;
import org.bson.BsonObjectId;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MongoMultiPointRepo extends MongoRepository<MongoSpatialMultiPoint, BsonObjectId>, MongoInterface<MongoSpatialMultiPoint> {
    List<MongoSpatialMultiPoint> findByMultiPointNear(Point p, Distance d);

    @Query(value="{}", fields="{ 'name' : 1}")
    List<NameResponse> findAllName();
}
