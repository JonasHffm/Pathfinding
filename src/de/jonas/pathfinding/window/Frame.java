package de.jonas.pathfinding.window;

import de.jonas.pathfinding.main.Pathfinding;
import de.jonas.pathfinding.menue.Menue;
import de.jonas.pathfinding.nodes.Node;

import javax.swing.*;

public class Frame {

    private int width;
    private int height;
    private int grid_size;
    private int nodeHeightWidth;

    private JFrame frame;

    public Frame(int width, int height, int grid_size, int nodeHeightWidth) {
        this.width = width;
        this.height = height;
        this.grid_size = grid_size;
        this.nodeHeightWidth = nodeHeightWidth;
    }

    public void open() {

        this.frame = new JFrame();
        frame.setSize(this.width, this.height);
        frame.setVisible(true);
        frame.setTitle("A* Pathfinding");
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        //ADD ELEMENTS
        Menue menue = new Menue(frame);
        menue.addBottom();
        menue.addSide();
        //


        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    public void addNodeGrid() {
        System.out.println("Instance: " + Pathfinding.instance);
        System.out.println("Initializer: " + Pathfinding.initializer);
        System.out.println("Data: " + Pathfinding.initializer.getData());
        for(int x = 0; x < grid_size*nodeHeightWidth; x +=  nodeHeightWidth) {
            for(int y = 0; y < grid_size*nodeHeightWidth; y += nodeHeightWidth) {
                Node node = new Node(this.frame, this.nodeHeightWidth, x, y);
                node.spawn();
                Pathfinding.instance.getInitializer().getData().getOpenSet().add(node);
                Pathfinding.instance.getInitializer().getData().getNodeList().add(node);
            }
        }
        frame.repaint();
    }

    public JFrame getFrame() {
        return frame;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
