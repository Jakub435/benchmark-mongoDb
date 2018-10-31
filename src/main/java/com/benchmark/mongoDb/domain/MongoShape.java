package com.benchmark.mongoDb.domain;

import com.benchmark.model.CoordinateWrapper;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "shape")
public class MongoShape {

    @BsonId
    private ObjectId id;

    private String name;

    private CoordinateWrapper coordinates;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public MongoShape(String name, CoordinateWrapper coordinates) {
        this.name = name;
        this.coordinates = coordinates;
    }
    public MongoShape(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CoordinateWrapper getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(CoordinateWrapper coordinates) {
        this.coordinates = coordinates;
    }
}
