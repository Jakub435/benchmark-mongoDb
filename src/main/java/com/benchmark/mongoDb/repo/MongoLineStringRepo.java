package com.benchmark.mongoDb.repo;

import com.benchmark.mongoDb.domain.MongoSpatialLineString;
import org.bson.BsonObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoLineStringRepo extends MongoRepository<MongoSpatialLineString, BsonObjectId>,MongoInterface<MongoSpatialLineString> {
}
