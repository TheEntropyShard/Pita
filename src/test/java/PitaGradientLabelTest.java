import me.theentropyshard.pita.model.gui.components.PitaGradientLabel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.InputStream;

public class PitaGradientLabelTest {
    public static void main(String[] args) {
        Font font = new Font("Arial", Font.BOLD, 16);

        try {
            InputStream stream = PitaGradientLabelTest.class.getResourceAsStream("/JetBrainsMono-Bold.ttf");
            font = Font.createFont(Font.TRUETYPE_FONT, stream);
            font = font.deriveFont(16.0f);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String text = "Some really long text here to see the gradient";

        PitaGradientLabel blueLabel = new PitaGradientLabel(text);
        blueLabel.setFont(font);
        blueLabel.setLeftColor(Color.decode("#000069"));
        blueLabel.setRightColor(Color.decode("#0000C3"));

        PitaGradientLabel greenLabel = new PitaGradientLabel(text);
        greenLabel.setFont(font);
        greenLabel.setLeftColor(Color.decode("#064F0A"));
        greenLabel.setRightColor(Color.decode("#228829"));

        PitaGradientLabel redLabel = new PitaGradientLabel(text);
        redLabel.setFont(font);
        redLabel.setLeftColor(Color.decode("#690000"));
        redLabel.setRightColor(Color.decode("#A80000"));

        PitaGradientLabel yellowLabel = new PitaGradientLabel(text);
        yellowLabel.setFont(font);
        yellowLabel.setLeftColor(Color.decode("#7D7D00"));
        yellowLabel.setRightColor(Color.decode("#C3C300"));

        JPanel labelsPanel = new JPanel();
        labelsPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        labelsPanel.setLayout(new BoxLayout(labelsPanel, BoxLayout.Y_AXIS));

        labelsPanel.add(blueLabel);
        labelsPanel.add(greenLabel);
        labelsPanel.add(redLabel);
        labelsPanel.add(yellowLabel);

        JFrame frame = new JFrame("PitaGradientLabel Test");
        frame.add(labelsPanel);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
