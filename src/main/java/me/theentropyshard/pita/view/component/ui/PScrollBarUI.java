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

import me.theentropyshard.pita.view.PitaColors;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

/**
 * Slightly modified version of https://github.com/DJ-Raven/java-swing-school-management-dashboard/blob/main/school-dashboard/src/com/raven/swing/scrollbar/ModernScrollBarUI.java
 */
public class PScrollBarUI extends BasicScrollBarUI {
    private static final int THUMB_SIZE = 80;

    @Override
    protected Dimension getMaximumThumbSize() {
        if(this.scrollbar.getOrientation() == JScrollBar.VERTICAL) {
            return new Dimension(0, PScrollBarUI.THUMB_SIZE);
        } else {
            return new Dimension(PScrollBarUI.THUMB_SIZE, 0);
        }
    }

    @Override
    protected Dimension getMinimumThumbSize() {
        if(this.scrollbar.getOrientation() == JScrollBar.VERTICAL) {
            return new Dimension(0, PScrollBarUI.THUMB_SIZE);
        } else {
            return new Dimension(PScrollBarUI.THUMB_SIZE, 0);
        }
    }

    @Override
    protected JButton createIncreaseButton(int i) {
        return new ScrollBarButton();
    }

    @Override
    protected JButton createDecreaseButton(int i) {
        return new ScrollBarButton();
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {

    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int x = thumbBounds.x;
        int y = thumbBounds.y;
        int width = thumbBounds.width;
        int height = thumbBounds.height;
        if(this.scrollbar.getOrientation() == JScrollBar.VERTICAL) {
            y += 8;
            height -= 16;
        } else {
            x += 8;
            width -= 16;
        }
        g2.setPaint(new GradientPaint(0, 0, PitaColors.DARK_GREEN, width, height, PitaColors.LIGHT_GREEN));
        g2.fillRoundRect(x, y, width, height, 1, 1);
    }

    private static class ScrollBarButton extends JButton {
        public ScrollBarButton() {
            this.setBorder(BorderFactory.createEmptyBorder());
        }

        @Override
        public void paint(Graphics g) {

        }
    }
}
