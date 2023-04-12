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

import me.theentropyshard.pita.Credentials;
import me.theentropyshard.pita.Pita;
import me.theentropyshard.pita.ResourceManager;
import me.theentropyshard.pita.view.component.GradientLabel;
import me.theentropyshard.pita.view.component.LoginButton;
import me.theentropyshard.pita.view.component.PPassField;
import me.theentropyshard.pita.view.component.PTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LoginPanel extends JPanel {
    public static final String SGO_TEXT = "Сетевой Город. Образование";
    public static final String SGO_LABEL_FONT_NAME = "sansserif";
    public static final int SGO_TEXT_SIZE = 30;

    public static final String ERROR_OCCURRED_TEXT = "Произошла ошибка: ";
    public static final String ERROR_FONT_NAME = "sansserif";
    public static final int ERROR_TEXT_SIZE = 18;

    private final PTextField sgoAddressField;
    private final PTextField schoolNameField;
    private final PTextField loginField;
    private final PPassField passwordField;
    private final LoginButton loginButton;
    private final GradientLabel errorLabel;

    private LoginButtonCallback callback;
    private boolean passwordHashed;

    public LoginPanel() {
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.WHITE);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 0, 5, 0);
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.VERTICAL;

        c.gridx = 0;
        c.gridy = 0;
        GradientLabel sgoLabel = new GradientLabel(PitaColors.DARK_COLOR, PitaColors.LIGHT_COLOR);
        sgoLabel.setText(LoginPanel.SGO_TEXT);
        sgoLabel.setFont(new Font(LoginPanel.SGO_LABEL_FONT_NAME, Font.BOLD, LoginPanel.SGO_TEXT_SIZE));
        this.add(sgoLabel, c);

        c.gridy = 1;
        this.sgoAddressField = new PTextField();
        this.sgoAddressField.setPrefixIcon(ResourceManager.getIcon("/images/browser.png"));
        this.sgoAddressField.setHint("Сайт дневника");
        this.sgoAddressField.setPreferredSize(new Dimension(sgoLabel.getPreferredSize().width, this.sgoAddressField.getPreferredSize().height));
        this.add(this.sgoAddressField, c);

        c.gridy = 2;
        this.schoolNameField = new PTextField();
        this.schoolNameField.setPrefixIcon(ResourceManager.getIcon("/images/school.png"));
        this.schoolNameField.setHint("Имя школы");
        this.schoolNameField.setPreferredSize(new Dimension(sgoLabel.getPreferredSize().width, this.sgoAddressField.getPreferredSize().height));
        this.add(this.schoolNameField, c);

        c.gridy = 3;
        this.loginField = new PTextField();
        loginField.setPrefixIcon(ResourceManager.getIcon("/images/mail.png"));
        loginField.setHint("Логин");
        loginField.setPreferredSize(new Dimension(sgoLabel.getPreferredSize().width, this.sgoAddressField.getPreferredSize().height));
        this.add(loginField, c);

        c.gridy = 4;
        this.passwordField = new PPassField();
        this.passwordField.setPrefixIcon(ResourceManager.getIcon("/images/pass.png"));
        this.passwordField.setHint("Пароль");
        this.passwordField.setPreferredSize(new Dimension(sgoLabel.getPreferredSize().width, this.sgoAddressField.getPreferredSize().height));
        this.passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                passwordHashed = false;
            }
        });
        this.add(this.passwordField, c);

        c.gridy = 5;
        this.loginButton = new LoginButton("Войти");
        this.loginButton.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
        this.loginButton.setPreferredSize(new Dimension(sgoLabel.getPreferredSize().width, this.sgoAddressField.getPreferredSize().height));
        this.loginButton.addActionListener(e -> this.loginButtonPressed(this.callback));
        this.add(this.loginButton, c);

        this.loadCredentials();

        c.gridy = 6;
        this.errorLabel = new GradientLabel(PitaColors.DARK_RED, PitaColors.LIGHT_RED);
        this.errorLabel.setFont(new Font(LoginPanel.ERROR_FONT_NAME, Font.BOLD, LoginPanel.ERROR_TEXT_SIZE));
        this.add(this.errorLabel, c);

        this.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "ENTER");
        this.getActionMap().put("ENTER", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                KeyboardFocusManager m = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                m.focusNextComponent();
                if(m.getFocusOwner() == passwordField || passwordHashed) {
                    loginButtonPressed(callback);
                }
            }
        });
    }

    private void loginButtonPressed(LoginButtonCallback callback) {
        for(int i = 1; i < 5; i++) {
            Component com = this.getComponent(i);
            if(com instanceof JTextField) {
                JTextField t = (JTextField) com;
                if(t.getText().isEmpty()) {
                    com.setBackground(PitaColors.WRONG);
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

        for(int i = 1; i < 5; i++) {
            JTextField t = (JTextField) this.getComponent(i);
            t.setEditable(false);
        }

        this.loginButton.setLoading(true);
        this.loginButton.setEnabled(false);

        callback.buttonPressed(
                this.sgoAddressField.getText(), this.schoolNameField.getText(),
                this.loginField.getText(), new String(this.passwordField.getPassword()),
                this.passwordHashed
        );
    }

    private void loadCredentials() {
        Credentials creds = Pita.getPita().loadCredentials();
        if(creds != null) {
            this.sgoAddressField.setText(creds.getSchoolAddress());
            this.schoolNameField.setText(creds.getSchoolName());
            this.loginField.setText(creds.getLogin());
            this.passwordField.setText(creds.getPasswordHash());
            this.passwordHashed = true;
        }
    }

    public void reset() {
        this.passwordHashed = false;
        this.resetFields(true, true);
        this.resetLoginButton();
    }

    public void resetFields(boolean clear, boolean editable) {
        for(int i = 1; i < 5; i++) {
            JTextField t = (JTextField) this.getComponent(i);
            t.setEditable(editable);
            if(clear) {
                t.setText("");
            }
        }
    }

    public void resetLoginButton() {
        this.loginButton.setLoading(false);
        this.loginButton.setEnabled(true);
    }

    public void wrongAddress() {
        this.sgoAddressField.setWrong(true);
        this.sgoAddressField.setEditable(true);
        this.sgoAddressField.requestFocus();
        this.resetLoginButton();
        this.repaint();
    }

    public void schoolNotFound() {
        this.schoolNameField.setWrong(true);
        this.schoolNameField.setEditable(true);
        this.schoolNameField.requestFocus();
        this.resetLoginButton();
        this.repaint();
    }

    public void wrongCredentials() {
        this.loginField.setWrong(true);
        this.loginField.setEditable(true);
        this.loginField.requestFocus();
        this.passwordField.setWrong(true);
        this.passwordField.setEditable(true);
        this.passwordField.setText("");
        this.resetLoginButton();
        this.repaint();
    }

    public void setLoginButtonPressedCallback(LoginButtonCallback callback) {
        this.callback = callback;
    }

    @FunctionalInterface
    public interface LoginButtonCallback {
        void buttonPressed(String address, String schoolName, String login, String password, boolean passwordHashed);
    }
}
