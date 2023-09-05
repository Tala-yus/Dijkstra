package edu.birzeit.algo.dijkstra.dijkstraalgorithm.djkstraUtils;

import edu.birzeit.algo.dijkstra.dijkstraalgorithm.HelloController;

public class Building {


    private String buildingName;
    private double mapped_x_coordinate;
    private double mapped_y_coordinate;


    public Building(String buildingName, double mapped_x_coordinate, double mapped_y_coordinate) {
        this.buildingName = buildingName;
        this.mapped_x_coordinate = mapped_x_coordinate;
        this.mapped_y_coordinate = mapped_y_coordinate;

    }


    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public double getMapped_x_coordinate() {
        return mapped_x_coordinate;
    }

    public void setMapped_x_coordinate(double mapped_x_coordinate) {
        this.mapped_x_coordinate = mapped_x_coordinate;
    }

    public double getMapped_y_coordinate() {
        return mapped_y_coordinate;
    }

    public void setMapped_y_coordinate(double mapped_y_coordinate) {
        this.mapped_y_coordinate = mapped_y_coordinate;
    }


    @Override
    public String toString() {
        return buildingName;
    }
}
