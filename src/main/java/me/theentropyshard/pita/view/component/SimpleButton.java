/*      Pita. A simple desktop client for NetSchool by irTech
 *      Copyright (C) 2022-2023 TheEntropyShard
 *
 *      This program is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *
 *      This program is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package me.theentropyshard.pita.view.component;

import me.theentropyshard.pita.view.UIConstants;

import javax.swing.*;
import java.awt.*;

public class SimpleButton extends JButton {
    public SimpleButton(String text) {
        super(text);
        this.setFocusPainted(false);
        this.setContentAreaFilled(false);
        this.setForeground(new Color(250, 250, 250));
        this.setBackground(Color.WHITE);
        this.setFont(new Font("JetBrains Mono", Font.BOLD, 12));
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    protected void paintBorder(Graphics g) {

    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(new GradientPaint(0, 0, UIConstants.DARK_GREEN, this.getWidth(), this.getHeight(), UIConstants.LIGHT_GREEN));
        g2.fillRect(0, 0, this.getWidth(), this.getHeight());
        super.paintComponent(g);
    }
}
