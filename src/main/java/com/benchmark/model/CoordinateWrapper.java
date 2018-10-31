package com.benchmark.model;


import java.util.ArrayList;

public class CoordinateWrapper extends ArrayList<Coordinate>{
    private ArrayList<Coordinate> coordinateList;

    public ArrayList<Coordinate> getCoordinateList() {
        return coordinateList;
    }

    public void setCoordinateList(ArrayList<Coordinate> coordinateList) {
        this.coordinateList = coordinateList;
    }
}
