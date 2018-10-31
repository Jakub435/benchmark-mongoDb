package com.benchmark.service;

import com.benchmark.model.Coordinate;
import com.benchmark.model.CoordinateGetResponse;
import com.benchmark.model.CoordinateWrapper;
import com.benchmark.model.NameResponse;
import com.benchmark.mongoDb.domain.MongoShape;
import com.benchmark.mongoDb.domain.MongoSpatialLineString;
import com.benchmark.mongoDb.domain.MongoSpatialMultiPoint;
import com.benchmark.mongoDb.domain.MongoSpatialPolygon;
import com.benchmark.mongoDb.repo.MongoLineStringRepo;
import com.benchmark.mongoDb.repo.MongoPolygonRepo;
import com.benchmark.mongoDb.repo.MongoRepo;
import com.benchmark.mongoDb.repo.MongoMultiPointRepo;
import com.mongodb.client.model.geojson.LineString;
import com.mongodb.client.model.geojson.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonLineString;
import org.springframework.data.mongodb.core.geo.GeoJsonMultiPoint;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MongoService {
    @Autowired
    private MongoRepo mongoRepo;

    @Autowired
    private MongoMultiPointRepo mongoMultiPointRepo;

    @Autowired
    private MongoPolygonRepo mongoPolygonRepo;

    @Autowired
    private MongoLineStringRepo mongoLineStringRepo;

    public void clear(){
        mongoRepo.deleteAll();
        mongoPolygonRepo.deleteAll();
        mongoLineStringRepo.deleteAll();
        mongoMultiPointRepo.deleteAll();
    }

    public String checkIfExistAndReturnNewName(String name){
        if(mongoRepo.existsByName(name)) {
            return name + System.currentTimeMillis();
        }

        return name;
    }

    public List<NameResponse> getAllName(){
        return mongoMultiPointRepo.findAllName();
    }

    public CoordinateGetResponse getReadTime(String name){
        CoordinateGetResponse response = new CoordinateGetResponse();

        long before = System.currentTimeMillis();

        MongoShape mongoShape = mongoRepo.findFirstByName(name);

        long after = System.currentTimeMillis();
        double time = (double)(after-before);

        response.setCoordinates(mongoShape.getCoordinates());
        response.setMongoDb(time);

        return response;
    }

    public double getSpatialMultiPointReadTime(String name){

        long before = System.currentTimeMillis();
        mongoMultiPointRepo.findFirstByName(name);
        long after = System.currentTimeMillis();

        double time = (double)(after-before);

        return time;
    }

    public  double getSpatialPolygonReadTime(String name){
        long before = System.currentTimeMillis();
        mongoPolygonRepo.findFirstByName(name);
        long after = System.currentTimeMillis();
        return (double)(after-before);
    }

    public  double getSpatialLineStringReadTime(String name){
        long before = System.currentTimeMillis();
        mongoPolygonRepo.findFirstByName(name);
        long after = System.currentTimeMillis();
        return (double)(after-before);
    }

    public double getSaveTime(String name, CoordinateWrapper coordinates){
        MongoShape mongoShape = new MongoShape(name,coordinates);
        long before = System.currentTimeMillis();
        mongoRepo.save(mongoShape);
        long after = System.currentTimeMillis();

        double time = (double)(after-before);
        return time;
    }

    public double getMultiPointSaveTime(String name, CoordinateWrapper coordinates){
        List<Point> pointList = coordinateToPointList(coordinates);
        MongoSpatialMultiPoint spatialShape = new MongoSpatialMultiPoint(name, new GeoJsonMultiPoint(pointList));

        long before = System.currentTimeMillis();
        mongoMultiPointRepo.save(spatialShape);
        long after = System.currentTimeMillis();
        double time = (double)(after-before);

        return time;
    }

    public  double getLineStringSaveTime(String name, CoordinateWrapper coordinates){
        List<Point> pointList = coordinateToPointList(coordinates);
        MongoSpatialLineString lineStringShape = new MongoSpatialLineString(name, new GeoJsonLineString(pointList));

        long before = System.currentTimeMillis();
        mongoLineStringRepo.save(lineStringShape);
        long after = System.currentTimeMillis();

        return (double)(after-before);
    }

    public double getPolygonSaveTime(String name, CoordinateWrapper coordinates){
        GeoJsonPolygon polygon = new GeoJsonPolygon(coordinateToPointList(coordinates));
        MongoSpatialPolygon polygonShape = new MongoSpatialPolygon(name, polygon);

        long before = System.currentTimeMillis();
        mongoPolygonRepo.save(polygonShape);
        long after = System.currentTimeMillis();

        return (double)(after-before);
    }

    private List<Point> coordinateToPointList(CoordinateWrapper coordinates){
        List<Point> pointList = new ArrayList<>();

        for (Coordinate coordinate : coordinates){
            double lat = coordinate.getLat();
            double lng = coordinate.getLng();
            pointList.add(new Point(lat,lng));
        }
        return pointList;
    }
}
