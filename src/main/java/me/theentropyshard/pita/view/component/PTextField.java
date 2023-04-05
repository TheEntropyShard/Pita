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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PTextField extends JTextField {
    public static final String TEXT_FIELD_FONT = "JetBrains Mono";
    public static final Color TEXT_FIELD_TEXT_COLOR = Color.decode("#7A8C8D");
    public static final int TEXT_FIELD_FONT_SIZE = 16;

    private Color defaultColor = UIConstants.NEAR_WHITE2;
    private Color wrongColor = UIConstants.WRONG;

    private Icon prefixIcon;
    private String hint = "";
    private boolean isWrong;

    public PTextField() {
        this.setOpaque(false);
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.setForeground(PTextField.TEXT_FIELD_TEXT_COLOR);
        this.setFont(new Font(PTextField.TEXT_FIELD_FONT, Font.BOLD, PTextField.TEXT_FIELD_FONT_SIZE));
        this.setSelectionColor(new Color(75, 175, 152));
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(isWrong) {
                    setWrong(false);
                    repaint();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if(this.isWrong) {
            g2.setColor(this.wrongColor);
        } else {
            g2.setColor(this.defaultColor);
        }
        g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), UIConstants.ARC_WIDTH, UIConstants.ARC_HEIGHT);
        this.paintIcon(g2);
        super.paintComponent(g2);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(this.getText().length() == 0) {
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g.setColor(PTextField.TEXT_FIELD_TEXT_COLOR);
            g.drawString(this.hint, this.getInsets().left, this.getHeight() / 2 + g.getFontMetrics().getAscent() / 2 - 2);
        }
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


    public Color getDefaultColor() {
        return this.defaultColor;
    }

    public void setDefaultColor(Color defaultColor) {
        this.defaultColor = defaultColor;
    }

    public Color getWrongColor() {
        return this.wrongColor;
    }

    public void setWrongColor(Color wrongColor) {
        this.wrongColor = wrongColor;
    }

    public void setWrong(boolean wrong) {
        this.isWrong = wrong;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public void setPrefixIcon(Icon prefixIcon) {
        this.prefixIcon = prefixIcon;
        this.initBorder();
    }
}
