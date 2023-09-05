package edu.birzeit.algo.dijkstra.dijkstraalgorithm.djkstraUtils;

import java.util.*;

public class DijkstraAlgo {

    public DijkstraAlgo() {

    }

    public List<Building> getShortestPath(Building source, Building destination, Graph graph) {
        PriorityQueue<Building> queue = new PriorityQueue<>((b1, b2) -> (int) (graph.getDistances().get(b1) - graph.getDistances().get(b2)));

        queue.add(source);
        graph.getDistances().put(source, 0.0);

        Map<Building, Building> previous = new HashMap<>();
        HashMap<String, Boolean> state = new HashMap<>();


        while (!queue.isEmpty()) {
            Building current = queue.poll();
            if (current == destination) {
                System.out.println("done");
                break;
            }
            if (state.containsKey(current.getBuildingName())) {
                continue;
            }
            state.put(current.getBuildingName(),true);

            for (Edge edge : graph.getEdges().get(current)) {
                Building neighbor = edge.getDestination();
                double distance = edge.getDistance();
                if (graph.getDistances().get(neighbor) > graph.getDistances().get(current) + distance) {
                    graph.getDistances().put(neighbor, graph.getDistances().get(current) + distance);
                    previous.put(neighbor, current);
                    queue.add(neighbor);

                }
            }
        }
        System.out.println(graph.getDistances().get(destination));

        if (previous.get(destination) == null) {
            return null;
        }

        List<Building> path = new ArrayList<>();
        Building current = destination;
        while (current != source) {
            path.add(current);
            current = previous.get(current);
        }
        path.add(source);
        Collections.reverse(path);

        return path;
    }

//    public List<Building> getShortestPath(Building source, Building destination, Graph graph) {
//        PriorityQueue<Building> queue = new PriorityQueue<>((city1, city2) -> (int) (city1.getCost() - city2.getCost()));
//        source.setCost(0);
//        queue.add(source);
//
//
//        Map<Building, Building> previous = new HashMap<>();
//        HashMap<String, Boolean> state = new HashMap<>();
//
//
//        while (!queue.isEmpty()) {
//            Building current = queue.poll();
//            if (state.containsKey(current.getBuildingName())) {
//                continue;
//            }
//            state.put(current.getBuildingName(),true);
//
//            for (Edge edge : graph.getEdges().get(current)) {
//                Building neighbor = edge.getDestination();
//                double distance = edge.getDistance();
//                if (neighbor.getCost() > current.getCost() + distance) {
//                    neighbor.setCost(current.getCost() + distance);
//                    previous.put(neighbor, current);
//                    queue.add(neighbor);
//
//                }
//            }
//        }
//        System.out.println(destination.getCost());
//
//        if (previous.get(destination) == null) {
//            return null;
//        }
//
//        List<Building> path = new ArrayList<>();
//        Building current = destination;
//        while (current != source) {
//            path.add(current);
//            current = previous.get(current);
//        }
//        path.add(source);
//        Collections.reverse(path);
//
//
//        return path;
//    }
}
