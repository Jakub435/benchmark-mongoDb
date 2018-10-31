package com.benchmark.mongoDb.repo;

public interface MongoInterface<T> {
    T findFirstByName(String name);
}
