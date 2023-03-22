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

package me.theentropyshard.pita.view.component.ui;

import me.theentropyshard.pita.view.component.GradientLabel;

import javax.swing.*;
import javax.swing.plaf.basic.BasicLabelUI;
import java.awt.*;

/**
 * @see <a href="https://stackoverflow.com/a/65133451/19857533">Redrawing a JLabel to get a Gradient Painted Text</a>
 */
public class PGradientLabelUI extends BasicLabelUI {
    @Override
    protected void paintEnabledText(JLabel l, Graphics g, String s, int x, int y) {
        if(l instanceof GradientLabel) {
            GradientLabel gl = (GradientLabel) l;
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setPaint(new GradientPaint(0, 0, gl.getColor1(), l.getWidth(), l.getHeight(), gl.getColor2()));
            g2.drawString(s, x, y);
        } else {
            super.paintEnabledText(l, g, s, x, y);
        }
    }
}
