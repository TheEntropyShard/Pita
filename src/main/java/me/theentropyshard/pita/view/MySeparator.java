package me.theentropyshard.pita.view;

import javax.swing.*;
import java.awt.*;

public class MySeparator extends JSeparator {
    private int gap = 5;

    @Override
    protected void paintComponent(Graphics g) {
        //super.paintComponent(g);

        int w = getWidth();
        int h = getHeight();

        g.drawLine(w / 2 - 0, 0 + gap, w / 2 - 0, h - gap);
    }
}
