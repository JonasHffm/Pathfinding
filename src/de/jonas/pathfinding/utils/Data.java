package de.jonas.pathfinding.utils;

import de.jonas.pathfinding.nodes.Node;

import java.util.ArrayList;
import java.util.HashMap;

public class Data {

    public ArrayList<Node> nodeList; //ALL AVAILABLE NODES

    private ArrayList<Node> openSet;   //QUEUE NODES

    private ArrayList<Node> closedSet;  //CHECKED NODES
    private ArrayList<Node> crashedSet;  //CRASHED NODES

    private ArrayList<Node> barrierSet; //BARRIER NODES

    private HashMap<Node, Double> bestNodes; //BEST NODES    -> NODE + COST

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

    public ArrayList<Node> getOpenSet() {
        return openSet;
    }

    public ArrayList<Node> getClosedSet() {
        return closedSet;
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

    public void setOpenSet(ArrayList<Node> openSet) {
        this.openSet = openSet;
    }

    public void setClosedSet(ArrayList<Node> closedSet) {
        this.closedSet = closedSet;
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

    public HashMap<Node, Double> getBestNodes() {
        return bestNodes;
    }

    public void setBestNodes(HashMap<Node, Double> bestNodes) {
        this.bestNodes = bestNodes;
    }

    public ArrayList<Node> getCrashedSet() {
        return crashedSet;
    }

    public void setCrashedSet(ArrayList<Node> crashedSet) {
        this.crashedSet = crashedSet;
    }
}

