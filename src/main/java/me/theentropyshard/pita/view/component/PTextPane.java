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

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;

public class PTextPane extends JTextPane {
    public PTextPane() {
        ThemeManager tm = Pita.getPita().getThemeManager();

        this.setContentType("text/html");
        this.setOpaque(false);
        this.setEditable(false);
        this.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));
        this.setForeground(new Color(120, 120, 120));
        this.setSelectionColor(tm.getColor("ultraLightAccentColor"));
        this.setMargin(new Insets(-5, 5, 5, 5));
        this.addHyperlinkListener(e -> {
            if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                if(Desktop.isDesktopSupported()) {
                    try {
                        Desktop.getDesktop().browse(e.getURL().toURI());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 7, 7);
        super.paintComponent(g2);
    }

    @Override
    public void setText(String t) {
        super.setText("<html><head><style> a { color: #2a5885; } p { font-family: \"JetBrains Mono\"; } </style></head>" + t + "</html>");
    }
}