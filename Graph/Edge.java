package Graph;

/**
 * A representation of a Graph Edge.
 */
public class Edge extends Node {

    private double pheromone;

    /**
     * Constructs the Edge.
     * @param name      The name of the Edge.
     * @param x         The x coordinate of the Edge on the locale.
     * @param y         The y coordinate of the Edge on the locale.
     */
    public Edge (String name, int x, int y) {
        super(name, x, y);
        pheromone = 0.01;
    }

    public void setPheromone (double pheromone) {
        this.pheromone = pheromone;
    }

    public double getPheromone () {
        return pheromone;
    }

    @Override
    public String toString () {
        return "Edge{" +
                "name='" + name + '\'' +
                ", x=" + x +
                ", y=" + y +
                '}';
    }

}

