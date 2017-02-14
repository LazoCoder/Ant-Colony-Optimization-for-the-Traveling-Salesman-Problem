package Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * A representation of a Graph Vertex.
 * <br />
 * Contains all the same functionality as the Edge class,
 * with the added functionality of storing Edges.
 * <br />
 * Even though the elements in the hashMap are of type Edge,
 * Vertices can be added as well indirectly.
 *
 * Adding a Vertex to the hashMap creates a new Edge instance
 * with the same name, locale and coordinates as the Vertex.
 * This Edge is then added to the hashMap.
 */
public class Vertex extends Node implements Iterable<Edge> {

    private HashMap<Integer, Edge> hashMap = new HashMap<>();
    private ArrayList<Edge> list;

    /**
     * Construct the Vertex.
     * @param name      The name of the Vertex
     * @param x         The x coordinate of the Vertex on the locale
     * @param y         The y coordinate of the Vertex on the locale
     */
    public Vertex (String name, int x, int y) {
        super(name, x, y);
        hashMap = new HashMap<>();
        list = new ArrayList<>();
    }

    /**
     * Add a Node to the hashMap. If the Node is as Vertex, creates an Edge equivalent,
     * then adds the Edge to the hashMap, otherwise adds it in directly.
     * @param n         The Node that will be added to the hashMap
     */
    public void addEdge (Node n) {
        if (n instanceof Vertex) {
            Edge e = new Edge(n.getName(), n.getX(), n.getY());
            hashMap.put(e.hashCode(), e);
            list.add(e);
        }
        else {
            hashMap.put(n.hashCode(), (Edge)n);
            list.add((Edge)n);
        }
    }

    public Edge getEdge (Node n) {
        return hashMap.get(n.hashCode());
    }

    /**
     * Check to see if an Edge with the same immutable properties as the specified Node exists.
     * @param n         The Node that will be checked to see if it exists
     * @return          True if an Edge with the same properties exists in the hashMap
     */
    public boolean contains (Node n) {
        return hashMap.containsValue(n.hashCode());
    }

    /**
     * Check the total number of Edges contained in this Vertex.
     * @return          The total number of Edges the Vertex contains.
     */
    public int getTotalEdges () {
        return hashMap.size();
    }

    @Override
    public Iterator<Edge> iterator () {
        return list.iterator();
    }

    @Override
    public String toString () {
        return "Vertex{" +
                "name='" + name + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", edges=" + hashMap +
                '}';
    }

}

