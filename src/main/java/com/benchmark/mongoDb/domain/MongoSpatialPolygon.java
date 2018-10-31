package com.benchmark.mongoDb.domain;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "spatial_Polygon")
public class MongoSpatialPolygon {
    @BsonId
    private ObjectId id;
    private String name;
    private GeoJsonPolygon polygon;

    public MongoSpatialPolygon(String name, GeoJsonPolygon polygon) {
        this.name = name;
        this.polygon = polygon;
    }

    public MongoSpatialPolygon() {}

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GeoJsonPolygon getPolygon() {
        return polygon;
    }

    public void setPolygon(GeoJsonPolygon polygon) {
        this.polygon = polygon;
    }
}
