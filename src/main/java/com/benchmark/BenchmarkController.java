package com.benchmark;


import com.benchmark.model.*;
import com.benchmark.service.MongoBasicObjectService;
import com.benchmark.service.MongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;


@Controller
public class BenchmarkController {
    @Autowired
    private MongoService mongoService;

    private MongoBasicObjectService mongoDriverService = new MongoBasicObjectService();

    @PostConstruct
    public void clearAll(){
        mongoService.clear();
    }

    @GetMapping(path = "/coordinateName")
    public @ResponseBody
    List<NameResponse> getAllName(){
        return mongoService.getAllName();
    }

    @GetMapping(path = "/coordinate/{shapeName}")
    public @ResponseBody
    CoordinateGetResponse getShapeCoordinate(@PathVariable String shapeName)  {
        CoordinateGetResponse response = mongoService.getReadTime(shapeName);

        response.setMongoSpatialMultiPoint(mongoService.getSpatialMultiPointReadTime(shapeName));
        response.setMongoSpatialPolygon(mongoService.getSpatialPolygonReadTime(shapeName));
        response.setMongoSpatialLineString(mongoService.getSpatialLineStringReadTime(shapeName));
        response.setBasicObjectMultiPoint(mongoDriverService.getMultiPointReadTime(shapeName));

        return response;
    }


    @PostMapping(path = "/coordinate/{shapeName}", consumes="application/json",produces="application/json")
    public @ResponseBody
    CoordinatePostResponse postShapeCoordinate(@PathVariable String shapeName, @RequestBody CoordinateWrapper coordinate){
        shapeName = mongoService.checkIfExistAndReturnNewName(shapeName);
        CoordinatePostResponse response = new CoordinatePostResponse();

        response.setMongoDb(mongoService.getSaveTime(shapeName, coordinate));
        response.setMongoSpatialMultiPoint(mongoService.getMultiPointSaveTime(shapeName, coordinate));
        response.setMongoSpatialLineString(mongoService.getLineStringSaveTime(shapeName, coordinate));
        response.setMongoSpatialPolygon(mongoService.getPolygonSaveTime(shapeName, coordinate));

        return response;
    }

}
