package com.benchmark.model;

import java.util.List;

public class CoordinateGetResponse extends CoordinatePostResponse{
    private List<Coordinate> coordinates;
    private double basicObjectMultiPoint;

    public double getBasicObjectMultiPoint() {
        return basicObjectMultiPoint;
    }

    public void setBasicObjectMultiPoint(double basicObjectMultiPoint) {
        basicObjectMultiPoint = basicObjectMultiPoint;
    }

    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }
}
