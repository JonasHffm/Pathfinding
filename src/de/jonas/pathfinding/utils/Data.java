package de.jonas.pathfinding.utils;

import de.jonas.pathfinding.nodes.Node;

import java.util.ArrayList;
import java.util.HashMap;

public class Data {

    public ArrayList<Node> nodeList; //ALL AVAILABLE NODES

    private ArrayList<String> openSet;   //QUEUE NODES

    private ArrayList<String> closedSet;  //CHECKED NODES
    private ArrayList<String> crashedSet;  //CRASHED NODES

    private ArrayList<Node> barrierSet; //BARRIER NODES

    private HashMap<String, Double> bestNodes; //BEST NODES    -> NODE + COST

    private boolean started = false;

    private Node startNode;
    private Node endNode;

    public Data() {
        this.openSet = new ArrayList<>();
        this.closedSet = new ArrayList<>();
        this.barrierSet = new ArrayList<>();
        this.nodeList = new ArrayList<>();
        this.bestNodes = new HashMap<>();
        this.crashedSet = new ArrayList<>();
    }

    public void setStartNode(Node startNode) {
        this.startNode = startNode;
    }
    public void setEndNode(Node endNode) {
        this.endNode = endNode;
    }
    public Node getStartNode() {
        return startNode;
    }
    public Node getEndNode() {
        return endNode;
    }


    public ArrayList<Node> getBarrierSet() {
        return barrierSet;
    }

    public void setBarrierSet(ArrayList<Node> barrierSet) {
        this.barrierSet = barrierSet;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public ArrayList<Node> getNodeList() {
        return nodeList;
    }

    public void setNodeList(ArrayList<Node> nodeList) {
        this.nodeList = nodeList;
    }

    public ArrayList<String> getOpenSet() {
        return openSet;
    }

    public void setOpenSet(ArrayList<String> openSet) {
        this.openSet = openSet;
    }

    public ArrayList<String> getClosedSet() {
        return closedSet;
    }

    public void setClosedSet(ArrayList<String> closedSet) {
        this.closedSet = closedSet;
    }

    public ArrayList<String> getCrashedSet() {
        return crashedSet;
    }

    public void setCrashedSet(ArrayList<String> crashedSet) {
        this.crashedSet = crashedSet;
    }

    public HashMap<String, Double> getBestNodes() {
        return bestNodes;
    }

    public void setBestNodes(HashMap<String, Double> bestNodes) {
        this.bestNodes = bestNodes;
    }
}

