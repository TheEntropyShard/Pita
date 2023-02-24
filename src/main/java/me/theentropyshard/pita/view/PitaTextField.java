package me.theentropyshard.pita.view;

import javax.swing.*;
import java.awt.*;

public class PitaTextField extends JTextField {
    private Icon prefixIcon;
    private String hint = "";

    public PitaTextField() {
        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(0, 0, 0, 0));
        setForeground(Color.decode("#7A8C8D"));
        setFont(new java.awt.Font("sansserif", Font.BOLD, View.TEXT_FIELD_FONT_SIZE));
        setSelectionColor(new Color(75, 175, 152));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(218, 243, 235));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 7, 7);
        paintIcon(g);
        super.paintComponent(g);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(getText().length() == 0) {
            int h = getHeight();
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            Insets ins = this.getInsets();
            FontMetrics fm = g.getFontMetrics();
            g.setColor(new Color(200, 200, 200));
            g.drawString(this.hint, ins.left, h / 2 + fm.getAscent() / 2 - 2);
        }
    }

    private void paintIcon(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if(this.prefixIcon != null) {
            Image prefix = ((ImageIcon) this.prefixIcon).getImage();
            int y = (this.getHeight() - this.prefixIcon.getIconHeight()) / 2;
            g2.drawImage(prefix, 10, y, this);
        }
    }

    private void initBorder() {
        int left = 15;
        if(this.prefixIcon != null) {
            left = this.prefixIcon.getIconWidth() + 15;
        }

        this.setBorder(BorderFactory.createEmptyBorder(10, left, 10, 15));
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public void setPrefixIcon(Icon prefixIcon) {
        this.prefixIcon = prefixIcon;
        this.initBorder();
    }
}
