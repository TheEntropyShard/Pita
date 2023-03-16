package me.theentropyshard.pita.view.component;

import me.theentropyshard.pita.view.UIConstants;

import javax.swing.*;
import java.awt.*;

public class PTextField extends JTextField {
    public static final String TEXT_FIELD_FONT = "sansserif";
    public static final Color TEXT_FIELD_TEXT_COLOR = Color.decode("#7A8C8D");
    public static final int TEXT_FIELD_FONT_SIZE = 16;

    private Icon prefixIcon;
    private String hint = "";

    public PTextField() {
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.setBackground(new Color(0, 0, 0, 0));
        this.setForeground(PTextField.TEXT_FIELD_TEXT_COLOR);
        this.setFont(new Font(PTextField.TEXT_FIELD_FONT, Font.BOLD, PTextField.TEXT_FIELD_FONT_SIZE));
        this.setSelectionColor(new Color(75, 175, 152));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(220, 243, 218));
        g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), UIConstants.ARC_WIDTH, UIConstants.ARC_HEIGHT);
        this.paintIcon(g);
        super.paintComponent(g);
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

    public void setHint(String hint) {
        this.hint = hint;
    }

    public void setPrefixIcon(Icon prefixIcon) {
        this.prefixIcon = prefixIcon;
        this.initBorder();
    }
}
