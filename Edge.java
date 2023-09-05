package edu.birzeit.algo.dijkstra.dijkstraalgorithm.djkstraUtils;

public class Edge {

    private Building source;
    private Building destination;
    private double distance;

    public Edge() {

    }

    public Edge(Building source, Building destination, double distance) {
        this.source = source;
        this.destination = destination;
        this.distance = distance;
    }

    public Building getSource() {
        return source;
    }

    public void setSource(Building source) {
        this.source = source;
    }

    public Building getDestination() {
        return destination;
    }

    public void setDestination(Building destination) {
        this.destination = destination;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "source=" + source.getBuildingName() +
                ", destination=" + destination.getBuildingName() +
                ", weight=" + distance +
                '}';
    }
}
