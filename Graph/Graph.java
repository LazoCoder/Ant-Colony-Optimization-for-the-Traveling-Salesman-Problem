package Graph;

import Ants.Ant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/**
 * A representation of a Graph, which contains Vertices, which contain Edges.
 */
public class Graph implements Iterable<Vertex> {

    /**
     * Used a HashMap instead of a HashSet because a HashSet
     * cannot return a Vertex. It is necessary to be able to
     * return an equivalent Vertex based on an Edge.
     */
    private HashMap<Integer, Vertex> hashMap;

    /**
     * Also store the vertices in a list so that they can be
     * iterated through.
     */
    private ArrayList<Vertex> list;
    private int totalEdges;
    private int alpha, beta;
    private double evaporationRate;

    /**
     * Constructs an empty graph.
     */
    public Graph (double evaporationRate, int alpha, int beta) {
        this.alpha = alpha;
        this.beta = beta;
        this.evaporationRate = evaporationRate;
        clear();
    }

    public int getAlpha () {
        return alpha;
    }

    public int getBeta () {
        return beta;
    }

    /**
     * Gets the total number of Vertices in the Graph.
     * @return          The quantity of Vertices.
     */
    public int getTotalVertices () {
        return hashMap.size();
    }

    /**
     * Gets the total number of undirected Edges in the Graph.
     * This number will always be equivalent to the number of Edge
     * objects in the Graph divided by two, since each Edge object is
     * technically a directed Edge.
     * @return          The quantity of undirected Edges.
     */
    public int getTotalEdges () {
        return totalEdges;
    }

    /**
     * Checks to see if the Graph contains no Vertices.
     * @return          True if no Vertices exist within the Graph.
     */
    public boolean isEmpty () {
        return hashMap.isEmpty();
    }

    /**
     * Removes all the Vertices and Edges.
     */
    public void clear () {
        hashMap = new HashMap<>();
        list = new ArrayList<>();
        totalEdges = 0;
    }

    /**
     * Adds a new Vertex to the Graph.
     * @param vertex    The Vertex to add to the Graph.
     */
    public void addVertex (Vertex vertex) {
        hashMap.put(vertex.hashCode(), vertex);
        list.add(vertex);
    }

    public Vertex getVertex (Node node) {
        return hashMap.get(node.hashCode());
    }

    /**
     * Adds a Node to a Vertex.
     * If the Node is an Edge it is added directly.
     * @param vertex    The Vertex to add to.
     * @param node      The Node to be added.
     */
    public void addEdge (Vertex vertex, Node node) {
        vertex.addEdge(node);
        totalEdges++;
    }

    public Node[] getVertices () {
        Node[] nodes = new Node[getTotalVertices()];
        int i = 0;
        for (Vertex v : this) {
            nodes[i++] = v;
        }
        return nodes;
    }

    public void updatePheromone(Ant ant) {

        double eval = ant.eval();

        double probability = (1 - evaporationRate);

        Node[] edges = ant.getTour();

        HashSet<Edge> hashSet = new HashSet<>();

        for (int i = 1; i < edges.length; i++) {
            Edge e1 = getVertex(edges[i-1]).getEdge(edges[i]);
            Edge e2 = getVertex(edges[i]).getEdge(edges[i-1]);

            // The pheromones.
            double p1 = e1.getPheromone();
            double p2 = e2.getPheromone();

            hashSet.add(e1);
            hashSet.add(e2);

            e1.setPheromone(probability*p1 + 1.0/eval);
            e2.setPheromone(probability*p2 + 1.0/eval);
        }

        // Evaporate the pheromones on all the rest of the edges.
        for (Vertex v : this) {
            for (Edge e : v) {
                if (!hashSet.contains(e)) {
                    double p = e.getPheromone();
                    e.setPheromone(probability*p);
                }
            }
        }

    }

    public static double getDistance (Node node1, Node node2) {
        double xDiff = node1.getX() - node2.getX();
        double yDiff = node1.getY() - node2.getY();

        return Math.sqrt(xDiff*xDiff + yDiff*yDiff);
    }

    @Override
    public Iterator<Vertex> iterator() {
        return list.iterator();
    }

    @Override
    public String toString () {
        StringBuilder sb = new StringBuilder();

        for (Vertex v : this) {
            sb.append(v + "\n");
        }

        return new String(sb);
    }
}

