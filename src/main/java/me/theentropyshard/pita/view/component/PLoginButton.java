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
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class PLoginButton extends JButton implements ActionListener {
    private final ThemeManager tm;

    private int targetSize;
    private float animationSize;
    private float alpha;
    private Point pressedPoint;
    private Color effectColor = new Color(255, 255, 255);
    private boolean loading;
    private int endAngle;

    private String oldText;

    private final Timer timer;

    public PLoginButton(String text) {
        super(text);

        this.oldText = text;

        this.setFocusPainted(false);
        this.setContentAreaFilled(false);
        this.setBorder(new EmptyBorder(0, 0, 0, 0));
        this.setForeground(new Color(250, 250, 250));

        this.tm = Pita.getPita().getThemeManager();

        this.setBackground(this.tm.getColor("mainColor"));
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));

        this.timer = new Timer(100, this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        BufferedImage img = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(new GradientPaint(0, 0, this.tm.getColor("darkAccentColor"), this.getWidth(), this.getHeight(), this.tm.getColor("lightAccentColor")));
        g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), UIConstants.ARC_WIDTH, UIConstants.ARC_HEIGHT);
        if (this.loading) {
            int w = this.getWidth();
            int h = this.getHeight();

            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(3));
            g2.rotate(Math.toRadians(this.endAngle), w >> 1, h >> 1);
            g2.drawArc(w / 2 - 9, h / 2 - 9, 18, 18, 0, -250);
        }
        g2.dispose();
        g.drawImage(img, 0, 0, null);
        super.paintComponent(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.endAngle += 10;
        this.repaint();
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
        if (loading) {
            this.oldText = this.getText();
            this.setText("");
            if (!this.timer.isRunning()) {
                this.timer.start();
            }
        } else {
            this.setText(this.oldText);
            if (this.timer.isRunning()) {
                this.timer.stop();
            }
        }
    }

    public boolean isLoading() {
        return this.loading;
    }

    public int getTargetSize() {
        return this.targetSize;
    }

    public void setTargetSize(int targetSize) {
        this.targetSize = targetSize;
    }

    public float getAnimationSize() {
        return this.animationSize;
    }

    public void setAnimationSize(float animationSize) {
        this.animationSize = animationSize;
    }

    public float getAlpha() {
        return this.alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public Point getPressedPoint() {
        return this.pressedPoint;
    }

    public void setPressedPoint(Point pressedPoint) {
        this.pressedPoint = pressedPoint;
    }

    public Color getEffectColor() {
        return this.effectColor;
    }

    public void setEffectColor(Color effectColor) {
        this.effectColor = effectColor;
    }
}
