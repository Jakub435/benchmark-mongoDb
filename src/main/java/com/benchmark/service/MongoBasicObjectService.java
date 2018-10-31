package com.benchmark.service;

import com.mongodb.*;
import org.springframework.stereotype.Service;

@Service
public class MongoBasicObjectService {
    private MongoClient mongo;
    private DB database;
    private DBCollection collection;

    public MongoBasicObjectService(){
        mongo = new MongoClient("localhost",27017);
        database = mongo.getDB("bench");
        collection = database.getCollection("spatial_MultiPoint");
    }

    public double getMultiPointReadTime(String name){
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("name", name);

        long before = System.currentTimeMillis();
        DBCursor cursor = collection.find(searchQuery);
        long after = System.currentTimeMillis();

        System.out.println("===== WYNIKI =====");
        while (cursor.hasNext()){
            System.out.println(cursor.next());
        }

        return (double)(after - before);
    }

}
