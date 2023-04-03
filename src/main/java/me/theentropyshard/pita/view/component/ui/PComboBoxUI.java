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

import me.theentropyshard.pita.view.UIConstants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;

public class PComboBoxUI extends BasicComboBoxUI {
    public static final Color TRIANGLE_COLOR = Color.decode("#7A8C8D");

    @Override
    protected JButton createArrowButton() {
        return new JButton() {
            {
                this.setFocusPainted(false);
                this.setContentAreaFilled(false);
                this.setBorder(new EmptyBorder(0, 0, 0, 0));
                this.setForeground(new Color(250, 250, 250));
                this.setBackground(Color.WHITE);
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(UIConstants.NEAR_WHITE2);
                g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), UIConstants.ARC_WIDTH, UIConstants.ARC_HEIGHT);
                int[] xPoints = new int[]{(int) (0.25 * getWidth()), (int) (0.5 * getWidth()), (int) (0.75 * getWidth())};
                int[] yPoints;

                if(isPopupVisible(comboBox)) {
                    yPoints = new int[]{(int) (0.75 * getHeight()), (int) (0.25 * getHeight()), (int) (0.75 * getHeight())};
                } else {
                    yPoints = new int[]{(int) (0.25 * getHeight()), (int) (0.75 * getHeight()), (int) (0.25 * getHeight())};
                }

                int nPoints = 3;
                g2.setColor(PComboBoxUI.TRIANGLE_COLOR);
                g2.fillPolygon(xPoints, yPoints, nPoints);
                super.paintComponent(g2);
            }
        };
    }
}
