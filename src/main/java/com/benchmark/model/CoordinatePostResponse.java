package com.benchmark.model;

public class CoordinatePostResponse {
    private double MongoDb;
    private double mongoSpatialMultiPoint;
    private double mongoSpatialPolygon;
    private double mongoSpatialLineString;

    public CoordinatePostResponse(double mongoDb, double mongoSpatialMultiPoint, double mongoSpatialPolygon, double mongoSpatialLineString) {
        MongoDb = mongoDb;
        this.mongoSpatialMultiPoint = mongoSpatialMultiPoint;
        this.mongoSpatialPolygon = mongoSpatialPolygon;
        this.mongoSpatialLineString = mongoSpatialLineString;
    }

    public CoordinatePostResponse(){}

    public double getMongoDb() {
        return MongoDb;
    }

    public void setMongoDb(double mongoDb) {
        MongoDb = mongoDb;
    }

    public double getMongoSpatialMultiPoint() {
        return mongoSpatialMultiPoint;
    }

    public void setMongoSpatialMultiPoint(double mongoSpatialMultiPoint) {
        this.mongoSpatialMultiPoint = mongoSpatialMultiPoint;
    }

    public double getMongoSpatialPolygon() {
        return mongoSpatialPolygon;
    }

    public void setMongoSpatialPolygon(double mongoSpatialPolygon) {
        this.mongoSpatialPolygon = mongoSpatialPolygon;
    }

    public double getMongoSpatialLineString() {
        return mongoSpatialLineString;
    }

    public void setMongoSpatialLineString(double mongoSpatialLineString) {
        this.mongoSpatialLineString = mongoSpatialLineString;
    }
}
