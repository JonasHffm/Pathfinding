package de.jonas.pathfinding.func;

import de.jonas.pathfinding.main.Pathfinding;
import de.jonas.pathfinding.nodes.Node;

import java.awt.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Function {

    public void start() {
        new Thread(() -> {

            Node start = Pathfinding.initializer.getData().getStartNode();
            Node end = Pathfinding.initializer.getData().getEndNode();

            System.out.println();
            System.out.println();
            if(start == null) {
                System.out.println("ERR -> Start node not set!");
                return;
            }
            if(end == null) {
                System.out.println("ERR -> End node not set!");
                return;
            }

            System.out.println("STARTING SEARCH!");
            System.out.println("Start node: X:" + start.getPosX() + " Y:" + start.getPosY());
            System.out.println("End node: X:" + end.getPosX() + " Y:" + end.getPosY());
            System.out.println();
            System.out.println();
            Pathfinding.initializer.getData().setStarted(false);

            /*
            ALGORITHM

                f(x) = g(x) + h(x)

                g(x) -> Distance between current (x) node and start node
                h(x) -> Distance between current (x) node and end node

                Check which fCost is the smallest value and take it as new X node.
             */

            System.out.println(pathfinding(start, end, start));

        }).start();
    }


    public boolean pathfinding(Node x, Node end, Node start) {
        Node bestPick = null;
        boolean found = false;
        HashMap<Node, Double> allNodesWithCost = new HashMap<>();
        for(Node sNode : getSurroundingNodes(x)) {
            double fCost = calcDistance(end, sNode) + calcDistance(start, sNode);
            allNodesWithCost.put(sNode, fCost);
            System.out.println("X: " + sNode.getPosX() + " Y: " + sNode.getPosY() + "  COST: " + fCost);
            for(Node sBCheckNode : getSurroundingNodes(sNode)) {
                if(sBCheckNode.isBarrier()) {
                    Pathfinding.initializer.getData().getClosedSet().add(sNode);
                }
            }

            if(sNode.equals(end)) {
                sNode.getPanel().setBackground(Color.MAGENTA);
                found = true;
            }
        }
        if(!found) {
            if (allNodesWithCost.size() != 0) {
                bestPick = Collections.min(allNodesWithCost.entrySet(), Map.Entry.comparingByValue()).getKey();
                System.out.println("BEST PICK X: " + bestPick.getPosX() + " Y: " + bestPick.getPosY() + "  COST: " + Collections.min(allNodesWithCost.entrySet(), Map.Entry.comparingByValue()));
                Pathfinding.initializer.getData().getClosedSet().add(x);
                x.getPanel().setBackground(Color.GRAY);
                bestPick.getPanel().setBackground(Color.YELLOW);

                if (bestPick != null) {
                    if (!bestPick.equals(end)) {

                        //SLOWS DOWN ALGORITHM -> REMOVE FOR INSTANT PATH DISPLAY
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //

                        pathfinding(bestPick, end, start);
                    }
                }
            }
        }
        return true;
    }

    public ArrayList<Node> getSurroundingNodes(Node xNode) {
        ArrayList<Node> nodes = new ArrayList<>();
        for(int x = -1; x <= 1; x++) {
            for(int y = -1; y <= 1; y++) {
                Node c_Node = getNodeFromXY(xNode.getPosX()+x, xNode.getPosY()+y);
                if(c_Node != null) {
                    if (!xNode.equals(c_Node)) {
                        if (!Pathfinding.initializer.getData().getClosedSet().contains(c_Node)) {
                            if (!c_Node.isBarrier()) {
                                nodes.add(c_Node);
                                c_Node.getPanel().setBackground(Color.BLUE);
                            }
                        }
                    }
                }
            }
        }
        return nodes;
    }

    public Node getNodeFromXY(int posX, int posY) {
        for(Node node : Pathfinding.initializer.getData().getOpenSet()) {
            if(node.getPosX() == posX && node.getPosY() == posY) {
                return node;
            }
        }
        return null;
    }
    public double calcDistance(Node from, Node to) {
        return Math.sqrt((from.getPosX()-to.getPosX())*(from.getPosX()-to.getPosX()) + (from.getPosY()-to.getPosY())*(from.getPosY()-to.getPosY()));
    }

}
