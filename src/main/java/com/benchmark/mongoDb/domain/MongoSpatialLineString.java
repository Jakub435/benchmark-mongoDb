package com.benchmark.mongoDb.domain;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.geo.GeoJsonLineString;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "spatial_LineString")
public class MongoSpatialLineString {
    @BsonId
    private ObjectId id;
    private String name;
    private GeoJsonLineString lineString;

    public MongoSpatialLineString(String name, GeoJsonLineString lineString) {
        this.name = name;
        this.lineString = lineString;
    }

    public MongoSpatialLineString() {}

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

    public GeoJsonLineString getLineString() {
        return lineString;
    }

    public void setLineString(GeoJsonLineString lineString) {
        this.lineString = lineString;
    }
}
