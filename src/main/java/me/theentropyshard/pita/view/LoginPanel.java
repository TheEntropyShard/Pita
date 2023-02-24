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
import me.theentropyshard.pita.Utils;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class LoginPanel extends JPanel {
    private final PitaTextField loginField;
    private final PitaTextField schoolNameField;
    private final PitaTextField schoolDomainField;
    private final PitaPasswordField passwordField;
    private final Button loginButton;

    private int currentFocusedComponent = 1;

    public LoginPanel(Callback callback) {
        this.setLayout(new MigLayout("wrap", "push[center]push", "push[]25[]10[]10[]10[]25[]push"));

        Action action = new AbstractAction("ENTER") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int n = ++currentFocusedComponent;

                if(n == 5) {
                    ActionListener[] actionListeners = LoginPanel.this.loginButton.getActionListeners();
                    for(ActionListener l : actionListeners) {
                        l.actionPerformed(new ActionEvent(LoginPanel.this.loginButton, (int) (Math.random() * 10000), ""));
                    }
                    n = currentFocusedComponent = 1;
                }

                LoginPanel.this.getComponent(n).requestFocus();
            }
        };
        this.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "ENTER");
        this.getActionMap().put("ENTER", action);

        JLabel label = new JLabel("Сетевой Город. Образование");
        label.setFont(new Font("sansserif", Font.BOLD, 30));
        label.setForeground(new Color(7, 164, 121));
        this.add(label);

        String width = String.format("w %d", label.getPreferredSize().width);

        this.loginField = new PitaTextField();
        this.loginField.setPrefixIcon(Utils.loadIcon("/mail.png"));
        this.loginField.setHint("Логин");
        this.add(this.loginField, width);

        this.schoolNameField = new PitaTextField();
        this.schoolNameField.setPrefixIcon(Utils.loadIcon("/school.png"));
        this.schoolNameField.setHint("Имя/ID школы");
        this.add(this.schoolNameField, width);

        this.schoolDomainField = new PitaTextField();
        this.schoolDomainField.setPrefixIcon(Utils.loadIcon("/browser.png"));
        this.schoolDomainField.setHint("Домен дневника");
        this.add(this.schoolDomainField, width);

        this.passwordField = new PitaPasswordField();
        this.passwordField.setPrefixIcon(Utils.loadIcon("/pass.png"));
        this.passwordField.setHint("Пароль");
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
}
