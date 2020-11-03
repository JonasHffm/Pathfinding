package de.jonas.pathfinding.main;

import de.jonas.pathfinding.init.Initializer;

public class Pathfinding implements Search{

    public static Pathfinding instance;
    public static Initializer initializer;

    public static void main(String[] args) {
        instance = new Pathfinding();
        instance.start();
    }

    @Override
    public void start() {
        System.out.println("Initializer created!");
        initializer = new Initializer();
    }

    public Initializer getInitializer() {
        return initializer;
    }
}
