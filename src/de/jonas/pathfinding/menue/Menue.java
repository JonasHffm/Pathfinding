package de.jonas.pathfinding.menue;

import de.jonas.pathfinding.func.Function;
import de.jonas.pathfinding.main.Pathfinding;

import javax.swing.*;
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

        JButton button = new JButton();
        button.setBounds(25,frame.getHeight()-buttonHeight-buttonHeight/2, buttonWidth, buttonHeight);
        button.setText("Start");
        button.setVisible(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!Pathfinding.initializer.getData().isStarted()) {
                    Function function = new Function();
                    function.start();
                }
            }
        });
        frame.add(button);

    }
}
