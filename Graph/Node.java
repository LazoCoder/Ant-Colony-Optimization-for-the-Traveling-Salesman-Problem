package Graph;

/**
 * This abstract class is the parent of both Vertex and Edge.
 * <br />
 * Vertices and edges contain similar properties: a name and a coordinate.
 * This abstract class brings those commonalities together to avoid duplicity.
 */
public abstract class Node {

    String name;
    int x, y;

    /**
     * Construct the Node.
     * @param name      The name of the Node
     * @param x         The x coordinate of the Node on the locale
     * @param y         The y coordinate of the Node on the locale
     */
    public Node (String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    /**
     * Get the name of the Node.
     * @return          The name of the Node
     */
    public String getName () {
        return name;
    }

    /**
     * Get the x coordinate of the Node on the locale.
     * @return          The x coordinate of the Node
     */
    public int getX () {
        return x;
    }

    /**
     * Get the y coordinate of the Node on the locale.
     * @return          The y coordinate of the Node
     */
    public int getY () {
        return y;
    }

    /**
     * Change the name of the Node.
     * This is the only mutable property of Node as it has no effect
     * on the hashCode or equals methods.
     * @param name      The new name of the Node
     */
    public void setName (String name) {
        if (name == null)
            throw new NullPointerException();

        this.name = name;
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;

        Node node = (Node) o;

        return y == node.y && x == node.x;
    }

    @Override
    public int hashCode () {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString () {
        return "Node{" +
                "name='" + name + '\'' +
                ", x=" + x +
                ", y=" + y +
                '}';
    }

}
