package Ants;

import Graph.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

/**
 * Represents an Ant from the Ant Colony Optimization algorithm.
 */
public class Ant {

    private Graph graph;
    private Node current;
    private HashSet<Node> placesTravelled;
    private ArrayList<Node> tour;

    /**
     * Construct the Ant.
     * @param graph     the graph that the ant will traverse
     */
    public Ant (Graph graph) {
        this.graph = graph;
        clear();
    }

    /**
     * Restart the Ant by removing the current position, tour, & history.
     */
    public void clear () {
        current = getRandomVertex();
        placesTravelled = new HashSet<>();
        tour = new ArrayList<>();
    }

    /**
     * Select a random vertex from the graph.
     * @return      a random vertex
     */
    private Vertex getRandomVertex () {
        int r = new Random().nextInt(graph.getTotalVertices());
        Iterator<Vertex> it = graph.iterator();
        for (int i = 0; i < r; i++) {
            it.next();
        }
        return it.next();
    }

    /**
     * Allow the Ant to travel to the next Vertex.
     */
    public void travel () {

        if (!notFinished()) {
            throw new IllegalStateException("Cannot travel since the tour is complete.");
        }

        // If there are no more Edges left, add the first one to the end.
        if (graph.getTotalVertices() == tour.size()) {
            tour.add(tour.get(0));
            return;
        }

        Edge e = nextEdge();
        placesTravelled.add(e);
        tour.add(e);
        current = e;
    }

    /**
     * Check if the Ant has made a complete tour around the graph.
     * The number of vertices in the tour should be one greater than
     * the total vertices in the graph as the Ant has to come back
     * to its starting position at the end.
     * @return      true if it has made a complete tour
     */
    public boolean notFinished () {
        return graph.getTotalVertices() + 1 != tour.size();
    }

    /**
     * Get the completed tour that the ant travelled.
     * @return      the tour that the Ant travelled
     */
    public Node[] getTour () {
        if (notFinished()) {
            throw new IllegalStateException("Cannot return an incomplete tour.");
        }

        Node[] nodes = new Node[tour.size()];

        for (int i = 0; i < tour.size(); i++) {
            nodes[i] = tour.get(i);
        }

        return nodes;
    }

    /**
     * Get the evaluation of the completed tour.
     * @return      the sum of the total distances
     */
    public int eval () {
        int eval = 0;

        for (int i = 1; i < tour.size(); i++) {
            eval += Graph.getDistance(tour.get(i), tour.get(i-1));
        }

        return eval;
    }

    /**
     * Get the Edge that the ant should travel to next. Accounts for both
     * the pheromones and distances of all the edges.
     * @return      the Edge to travel across
     */
    public Edge nextEdge () {
        ArrayList<Pair<Edge, Double>> probabilities = probabilities();
        double r = new Random().nextDouble();

        for (Pair<Edge, Double> pair : probabilities) {
            if (r <= pair.item2) {
                return pair.item1;
            }
        }

        throw new AssertionError("No Edge could be selected.");
    }

    /**
     * Get the probabilities of all the edges and return them in an array.
     * Example: if there are 4 edges, each with a probability of 0.25, then
     * the array list will contain [0.25, 0.50, 0.75, 1.00].
     * @return      the probabilities of each edge
     */
    private ArrayList<Pair<Edge, Double>> probabilities () {
        double denominator = denominator();
        ArrayList<Pair<Edge, Double>> probabilities = new ArrayList<>(validEdges());

        for (Edge e : graph.getVertex(current)) {
            if (placesTravelled.contains(e)) continue;
            Pair<Edge, Double> pair = new Pair<>();

            if (probabilities.size() == 0) {
                pair.item2 = desirability(e) / denominator;
            } else {
                int i = probabilities.size() - 1;
                pair.item2 = probabilities.get(i).item2 + desirability(e) / denominator;
            }

            pair.item1 = e;
            probabilities.add(pair);
        }

        return probabilities;
    }

    /**
     * Get the number of Edges that are valid from the current position of the ant.
     * Edges that have been travelled to previously are invalid.
     * @return      the quantity of valid edges
     */
    private int validEdges () {
        int i = 0;
        for (Edge e : graph.getVertex(current)) {
            if (!placesTravelled.contains(e)) {
                i++;
            }
        }
        return i;
    }

    /**
     * Calculate the denominator for the formula that determines the probability
     * of an ant moving from the current location to another.
     * @return      the sum of all the probabilities of each edge
     */
    private double denominator () {
        double denominator = 0.0;
        for (Edge e : graph.getVertex(current)) {
            if (placesTravelled.contains(e)) continue;
            denominator += desirability(e);
        }
        return denominator;
    }

    /**
     * Calculate the pheromone on Edge e, to the power of alpha.
     * Calculate the desirability of Edge e based on distance, to the power of beta.
     * Get the product of the two results.
     * @param e     the Edge to perform the calculations on
     * @return      the desirability of the Edge
     */
    private double desirability (Edge e) {
        double pheromone = Math.pow(e.getPheromone(), graph.getAlpha());
        double distance = Graph.getDistance(current, e);
        double distanceValue = Math.pow(1/distance, graph.getBeta());
        return pheromone * distanceValue;
    }

    /**
     * Holds a pair of items.
     * @param <T>   the first item type
     * @param <E>   the second item type
     */
    private static class Pair<T, E> {
        T item1;
        E item2;
    }

    @Override
    public String toString () {
        StringBuilder sb = new StringBuilder();
        boolean flag = false;
        for (Node node : tour) {
            if (flag) sb.append(" -> ");
            flag = true;
            sb.append(node.getName());
        }

        return new String(sb);
    }

}
