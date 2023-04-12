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

import me.theentropyshard.pita.view.PitaColors;
import me.theentropyshard.pita.view.UIConstants;
import me.theentropyshard.pita.view.component.ui.PComboBoxUI;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.plaf.basic.BasicComboPopup;
import java.awt.*;

public class PComboBox extends JComboBox<String> {
    private Icon prefixIcon;

    public PComboBox() {
        this.setUI(new PComboBoxUI());
        this.setBackground(PitaColors.ULTRA_LIGHT_COLOR);
        this.setEditable(false);
        this.setOpaque(false);

        this.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                getComponent(0).repaint();
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                getComponent(0).repaint();
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {

            }
        });

        this.setRenderer(new BasicComboBoxRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if(isSelected) {
                    this.setBackground(new Color(250, 250, 250));
                    this.setForeground(list.getSelectionForeground());
                } else {
                    this.setBackground(PitaColors.ULTRA_LIGHT_COLOR);
                    this.setForeground(list.getForeground());
                }
                list.setBackground(PitaColors.ULTRA_LIGHT_COLOR);
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });

        BasicComboPopup popup = (BasicComboPopup) this.getAccessibleContext().getAccessibleChild(0);
        popup.setBorder(new LineBorder(PitaColors.DARK_COLOR, 2, true) {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                if((this.thickness > 0) && (g instanceof Graphics2D)) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setStroke(new BasicStroke(2));
                    //g2.setPaint(new GradientPaint(0, 0, UIConstants.DARK_GREEN, width, height, UIConstants.LIGHT_GREEN));
                    g2.setColor(PitaColors.ULTRA_LIGHT_COLOR);
                    g2.fillRoundRect(0, 0, width - 1, height - 1, UIConstants.ARC_WIDTH, UIConstants.ARC_HEIGHT);
                    g2.drawRoundRect(0, 0, width - 1, height - 1, UIConstants.ARC_WIDTH, UIConstants.ARC_HEIGHT);
                }
            }
        });

        JScrollPane scrollPane = (JScrollPane) popup.getAccessibleContext().getAccessibleChild(0);
        scrollPane.setVerticalScrollBar(new PScrollBar());
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(PitaColors.ULTRA_LIGHT_COLOR);
        g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), UIConstants.ARC_WIDTH, UIConstants.ARC_HEIGHT);
        this.paintIcon(g);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(new GradientPaint(0, 0, PitaColors.DARK_COLOR, this.getWidth(), this.getHeight(), PitaColors.LIGHT_COLOR));
        g2.drawString(String.valueOf(this.getSelectedItem()), this.getInsets().left, this.getHeight() / 2 + g.getFontMetrics().getAscent() / 2 - 2);
    }

    protected void paintIcon(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if(this.prefixIcon != null) {
            int y = (this.getHeight() - this.prefixIcon.getIconHeight()) / 2;
            g2.drawImage(((ImageIcon) this.prefixIcon).getImage(), 10, y, this);
        }
    }

    private void initBorder() {
        int left = 15;
        if(this.prefixIcon != null) {
            left = this.prefixIcon.getIconWidth() + 15;
        }
        this.setBorder(BorderFactory.createEmptyBorder(10, left, 10, 15));
    }

    public void setPrefixIcon(Icon prefixIcon) {
        this.prefixIcon = prefixIcon;
        this.initBorder();
    }
}
