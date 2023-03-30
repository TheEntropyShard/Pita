package me.theentropyshard.pita.view.component;

import me.theentropyshard.pita.view.component.ui.PScrollBarUI;

import javax.swing.*;
import java.awt.*;

public class PScrollBar extends JScrollBar {
    public PScrollBar() {
        this.setUI(new PScrollBarUI());
        this.setPreferredSize(new Dimension(5, 5));
        this.setForeground(new Color(94, 139, 231));
        this.setUnitIncrement(25);
        this.setBackground(Color.WHITE);
    }
}
