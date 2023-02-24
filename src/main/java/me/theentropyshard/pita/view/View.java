package me.theentropyshard.pita.view;

import me.theentropyshard.pita.Pita;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {
    public static final int TEXT_FIELD_FONT_SIZE = 16;

    private final JPanel root;

    private final LoginPanel loginPanel;

    private final ContentPanel contentPanel;
    private final JPanel content;

    public View() {
        CardLayout layout = new CardLayout();
        this.root = new JPanel(layout);

        this.contentPanel = new ContentPanel();
        this.root.add(this.contentPanel, ContentPanel.class.getSimpleName());

        this.loginPanel = new LoginPanel(args -> {
            Thread t = new Thread(() -> {
                synchronized (Pita.class) {
                    Pita.getPita().getAPI().login(
                            args[0],
                            args[1],
                            args[2],
                            args[3]
                    );
                    layout.show(this.root, ContentPanel.class.getSimpleName());
                    contentPanel.addDefaultComponent();
                }
            });
            t.start();
        });
        this.root.add(this.loginPanel, LoginPanel.class.getSimpleName());

        this.content = this.contentPanel.getContent();

        layout.show(this.root, LoginPanel.class.getSimpleName());

        this.add(this.root, BorderLayout.CENTER);
        this.pack();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(800, 600));
        this.setLocationRelativeTo(null);
    }
}
