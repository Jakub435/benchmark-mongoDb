package com.benchmark.mongoDb.repo;

import com.benchmark.mongoDb.domain.MongoSpatialPolygon;
import org.bson.BsonObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoPolygonRepo extends MongoRepository<MongoSpatialPolygon, BsonObjectId>, MongoInterface<MongoSpatialPolygon> {
}
