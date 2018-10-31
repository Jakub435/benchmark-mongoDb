package com.benchmark.model;

import java.util.List;

public class CoordinateGetResponse extends CoordinatePostResponse{
    private List<Coordinate> coordinates;

    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }
}
