package Display;

import Graph.*;

import javax.swing.*;
import java.awt.*;

/**
 * Draws the nodes to the screen, as well as a path.
 */
public class WindowTSP extends JFrame {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 600 / 16*9;
    private static final int OFFSET = 40;
    private static final int CITY_SIZE = 6;

    private Panel panel;
    private Node[] nodes;
    private int maxX, maxY;
    private double scaleX, scaleY;

    /**
     * Construct WindowTSP and draw the nodes to the screen.
     * @param nodes    the nodes to draw to the screen
     */
    public WindowTSP(Node[] nodes) {
        this.nodes = nodes;
        setScale();
        panel = createPanel();
        setWindowProperties();
    }

    /**
     * Draw a path through the city.
     * @param chromosome    the Chromosome containing the path
     */
    public void draw (Node[] chromosome) {
        this.nodes = chromosome;
        panel.repaint();
    }

    private Panel createPanel () {
        Panel panel = new Panel();
        Container cp = getContentPane();
        cp.add(panel);
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        return panel;
    }

    private void setWindowProperties () {
        int sWidth = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2;
        int sHeight = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2;
        int x = sWidth - (WIDTH / 2);
        int y = sHeight - (HEIGHT / 2);
        setLocation(x, y);
        setResizable(false);
        pack();
        setTitle("Traveling Salesman Problem");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Sets the scale for the drawing so that all the nodes
     * are drawn inside the window.
     */
    private void setScale () {
        for (Node c : nodes) {
            if (c.getX() > maxX) {
                maxX = c.getX();
            }
            if (c.getY() > maxY) {
                maxY = c.getY();
            }
        }
        scaleX = ((double)maxX) / ((double)WIDTH- OFFSET);
        scaleY = ((double)maxY) / ((double)HEIGHT- OFFSET);
    }

    /**
     * All the drawing is done here.
     */
    private class Panel extends JPanel {

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            paintTravelingSalesman((Graphics2D)graphics);
        }

        private void paintTravelingSalesman (Graphics2D graphics) {
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            paintCityNames(graphics);
//            paintPheromones(graphics);
            paintChromosome(graphics);
            paintCities(graphics);
        }

        private void paintPheromones (Graphics2D graphics) {

            graphics.setColor(Color.darkGray);
            Node[] array = nodes;

            for (int i = 1; i < array.length; i++) {
                int x1 = (int)(array[i-1].getX() / scaleX + OFFSET / 2);
                int y1 = (int)(array[i-1].getY() / scaleY + OFFSET / 2);
                int x2 = (int)(array[i].getX() / scaleX + OFFSET / 2);
                int y2 = (int)(array[i].getY() / scaleY + OFFSET / 2);

                int x = (x1 + x2) / 2;
                int y = (y1 + y2) / 2;
//                String s = Double.toString(((Edge)array[i]).getPheromone());
//                graphics.drawString(s, x, y);
            }

        }

        private void paintChromosome (Graphics2D graphics) {

            graphics.setColor(Color.darkGray);
            Node[] array = nodes;

            for (int i = 1; i < array.length; i++) {
                int x1 = (int)(array[i-1].getX() / scaleX + OFFSET / 2);
                int y1 = (int)(array[i-1].getY() / scaleY + OFFSET / 2);
                int x2 = (int)(array[i].getX() / scaleX + OFFSET / 2);
                int y2 = (int)(array[i].getY() / scaleY + OFFSET / 2);
                graphics.drawLine(x1, y1, x2, y2);
            }

        }

        private void paintCities (Graphics2D graphics) {
            graphics.setColor(Color.darkGray);
            for (Node c : nodes) {
                int x = (int)((c.getX()) / scaleX - CITY_SIZE/2 + OFFSET / 2);
                int y = (int)((c.getY()) / scaleY - CITY_SIZE/2 + OFFSET / 2);
                graphics.fillOval(x, y, CITY_SIZE, CITY_SIZE);
            }
        }

        private void paintCityNames (Graphics2D graphics) {
            graphics.setColor(new Color(200, 200, 200));
            for (Node c : nodes) {
                int x = (int)((c.getX()) / scaleX - CITY_SIZE/2 + OFFSET/2);
                int y = (int)((c.getY()) / scaleY - CITY_SIZE/2 + OFFSET/2);
                graphics.fillOval(x, y, CITY_SIZE, CITY_SIZE);
                int fontOffset = getFontMetrics(graphics.getFont()).stringWidth(c.getName())/2-2;
                graphics.drawString(c.getName(), x-fontOffset, y-3);
            }
        }
    }

}
