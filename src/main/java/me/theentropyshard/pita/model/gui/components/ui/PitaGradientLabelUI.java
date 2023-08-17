/*
 *  Copyright 2022-2023 TheEntropyShard
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package me.theentropyshard.pita.model.gui.components.ui;

import me.theentropyshard.pita.model.gui.components.PitaGradientLabel;

import javax.swing.*;
import javax.swing.plaf.basic.BasicLabelUI;
import java.awt.*;

/**
 * @see <a href="https://stackoverflow.com/a/65133451/19857533">Redrawing a JLabel to get a Gradient Painted Text</a>
 */
public class PitaGradientLabelUI extends BasicLabelUI {
    @Override
    protected void paintEnabledText(JLabel l, Graphics g, String s, int x, int y) {
        if (l instanceof PitaGradientLabel) {
            PitaGradientLabel label = (PitaGradientLabel) l;
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2.setPaint(new GradientPaint(
                    0,
                    0,
                    label.getLeftColor(),
                    g2.getFontMetrics().stringWidth(s),
                    l.getHeight(),
                    label.getRightColor()
            ));
            g2.drawString(s, x, y);
        } else {
            super.paintEnabledText(l, g, s, x, y);
        }
    }
}
