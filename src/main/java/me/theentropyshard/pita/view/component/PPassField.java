package me.theentropyshard.pita.view.component;

import me.theentropyshard.pita.view.UIConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PPassField extends JPasswordField {
    private Icon prefixIcon;
    private String hint = "";
    private boolean isWrong;

    public PPassField() {
        this.setOpaque(false);
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.setBackground(UIConstants.NEAR_WHITE);
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
            g2.setColor(UIConstants.WRONG);
        } else {
            g2.setColor(UIConstants.NEAR_WHITE);
        }
        g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), UIConstants.ARC_WIDTH, UIConstants.ARC_HEIGHT);
        this.paintIcon(g2);
        super.paintComponent(g2);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(this.getPassword().length == 0) {
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
