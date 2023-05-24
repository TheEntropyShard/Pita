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

import me.theentropyshard.pita.Pita;
import me.theentropyshard.pita.view.ThemeManager;
import me.theentropyshard.pita.view.UIConstants;

import javax.swing.*;
import java.awt.*;

public class PSimpleButton extends JButton {
    private final ThemeManager tm;
    private boolean roundCorners;
    private boolean squareSides;

    public PSimpleButton(String text) {
        super(text);
        this.setFocusPainted(false);
        this.setContentAreaFilled(false);
        this.setForeground(new Color(250, 250, 250));

        this.tm = Pita.getPita().getThemeManager();
        this.setBackground(this.tm.getColor("mainColor"));
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

        g2.setPaint(new GradientPaint(0, 0, this.tm.getColor("darkAccentColor"), this.getWidth(), this.getHeight(), this.tm.getColor("lightAccentColor")));
        if(this.roundCorners) {
            g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), UIConstants.ARC_WIDTH, UIConstants.ARC_HEIGHT);
        } else {
            g2.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
        super.paintComponent(g);
    }

    @Override
    public Dimension getPreferredSize() {
        if(this.squareSides) {
            Dimension d = super.getPreferredSize();
            int s = Math.max(d.height, d.width);
            return new Dimension(s, s);
        } else {
            return super.getPreferredSize();
        }
    }

    public boolean isRoundCorners() {
        return this.roundCorners;
    }

    public void setRoundCorners(boolean roundCorners) {
        this.roundCorners = roundCorners;
    }

    public boolean isSquareSides() {
        return this.squareSides;
    }

    public void setSquareSides(boolean squareSides) {
        this.squareSides = squareSides;
    }
}
