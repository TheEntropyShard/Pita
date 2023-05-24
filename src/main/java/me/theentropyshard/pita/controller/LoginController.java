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

package me.theentropyshard.pita.controller;

import me.theentropyshard.pita.service.LoginService;
import me.theentropyshard.pita.utils.SwingUtils;
import me.theentropyshard.pita.utils.Utils;
import me.theentropyshard.pita.utils.credentials.Credentials;
import me.theentropyshard.pita.utils.credentials.CredentialsManager;
import me.theentropyshard.pita.view.AppWindow;
import me.theentropyshard.pita.view.LoginView;
import me.theentropyshard.pita.view.PitaColors;
import me.theentropyshard.pita.view.ShortMessagePanel;
import me.theentropyshard.pita.view.component.PLoginButton;
import me.theentropyshard.pita.view.component.PPassField;
import me.theentropyshard.pita.view.component.PTextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.nio.charset.Charset;

public class LoginController {
    private static final Logger LOG = LogManager.getLogger(LoginController.class);

    private final LoginService loginService;
    private final LoginView loginView;

    private boolean passwordHashed;

    public LoginController(LoginService loginService, LoginView loginView) {
        this.loginService = loginService;
        this.loginView = loginView;

        this.loginView.getLoginButton().addActionListener(e -> this.login());
        this.loginView.getPasswordField().addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                passwordHashed = false;
            }
        });

        InputMap inputMap = this.loginView.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "ENTER");
        ActionMap actionMap = this.loginView.getActionMap();
        actionMap.put("ENTER", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                KeyboardFocusManager m = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                m.focusNextComponent();
                if (m.getFocusOwner() == loginView.getPasswordField() || passwordHashed) {
                    login();
                }
            }
        });
    }

    public void resetView() {
        this.passwordHashed = false;
        this.resetFields(true, true);
        this.resetLoginButton();
    }

    public void resetLoginButton() {
        PLoginButton loginButton = this.loginView.getLoginButton();
        loginButton.setLoading(false);
        loginButton.setEnabled(true);
    }

    public void resetFields(boolean clear, boolean editable) {
        for (int i = 1; i < 5; i++) {
            JTextField t = (JTextField) this.loginView.getComponent(i);
            t.setEditable(editable);
            if (clear) {
                t.setText("");
            }
        }
    }

    public void loadCredentials() {
        Credentials c = CredentialsManager.loadCredentials();
        if (c != null) {
            this.loginView.getSgoAddressField().setText(c.getSchoolAddress());
            this.loginView.getSchoolNameField().setText(c.getSchoolName());
            this.loginView.getLoginField().setText(c.getLogin());
            this.loginView.getPasswordField().setText(c.getPasswordHash());
            this.passwordHashed = true;
        }
    }

    private void makeFieldValid(JTextField t) {
        if (t instanceof PTextField) {
            ((PTextField) t).setWrong(false);
        } else if (t instanceof PPassField) {
            ((PPassField) t).setWrong(false);
        }
        t.setBackground(PitaColors.ULTRA_LIGHT_COLOR);
    }

    private void makeFieldInvalid(JTextField t) {
        if (t instanceof PTextField) {
            ((PTextField) t).setWrong(true);
        } else if (t instanceof PPassField) {
            ((PPassField) t).setWrong(true);
        }
        t.setBackground(PitaColors.WRONG);
    }

    private void wrongField(String msg, JTextField textField) {
        LoginController.showErrorDialog(msg);
        textField.setText("");
        this.resetFields(false, true);
        this.makeFieldInvalid(textField);
    }

    public void login() {
        for (int i = 1; i < 5; i++) {
            JTextField t = (JTextField) this.loginView.getComponent(i);
            if (t.getText().isEmpty()) {
                t.requestFocus();
                this.makeFieldInvalid(t);
                return;
            }
            t.setEditable(false);
        }

        this.resetLoginButton();
        this.loginView.getLoginButton().setLoading(true);
        this.loginView.getLoginButton().setEnabled(false);

        PTextField addressField = this.loginView.getSgoAddressField();
        String baseUrl = addressField.getText();
        if (!Utils.isUrlValid(baseUrl)) {
            this.wrongField("Некорректный URL-адрес: " + baseUrl, addressField);
            return;
        }

        PTextField schoolNameField = this.loginView.getSchoolNameField();
        String schoolName = schoolNameField.getText();
        if (schoolName == null || schoolName.isEmpty()) {
            this.wrongField("Имя школы пусто или равно null", schoolNameField);
            return;
        }

        PTextField loginField = this.loginView.getLoginField();
        String login = loginField.getText();
        if (login == null || login.isEmpty()) {
            this.wrongField("Логин пуст или равен null", loginField);
            return;
        }

        PPassField passwordField = this.loginView.getPasswordField();
        char[] cPass = passwordField.getPassword();
        if (cPass == null || cPass.length == 0) {
            this.wrongField("Пароль пуст или равен null", passwordField);
            return;
        }

        String pass = new String(cPass);

        // TODO: move to CredentialsManager
        if (!this.passwordHashed) {
            String passHash = Utils.md5(pass.getBytes(Charset.forName("windows-1251")));
            if(passHash == null) {
                String msg = "Неожиданная ошибка при хешировании пароля";
                LOG.error(msg);
                LoginController.showErrorDialog(msg);
                return;
            }

            CredentialsManager.saveCredentials(new Credentials(baseUrl, schoolName, login, passHash));
            this.loginService.login(baseUrl, schoolName, login, passHash);
        } else {
            this.loginService.login(baseUrl, schoolName, login, pass);
        }
    }

    public static void showErrorDialog(String msg) {
        ShortMessagePanel smp = new ShortMessagePanel(msg);
        AppWindow.window.getGlassPane().setVisible(true);
        SwingUtils.newDialog("Ошибка", true, smp).setVisible(true);
        AppWindow.window.getGlassPane().setVisible(false);
    }
}
