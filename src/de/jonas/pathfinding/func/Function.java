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


    private Mode searchMode = Mode.SQUARE;

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

            for(Node all : Pathfinding.initializer.getData().getNodeList()) {
                double fCost = calcDistance(end, all) + calcDistance(start, all);
                all.setfCost(fCost);
            }
            for(Node barriers : Pathfinding.initializer.getData().getBarrierSet()) {
                if(barriers.isBarrier()) {
                    Pathfinding.initializer.getData().getClosedSet().add(getStr(barriers));
                }
            }


            pathfinding(getStr(start), getStr(end), getStr(start));

        }).start();
    }


    public boolean pathfinding(String x, String end, String start) {
        String bestPick = null;
        HashMap<String, Double> allNodesWithCost = new HashMap<>();
        if(x == null) {
            System.out.println("ERROR!");
            return true;
        }

        for(String sNode : getSurroundingNodes(x, searchMode)) {
            //CALCULATE COST
            if(!x.equals(sNode)) {
                if(!Pathfinding.initializer.getData().getClosedSet().contains(sNode)) {
                    double fCost = getNodeFromNodeStr(sNode).getfCost();
                    allNodesWithCost.put(sNode, fCost);
                }
            }
        }

        if (allNodesWithCost.size() != 0) {
            bestPick = Collections.min(allNodesWithCost.entrySet(), Map.Entry.comparingByValue()).getKey();

                Pathfinding.initializer.getData().getClosedSet().add(x);
                if (getNodeFromNodeStr(x).getPanel() != null) {
                    //COLOR
                    getNodeFromNodeStr(x).getPanel().setBackground(Color.GRAY);
                    getNodeFromNodeStr(x).getPanel().repaint();
                }
                //COLOR
                if (getNodeFromNodeStr(bestPick).getPanel() != null) {
                    getNodeFromNodeStr(bestPick).getPanel().setBackground(Color.YELLOW);
                }

                //TODO CHECK IF BEST CURRENT NODE SHOULD BE PUT IN THE BEST NODE MAP TO DRAW DIRECT PATH
                if (bestPick != null) {
                    if(!Pathfinding.initializer.getData().getClosedSet().contains(bestPick)) {
                        //CHECK IF END IS FOUND
                        if (!bestPick.equals(end)) {
                            //SLOWS DOWN ALGORITHM -> REMOVE FOR INSTANT PATH DISPLAY
                            try {
                                Thread.sleep(5);    //FAST
                                //Thread.sleep(10);       //MEDIUM
                                //Thread.sleep(200);      //SLOW
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            //
                            pathfinding(bestPick, end, start);
                            return true;
                        } else {
                            System.out.println("FOUND THE END NODE");
                            Pathfinding.initializer.getData().getClosedSet().add(end);
                            return true;
                        }
                    }
                }
        }else {

            /*

                    RESET

            */

            //REMOVE ALL CLOSED NODES FROM SET IF SEARCH FAILED -> ADD NODE TO CLOSED SET THAT CRASHED SEARCH
            for(String closed : Pathfinding.initializer.getData().getClosedSet()) {
                if(!getNodeFromNodeStr(closed).isBarrier()) {
                    getNodeFromNodeStr(closed).getPanel().setBackground(Color.WHITE);
                }
            }
            Pathfinding.initializer.getData().getClosedSet().clear();
            allNodesWithCost.clear();
            Pathfinding.initializer.getData().getCrashedSet().add(x);
            //RESET COLORS AND ADD TO CLOSED SET
            for(String crashed : Pathfinding.initializer.getData().getCrashedSet()) {
                getNodeFromNodeStr(crashed).getPanel().setBackground(Color.GRAY);
                Pathfinding.initializer.getData().getClosedSet().add(crashed);
            }
            //RESET THE BLUE COLOR AFTER RESET
            //TODO CHANGE TO NODE STRING METHOD
            for(Node all : Pathfinding.initializer.getData().getNodeList()) {
                if(all.getPanel().getBackground().equals(Color.BLUE)) {
                    all.getPanel().setBackground(Color.WHITE);
                }
            }
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            pathfinding(start,end,start);
            // RESET END
            return false;
        }
        return false;
    }


    public ArrayList<String> getSurroundingNodes(String xNode, Mode mode) {
        ArrayList<String> nodes = new ArrayList<>();
        switch (mode) {
            case STAR:
                break;
            case SQUARE:
                for(int x = -1; x <= 1; x++) {
                    for(int y = -1; y <= 1; y++) {
                        if (xNode != null) {
                            String c_Node = getNodeStrFromXY((Integer.parseInt(xNode.split(";")[0]) + x), (Integer.parseInt(xNode.split(";")[1]) + y));
                            if (c_Node != null) {
                                if (!xNode.equals(c_Node)) {
                                    if (!Pathfinding.initializer.getData().getClosedSet().contains(c_Node)) {
                                        if (!getNodeFromNodeStr(c_Node).isBarrier()) {
                                            nodes.add(c_Node);
                                            //COLOR THE OPEN SET NODES BLUE
                                            if(getNodeFromNodeStr(c_Node).getPanel() != null) {
                                                //COLOR
                                                getNodeFromNodeStr(c_Node).getPanel().setBackground(Color.BLUE);
                                                getNodeFromNodeStr(c_Node).getPanel().repaint();
                                            }
                                        }
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

    public String getNodeStrFromXY(int posX, int posY) {
        for(String node : Pathfinding.initializer.getData().getOpenSet()) {
            if(Integer.valueOf(node.split(";")[0]) == posX && Integer.valueOf(node.split(";")[1]) == posY) {
                return node;
            }
        }
        return null;
    }

    public Node getNodeFromXY(int posX, int posY) {
        for(Node node : Pathfinding.initializer.getData().getNodeList()) {
            if(node.getPosX() == posX && node.getPosY() == posY) {
                return node;
            }
        }
        return null;
    }
    public Node getNodeFromNodeStr(String nodeStr) {
        for(Node node : Pathfinding.initializer.getData().getNodeList()) {
            String c_nodeStr = node.getPosX() + ";" + node.getPosY();
            if(c_nodeStr.equals(nodeStr)) {
                return node;
            }
        }
        return null;
    }
    public String getStr(Node node) {
        return node.getPosX() + ";" + node.getPosY();
    }
    public int getXFromNodeStr(String nodeStr) {
        return Integer.valueOf(nodeStr.split(";")[0]);
    }
    public int getYFromNodeStr(String nodeStr) {
        return Integer.valueOf(nodeStr.split(";")[1]);
    }

    public double calcDistance(Node from, Node to) {
        return Math.sqrt((from.getPosX()-to.getPosX())*(from.getPosX()-to.getPosX()) + (from.getPosY()-to.getPosY())*(from.getPosY()-to.getPosY()));
    }

}
