package com.benchmark.model;


public class Coordinate {
    private double lat;
    private double lng;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public Coordinate(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public Coordinate() { }

    public String toPolygonString(){
        return lat + " " + lng;
    }
}
