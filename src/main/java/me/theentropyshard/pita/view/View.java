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

package me.theentropyshard.pita.view;

import me.theentropyshard.pita.Config;
import me.theentropyshard.pita.Pita;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public final class View {
    private final JFrame frame;

    private final CardLayout rootLayout;
    private final JPanel root;

    private final LoginPanel loginPanel;
    private final MainPanel mainPanel;

    public View() {
        if(view != null) {
            throw new IllegalStateException("View is already shown");
        }
        view = this;

        UIDefaults uiDefaults = UIManager.getDefaults();
        uiDefaults.put("activeCaption", new javax.swing.plaf.ColorUIResource(Color.gray));
        uiDefaults.put("activeCaptionText", new javax.swing.plaf.ColorUIResource(Color.white));

        this.frame = new JFrame("Pita");
        this.rootLayout = new CardLayout();
        this.root = new JPanel(this.rootLayout);

        this.root.setPreferredSize(new Dimension(UIConstants.DEFAULT_WIDTH, UIConstants.DEFAULT_HEIGHT));
        this.root.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "F1");
        this.root.getActionMap().put("F1", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Pita.getPita().getThemeManager().hotReload();
                View.getView().getRoot().repaint();
            }
        });
        this.root.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "F2");
        this.root.getActionMap().put("F2", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Config.reload();
                Pita.getPita().getThemeManager().loadTheme(Config.getString("selectedTheme"));
                View.getView().getRoot().repaint();
            }
        });
        this.mainPanel = new MainPanel();
        this.root.add(this.mainPanel, MainPanel.class.getSimpleName());

        this.loginPanel = new LoginPanel();

        LoginPanel.LoginButtonCallback callback = (address, schoolName, login, password, passwordHashed) -> {
            Thread t = new Thread(() -> {
                Pita.LoginResult result = Pita.getPita().login(address, schoolName, login, password, passwordHashed);

                switch(result) {
                    case OK:
                        SwingUtilities.invokeLater(() -> {
                            this.rootLayout.show(this.root, MainPanel.class.getSimpleName());
                            this.mainPanel.showComponents();
                            this.loginPanel.reset();
                        });
                        break;
                    case ERROR:
                        this.frame.getGlassPane().setVisible(true);

                        this.loginPanel.resetFields(false, true);
                        this.loginPanel.resetLoginButton();
                        new MessageDialog("Ошибка", "Произошла неизвестная ошибка");

                        this.frame.getGlassPane().setVisible(false);
                        break;
                    case WRONG_ADDRESS:
                        this.loginPanel.wrongAddress();
                        break;
                    case WRONG_SCHOOL_NAME:
                        this.loginPanel.schoolNotFound();
                        break;
                    case WRONG_CREDENTIALS:
                        this.loginPanel.wrongCredentials();
                        break;
                    default:
                        throw new RuntimeException("Unreachable");
                }
            });
            t.setName("LoginThread");
            t.start();
        };

        this.loginPanel.setLoginButtonPressedCallback(callback);
        this.root.add(this.loginPanel, LoginPanel.class.getSimpleName());

        this.rootLayout.show(this.root, LoginPanel.class.getSimpleName());

        this.frame.getRootPane().setGlassPane(new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(255, 255, 255, 127));
                g.fillRect(0, 0, this.getWidth(), this.getHeight());
                super.paintComponent(g);
            }
        });
        this.frame.add(this.root, BorderLayout.CENTER);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.pack();
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);
    }

    public CardLayout getRootLayout() {
        return this.rootLayout;
    }

    public JPanel getRoot() {
        return this.root;
    }

    public MainPanel getMainPanel() {
        return this.mainPanel;
    }

    public JFrame getFrame() {
        return this.frame;
    }

    private static View view;

    public static View getView() {
        return view;
    }
}
