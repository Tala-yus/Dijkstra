package edu.birzeit.algo.dijkstra.dijkstraalgorithm.djkstraUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Graph {

    private Map<Building, List<Edge>> edges;
    private Map<Building, Double> distances;

    public Graph() {
        edges = new HashMap<>();
        distances = new HashMap<>();
    }

    public void addBuilding(Building building) {
        edges.put(building, new LinkedList<>());
        distances.put(building, Double.POSITIVE_INFINITY);
    }

    public void addEdge(Building source, Building destination, double weight) {
        edges.get(source).add(new Edge(source, destination, weight));
    }

    public Map<Building, List<Edge>> getEdges() {
        return edges;
    }

    public void setEdges(Map<Building, List<Edge>> edges) {
        this.edges = edges;
    }

    public Map<Building, Double> getDistances() {
        return distances;
    }

    public void setDistances(Map<Building, Double> distances) {
        this.distances = distances;
    }
}
