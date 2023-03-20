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

import me.theentropyshard.pita.Utils;
import me.theentropyshard.pita.view.component.LoginButton;
import me.theentropyshard.pita.view.component.PPassField;
import me.theentropyshard.pita.view.component.PTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class LoginPanel extends JPanel {
    public static final String SGO_TEXT = "Сетевой Город. Образование";
    public static final String SGO_LABEL_FONT_NAME = "sansserif";
    public static final int SGO_TEXT_SIZE = 30;

    public LoginPanel(LoginButtonCallback callback) {
        this.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 0, 5, 0);
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.VERTICAL;

        c.gridx = 0;
        c.gridy = 0;
        JLabel sgoLabel = new JLabel(LoginPanel.SGO_TEXT) {
            {
                this.setFont(new Font(LoginPanel.SGO_LABEL_FONT_NAME, Font.BOLD, LoginPanel.SGO_TEXT_SIZE));
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(new GradientPaint(0, 0, UIConstants.DARK_GREEN, this.getWidth(), this.getHeight(), UIConstants.LIGHT_GREEN));
                g2.drawString(this.getText(), 0, g2.getFontMetrics().getAscent());
            }
        };
        this.add(sgoLabel, c);

        c.gridy = 1;
        PTextField sgoAddressField = new PTextField();
        sgoAddressField.setPrefixIcon(Utils.getIcon("/images/browser.png"));
        sgoAddressField.setHint("Сайт дневника");
        sgoAddressField.setPreferredSize(new Dimension(sgoLabel.getPreferredSize().width, sgoAddressField.getPreferredSize().height));
        this.add(sgoAddressField, c);

        c.gridy = 2;
        PTextField schoolNameField = new PTextField();
        schoolNameField.setPrefixIcon(Utils.getIcon("/images/school.png"));
        schoolNameField.setHint("Имя школы");
        schoolNameField.setPreferredSize(new Dimension(sgoLabel.getPreferredSize().width, sgoAddressField.getPreferredSize().height));
        this.add(schoolNameField, c);

        c.gridy = 3;
        PTextField loginField = new PTextField();
        loginField.setPrefixIcon(Utils.getIcon("/images/mail.png"));
        loginField.setHint("Логин");
        loginField.setPreferredSize(new Dimension(sgoLabel.getPreferredSize().width, sgoAddressField.getPreferredSize().height));
        this.add(loginField, c);

        c.gridy = 4;
        PPassField passwordField = new PPassField();
        passwordField.setPrefixIcon(Utils.getIcon("/images/pass.png"));
        passwordField.setHint("Пароль");
        passwordField.setPreferredSize(new Dimension(sgoLabel.getPreferredSize().width, sgoAddressField.getPreferredSize().height));
        this.add(passwordField, c);

        c.gridy = 5;
        LoginButton loginButton = new LoginButton("Войти");
        loginButton.setPreferredSize(new Dimension(sgoLabel.getPreferredSize().width, sgoAddressField.getPreferredSize().height));
        loginButton.addActionListener(e -> {
            for(int i = 1; i < 5; i++) {
                Component com = this.getComponent(i);
                if(com instanceof JTextField) {
                    JTextField t = (JTextField) com;
                    if(t.getText().isEmpty()) {
                        com.setBackground(UIConstants.WRONG);
                        if(t instanceof PTextField) {
                            ((PTextField) t).setWrong(true);
                        } else if(t instanceof PPassField) {
                            ((PPassField) t).setWrong(true);
                        }
                        com.requestFocus();
                        return;
                    }
                }
            }

            loginButton.setLoading(true);
            loginButton.setEnabled(false);

            sgoAddressField.setText("");
            schoolNameField.setText("");
            loginField.setText("");
            passwordField.setText("");

            callback.buttonPressed(
                    sgoAddressField.getText(), schoolNameField.getText(),
                    loginField.getText(), new String(passwordField.getPassword())
            );
        });
        this.add(loginButton, c);

        this.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "ENTER");
        this.getActionMap().put("ENTER", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                KeyboardFocusManager m = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                m.focusNextComponent();
                if(m.getFocusOwner() == passwordField) {
                    for(int i = 1; i < 5; i++) {
                        Component com = getComponent(i);
                        if(com instanceof JTextField) {
                            JTextField t = (JTextField) com;
                            if(t.getText().isEmpty()) {
                                com.setBackground(UIConstants.WRONG);
                                if(t instanceof PTextField) {
                                    ((PTextField) t).setWrong(true);
                                } else if(t instanceof PPassField) {
                                    ((PPassField) t).setWrong(true);
                                }
                                com.requestFocus();
                                return;
                            }
                        }
                    }

                    loginButton.setLoading(true);
                    loginButton.setEnabled(false);

                    sgoAddressField.setText("");
                    schoolNameField.setText("");
                    loginField.setText("");
                    passwordField.setText("");

                    callback.buttonPressed(
                            sgoAddressField.getText(), schoolNameField.getText(),
                            loginField.getText(), new String(passwordField.getPassword())
                    );
                }
            }
        });
    }

    @FunctionalInterface
    public interface LoginButtonCallback {
        void buttonPressed(String address, String schoolName, String login, String password);
    }
}
