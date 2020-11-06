package de.jonas.pathfinding.menue;

import de.jonas.pathfinding.func.Function;
import de.jonas.pathfinding.main.Pathfinding;
import de.jonas.pathfinding.nodes.Node;
import de.jonas.pathfinding.utils.Data;
import de.jonas.pathfinding.window.Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;

public class Menue {

    private JFrame frame;

    //START BUTTON
    private final int buttonHeight = 50;
    private final int buttonWidth = 100;
    //


    public Menue(JFrame frame) {
        this.frame = frame;
    }

    public void addSide() {

    }

    public void addBottom() {

        JTextField level = new JTextField();
        level.setBounds(150, frame.getHeight()-buttonHeight-buttonHeight/2, 400, 30);
        level.setVisible(true);
        frame.add(level);

        JButton button = new JButton();
        button.setBounds(25,frame.getHeight()-buttonHeight-buttonHeight/2, buttonWidth, buttonHeight);
        button.setText("Start");
        button.setVisible(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!Pathfinding.initializer.getData().isStarted()) {

                    //LEVEL LOAD
                    if(level.getText() != null && !level.getText().equals("")) {
                        loadLevel(level.getText());
                    }
                    //START
                    Function function = new Function();
                    function.start();
                    //LEVEL SAVE
                    if(level.getText() == null || level.getText().equalsIgnoreCase("")) {
                        if (Pathfinding.initializer.getData().getStartNode() == null) {
                            return;
                        }
                        if (Pathfinding.initializer.getData().getEndNode() == null) {
                            return;
                        }
                        level.setText(new Frame().getCurrentLevel());
                    }



                }
            }
        });
        frame.add(button);

        JButton random = new JButton();
        random.setBounds(600,frame.getHeight()-buttonHeight-buttonHeight/2, buttonWidth, buttonHeight);
        random.setText("Random");
        random.setVisible(true);
        random.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(Node all : Pathfinding.initializer.getData().getNodeList()) {
                    double r = Math.random();
                    if(r < 0.1) {
                        all.setBarrier(true);
                        all.getPanel().setBackground(Color.BLACK);
                        Pathfinding.initializer.getData().getBarrierSet().add(all);
                    }
                }
            }
        });
        frame.add(random);

    }


    public void loadLevel(String levelStr) {
        String startStr = levelStr.split("\\|")[0];
        int startStrX = Integer.valueOf(startStr.split(";")[0]);
        int startStrY = Integer.valueOf(startStr.split(";")[1]);
        String endStr = levelStr.split("\\|")[1];
        int endStrX = Integer.valueOf(endStr.split(";")[0]);
        int endStrY = Integer.valueOf(endStr.split(";")[1]);
        Function function = new Function();
        Data data = Pathfinding.initializer.getData();

        Node start = function.getNodeFromXY(startStrX, startStrY);
        start.getPanel().setBackground(Color.GREEN);
        data.setStartNode(start);
        Node end = function.getNodeFromXY(endStrX, endStrY);
        end.getPanel().setBackground(Color.RED);
        data.setEndNode(end);


        String[] barrierStrArr = levelStr.split("\\|")[2].split(":");
        for(int i = 0; barrierStrArr.length > i; i++) {
            int barrierX = Integer.valueOf(barrierStrArr[i].split(";")[0]);
            int barrierY = Integer.valueOf(barrierStrArr[i].split(";")[1]);
            Node barrier = function.getNodeFromXY(barrierX, barrierY);
            barrier.getPanel().setBackground(Color.BLACK);
            barrier.setBarrier(true);
            data.getBarrierSet().add(barrier);
        }
    }

}
