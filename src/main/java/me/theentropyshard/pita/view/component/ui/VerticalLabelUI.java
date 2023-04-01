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

import javax.swing.*;
import javax.swing.plaf.basic.BasicLabelUI;
import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * @see <a href="https://stackoverflow.com/a/14778018/19857533">java how to make a JLabel with vertical text?</a>
 */
public class VerticalLabelUI extends BasicLabelUI {
    private final boolean clockwise;

    public VerticalLabelUI(boolean clockwise) {
        super();
        this.clockwise = clockwise;
    }

    @Override
    public Dimension getPreferredSize(JComponent c) {
        Dimension dim = super.getPreferredSize(c);
        return new Dimension(dim.height, dim.width);
    }

    private static final Rectangle paintIconR = new Rectangle();
    private static final Rectangle paintTextR = new Rectangle();
    private static final Rectangle paintViewR = new Rectangle();
    private static Insets paintViewInsets = new Insets(0, 0, 0, 0);

    @Override
    public void paint(Graphics g, JComponent c) {
        JLabel label = (JLabel) c;
        String text = label.getText();
        Icon icon = label.isEnabled() ? label.getIcon() : label.getDisabledIcon();

        if(icon == null && text == null) {
            return;
        }

        FontMetrics fm = g.getFontMetrics();
        paintViewInsets = c.getInsets(paintViewInsets);

        paintViewR.x = paintViewInsets.left;
        paintViewR.y = paintViewInsets.top;

        // Use inverted height & width
        paintViewR.height = c.getWidth() - (paintViewInsets.left + paintViewInsets.right);
        paintViewR.width = c.getHeight() - (paintViewInsets.top + paintViewInsets.bottom);

        paintIconR.x = paintIconR.y = paintIconR.width = paintIconR.height = 0;
        paintTextR.x = paintTextR.y = paintTextR.width = paintTextR.height = 0;

        String clippedText = this.layoutCL(label, fm, text, icon, paintViewR, paintIconR, paintTextR);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        AffineTransform tr = g2.getTransform();
        if(this.clockwise) {
            g2.rotate(Math.PI / 2);
            g2.translate(0, -c.getWidth());
        } else {
            g2.rotate(-Math.PI / 2);
            g2.translate(-c.getHeight(), 0);
        }

        if(icon != null) {
            icon.paintIcon(c, g, paintIconR.x, paintIconR.y);
        }

        if(text != null) {
            int textX = paintTextR.x;
            int textY = paintTextR.y + fm.getAscent();

            if(label.isEnabled()) {
                this.paintEnabledText(label, g, clippedText, textX, textY);
            } else {
                this.paintDisabledText(label, g, clippedText, textX, textY);
            }
        }
        g2.setTransform(tr);
    }
}
