/*      NetSchoolAPI. A simple API client for NetSchool by irTech
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

import me.theentropyshard.pita.Callback;
import me.theentropyshard.pita.Pita;
import me.theentropyshard.pita.Utils;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginPanel extends JPanel {
    private final PitaTextField loginField;
    private final PitaTextField schoolNameField;
    private final PitaTextField schoolDomainField;
    private final PitaPasswordField passwordField;
    private final Button loginButton;
    private final JLabel labelErrorLogin;

    private int currentFocusedComponent = 1;

    public LoginPanel(Callback callback) {
        this.setLayout(new MigLayout("wrap", "push[center]push", "push[]25[]10[]10[]10[]25[]push"));

        Action action = new AbstractAction("ENTER") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!schoolNameField.getText().isEmpty() && !schoolDomainField.getText().isEmpty() && currentFocusedComponent != 4) {
                    currentFocusedComponent = 3;
                }

                int n = ++currentFocusedComponent;

                if(n == 5) {
                    ActionListener[] actionListeners = loginButton.getActionListeners();
                    for(ActionListener l : actionListeners) {
                        l.actionPerformed(new ActionEvent(loginButton, (int) (Math.random() * 10000), ""));
                    }
                    n = currentFocusedComponent = 1;
                }

                getComponent(n).requestFocus();
            }
        };
        this.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "ENTER");
        this.getActionMap().put("ENTER", action);

        JLabel labelSGO = new JLabel("Сетевой Город. Образование");
        labelSGO.setFont(new Font("sansserif", Font.BOLD, 30));
        labelSGO.setForeground(new Color(7, 164, 121));
        this.add(labelSGO);

        String width = String.format("w %d", labelSGO.getPreferredSize().width);

        this.labelErrorLogin = new JLabel();
        this.labelErrorLogin.setFont(new Font("sansserif", Font.BOLD, 30));
        this.labelErrorLogin.setForeground(new Color(164, 7, 44));
        this.labelErrorLogin.setVisible(false);

        this.loginField = new PitaTextField();
        this.loginField.setPrefixIcon(Utils.loadIcon("/mail.png"));
        this.loginField.setHint("Логин");
        this.loginField.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                currentFocusedComponent = 1;
            }
        });
        this.add(this.loginField, width);

        String[] data = Pita.getPita().getSchoolDomainAndName();

        this.schoolNameField = new PitaTextField();
        this.schoolNameField.setText(data[1]);
        this.schoolNameField.setPrefixIcon(Utils.loadIcon("/school.png"));
        this.schoolNameField.setHint("Имя/ID школы");
        this.schoolNameField.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                currentFocusedComponent = 2;
            }
        });
        this.add(this.schoolNameField, width);

        this.schoolDomainField = new PitaTextField();
        this.schoolDomainField.setText(data[0]);
        this.schoolDomainField.setPrefixIcon(Utils.loadIcon("/browser.png"));
        this.schoolDomainField.setHint("Домен дневника");
        this.schoolDomainField.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                currentFocusedComponent = 3;
            }
        });
        this.add(this.schoolDomainField, width);

        this.passwordField = new PitaPasswordField();
        this.passwordField.setPrefixIcon(Utils.loadIcon("/pass.png"));
        this.passwordField.setHint("Пароль");
        this.passwordField.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                currentFocusedComponent = 4;
            }
        });
        this.add(this.passwordField, width);

        this.loginButton = new Button();
        this.loginButton.setFocusPainted(false);
        this.loginButton.setBackground(new Color(7, 164, 121));
        this.loginButton.setForeground(new Color(250, 250, 250));
        this.loginButton.addActionListener(e -> {
            this.loginButton.setIcon(Utils.loadIcon("/loading.gif"));
            this.loginButton.setDisabledIcon(Utils.loadIcon("/loading.gif"));
            this.loginButton.setRolloverIcon(Utils.loadIcon("/loading.gif"));
            this.loginButton.setPressedIcon(Utils.loadIcon("/loading.gif"));
            this.loginButton.setSelectedIcon(Utils.loadIcon("/loading.gif"));
            this.loginButton.setEnabled(false);
            this.loginButton.setText("");
            callback.doWork(
                    this.loginField.getText(),
                    new String(this.passwordField.getPassword()),
                    this.schoolDomainField.getText(),
                    this.schoolNameField.getText()
            );
        });
        this.loginButton.setText("Войти");
        this.add(this.loginButton, width + ", h 40");
    }

    public void errorWhileLogin(Exception e) {
        this.add(this.labelErrorLogin);
        this.labelErrorLogin.setVisible(true);
        this.labelErrorLogin.setText("Произошла ошибка во время входа в систему");
        this.loginButton.setEnabled(true);
        this.loginButton.setText("Войти");
        this.loginButton.setIcon(null);
        this.loginButton.setDisabledIcon(null);
        this.loginButton.setRolloverIcon(null);
        this.loginButton.setPressedIcon(null);
        this.loginButton.setSelectedIcon(null);
        this.validate();
    }

    public void clearFields() {
        this.loginField.setText("");
        this.passwordField.setText("");
    }
}
