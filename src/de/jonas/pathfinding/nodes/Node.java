package de.jonas.pathfinding.nodes;

import de.jonas.pathfinding.init.Initializer;
import de.jonas.pathfinding.main.Pathfinding;
import de.jonas.pathfinding.utils.Data;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Node {

    private Node previous;

    private JFrame frame;

    private boolean barrier;

    private int size;
    private int x;
    private int y;
    private int posX, posY;
    private double fCost;

    private JPanel panel;


    public Node(JFrame frame, int size, int x, int y) {
        this.frame = frame;
        this.size = size;
        this.x = x;
        this.y = y;

        this.posX = x/size;
        this.posY = y/size;
    }

    public void spawn() {
        this.panel = new JPanel();
        panel.setBounds(this.x, this.y, this.size, this.size);
        panel.setBorder(new LineBorder(Color.BLACK, 1));
        setColor(Color.WHITE);
        panel.setVisible(true);

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("X: " + x + " Y: " + y);
                System.out.println("PosX: " + posX + " PosY: " + posY);
                Data data = Pathfinding.initializer.getData();
                if(e.isShiftDown()) {
                    if(data.getStartNode() != null) {
                        data.getStartNode().setColor(Color.WHITE);
                        data.setStartNode(nodeInstance());
                        data.getStartNode().setColor(Color.GREEN);
                    }else {
                        data.setStartNode(nodeInstance());
                        data.getStartNode().setColor(Color.GREEN);
                    }
                }else if(e.isAltDown()) {
                    if(data.getEndNode() != null) {
                        data.getEndNode().setColor(Color.WHITE);
                        data.setEndNode(nodeInstance());
                        data.getEndNode().setColor(Color.RED);
                    }else {
                        data.setEndNode(nodeInstance());
                        data.getEndNode().setColor(Color.RED);
                    }
                }else if(e.isControlDown()){
                    barrier = true;
                    setColor(Color.BLACK);
                    data.getBarrierSet().add(nodeInstance());
                }else {
                    if(nodeInstance().equals(data.getEndNode())) {
                        data.setEndNode(null);
                    }
                    if(nodeInstance().equals(data.getStartNode())) {
                        data.setStartNode(null);
                    }
                    if(barrier) {
                        barrier = false;
                    }
                    setColor(Color.WHITE);
                }
            }
        });

        frame.add(this.panel);
    }

    public void setBarrier(boolean barrier) {
        this.barrier = barrier;
    }
    public boolean isBarrier() {
        return barrier;
    }


    public void setColor(Color color) {
        this.panel.setBackground(color);
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public JPanel getPanel() {
        return panel;
    }

    public Node getPrevious() {
        return previous;
    }

    public void setPrevious(Node previous) {
        this.previous = previous;
    }

    public double getfCost() {
        return fCost;
    }

    public void setfCost(double fCost) {
        this.fCost = fCost;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    public Node nodeInstance() {
        return this;
    }

    public int getPosX() {
        return posX;
    }
    public int getPosY() {
        return posY;
    }
}
