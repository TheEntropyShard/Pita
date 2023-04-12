

package me.theentropyshard.pita.view.component;

import me.theentropyshard.pita.view.PitaColors;
import me.theentropyshard.pita.view.UIConstants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * @see <a href="https://github.com/DJ-Raven/raven-project/blob/main/src/checkbox/JCheckBoxCustom.java">Raven Project</a>
 */
public class PCheckBox extends JCheckBox {
    private static final int BORDER = 5;

    public PCheckBox() {
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.setBackground(Color.WHITE);
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Color c = g2.getColor();
        g2.setColor(new Color(240, 240, 240));
        g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), UIConstants.ARC_WIDTH, UIConstants.ARC_HEIGHT);
        g2.setColor(c);
        int ly = (getHeight() - 16) / 2;
        g2.setColor(Color.GRAY);
        g2.fillRoundRect(6, ly, 15, 15, BORDER, BORDER);
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(7, ly + 1, 13, 13, BORDER, BORDER);
        if(isSelected()) {
            //  Draw Check icon
            int[] px = {8, 12, 18, 16, 12, 10};
            int[] py = {ly + 7, ly + 13, ly + 4, ly + 2, ly + 9, ly + 5};
            g2.setPaint(new GradientPaint(0, 0, PitaColors.DARK_COLOR, g2.getFontMetrics().stringWidth(getText()), getHeight(), PitaColors.LIGHT_COLOR));
            g2.fillPolygon(px, py, px.length);
        }
        g2.dispose();
    }
}
