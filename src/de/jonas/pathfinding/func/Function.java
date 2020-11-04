package de.jonas.pathfinding.func;

import de.jonas.pathfinding.main.Pathfinding;
import de.jonas.pathfinding.nodes.Node;
import de.jonas.pathfinding.utils.Mode;

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

            pathfinding(start, end, start);

        }).start();
    }


    public void pathfinding(Node x, Node end, Node start) {
        Node bestPick = null;
        boolean found = false;
        HashMap<Node, Double> allNodesWithCost = new HashMap<>();
        Mode searchMode = Mode.SQUARE;
        for(Node sNode : getSurroundingNodes(x, searchMode)) {
            //CALCULATE COST
            double fCost = calcDistance(end, sNode) + calcDistance(start, sNode);
            allNodesWithCost.put(sNode, fCost);
            System.out.println("X: " + sNode.getPosX() + " Y: " + sNode.getPosY() + "  COST: " + fCost);
            for(Node sBCheckNode : getSurroundingNodes(sNode, searchMode)) {
                if(sBCheckNode.isBarrier()) {
                    Pathfinding.initializer.getData().getClosedSet().add(sNode);
                }
            }
        }
        if (allNodesWithCost.size() != 0) {

            //PICK BEST NODE
            bestPick = Collections.min(allNodesWithCost.entrySet(), Map.Entry.comparingByValue()).getKey();
            //double bestPickCost = Collections.min(allNodesWithCost.entrySet(), Map.Entry.comparingByValue()).getValue();
            System.out.println("BEST PICK X: " + bestPick.getPosX() + " Y: " + bestPick.getPosY() + "  COST: " + Collections.min(allNodesWithCost.entrySet(), Map.Entry.comparingByValue()).getValue());
            Pathfinding.initializer.getData().getClosedSet().add(x);
            x.getPanel().setBackground(Color.GRAY);
            bestPick.getPanel().setBackground(Color.YELLOW);


            //TODO CHECK IF BEST CURRENT NODE SHOULD BE PUT IN THE BEST NODE MAP TO DRAW DIRECT PATH


            if (bestPick != null) {

                //CHECK IF END IS FOUND
                if (!bestPick.equals(end)) {


                    //SLOWS DOWN ALGORITHM -> REMOVE FOR INSTANT PATH DISPLAY
                    try {
                        //Thread.sleep(2);    //FAST
                        Thread.sleep(10);       //MEDIUM
                        //Thread.sleep(200);      //SLOW
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //


                    pathfinding(bestPick, end, start);
                }else {
                    System.out.println("FOUND THE END NODE");
                    Pathfinding.initializer.getData().getClosedSet().add(end);
                }
            }
        }else {

            //REMOVE ALL CLOSED NODES FROM SET IF SEARCH FAILED -> ADD NODE TO CLOSED SET THAT CRASHED SEARCH
            for(Node closed : Pathfinding.initializer.getData().getClosedSet()) {
                closed.getPanel().setBackground(Color.WHITE);
            }
            Pathfinding.initializer.getData().getClosedSet().clear();
            allNodesWithCost.clear();
            Pathfinding.initializer.getData().getCrashedSet().add(x);
            //RESET COLORS AND ADD TO CLOSED SET
            for(Node crashed : Pathfinding.initializer.getData().getCrashedSet()) {
                crashed.getPanel().setBackground(Color.GRAY);
                Pathfinding.initializer.getData().getClosedSet().add(crashed);
            }


            //RESET THE BLUE COLOR AFTER RESET
            for(Node all : Pathfinding.initializer.getData().getNodeList()) {
                if(all.getPanel().getBackground().equals(Color.BLUE)) {
                    all.getPanel().setBackground(Color.WHITE);
                }
            }

            pathfinding(start,end,start);
        }
    }


    public ArrayList<Node> getSurroundingNodes(Node xNode, Mode mode) {
        ArrayList<Node> nodes = new ArrayList<>();
        switch (mode) {
            case STAR:
                Node left = null, right = null, up = null, down = null;
                left = getNodeFromXY(xNode.getPosX()-1, xNode.getPosY());
                right = getNodeFromXY(xNode.getPosX()+1, xNode.getPosY());
                up = getNodeFromXY(xNode.getPosX(), xNode.getPosY()-1);
                down = getNodeFromXY(xNode.getPosX(), xNode.getPosY()+1);
                if(left != null) {
                    nodes.add(left);
                }
                if(right != null) {
                    nodes.add(right);
                }
                if(up != null) {
                    nodes.add(up);
                }
                if(down != null) {
                    nodes.add(down);
                }
                break;
            case SQUARE:
                for(int x = -1; x <= 1; x++) {
                    for(int y = -1; y <= 1; y++) {
                        Node c_Node = getNodeFromXY(xNode.getPosX()+x, xNode.getPosY()+y);
                        if(c_Node != null) {
                            if (!xNode.equals(c_Node)) {
                                if (!Pathfinding.initializer.getData().getClosedSet().contains(c_Node)) {
                                    if (!c_Node.isBarrier()) {
                                        nodes.add(c_Node);
                                        //COLOR THE OPEN SET NODES BLUE
                                        c_Node.getPanel().setBackground(Color.BLUE);
                                    }
                                }
                            }
                        }
                    }
                }
                break;
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
