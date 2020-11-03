package de.jonas.pathfinding.init;

import de.jonas.pathfinding.menue.Menue;
import de.jonas.pathfinding.utils.Data;
import de.jonas.pathfinding.window.Frame;

public class Initializer {

    private Data data;
    private Frame mainframe;

    private final int width = 750;
    private final int height = 750;
    private final int grid_size = 45;
    private final int nodeHeightWidth = 15;

    public Initializer() {
        System.out.println("Started Init Session");
        this.data = new Data();

        new Thread(() -> {
            this.mainframe = new Frame(width, height, grid_size, nodeHeightWidth);
            this.mainframe.open();
            this.mainframe.addNodeGrid();
        }).start();
    }

    public Data getData() {
        return data;
    }

    public Frame getMainframe() {
        return mainframe;
    }
}
