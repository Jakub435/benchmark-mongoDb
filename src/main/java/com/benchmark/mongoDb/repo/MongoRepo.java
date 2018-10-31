package com.benchmark.mongoDb.repo;

import com.benchmark.mongoDb.domain.MongoShape;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoRepo extends MongoRepository<MongoShape, Long>, MongoInterface<MongoShape> {
    boolean existsByName(String name);
}