package me.theentropyshard.pita.view.component;

import me.theentropyshard.pita.view.UIConstants;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class GradientPressEffectButton extends JButton {
    private final Animator animator;

    private int targetSize;
    private float animationSize;
    private float alpha;
    private Point pressedPoint;
    private Color effectColor = new Color(255, 255, 255);

    public GradientPressEffectButton(String text) {
        super(text);
        this.setFocusPainted(false);
        this.setContentAreaFilled(false);
        this.setBorder(new EmptyBorder(0, 0, 0, 0));
        this.setForeground(new Color(250, 250, 250));
        this.setBackground(Color.WHITE);
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                if(isEnabled()) {
                    targetSize = Math.max(getWidth(), getHeight()) * 2;
                    animationSize = 0;
                    pressedPoint = me.getPoint();
                    alpha = 0.5f;
                    if(animator.isRunning()) {
                        animator.stop();
                    }
                    animator.start();
                }
            }
        });

        this.animator = new Animator(700, new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                if (fraction > 0.5f) {
                    alpha = 1 - fraction;
                }
                animationSize = fraction * targetSize;
                repaint();
            }
        });
        this.animator.setAcceleration(0.5f);
        this.animator.setDeceleration(0.5f);
        this.animator.setResolution(0);
    }

    @Override
    protected void paintComponent(Graphics g) {
        BufferedImage img = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(new GradientPaint(0, 0, UIConstants.DARK_GREEN, this.getWidth(), this.getHeight(), UIConstants.LIGHT_GREEN));
        g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), UIConstants.ARC_WIDTH, UIConstants.ARC_HEIGHT);
        if (this.pressedPoint != null) {
            g2.setColor(this.effectColor);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, this.alpha));
            g2.fillOval((int) (this.pressedPoint.x - this.animationSize / 2), (int) (this.pressedPoint.y - this.animationSize / 2), (int) this.animationSize, (int) this.animationSize);
        }
        g2.dispose();
        g.drawImage(img, 0, 0, null);
        super.paintComponent(g);
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
