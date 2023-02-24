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
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    public LoginPanel(Callback callback) {
        this.setLayout(new MigLayout("wrap", "push[center]push", "push[]25[]10[]10[]10[]25[]push"));

        JLabel label = new JLabel("Сетевой Город. Образование");
        label.setFont(new Font("sansserif", Font.BOLD, 30));
        label.setForeground(new Color(7, 164, 121));
        this.add(label);

        PitaTextField login = new PitaTextField();
        login.setPrefixIcon(new ImageIcon(getClass().getResource("/mail.png")));
        login.setHint("Логин");
        int i = label.getPreferredSize().width;
        String width = String.format("w %d", i);
        this.add(login, width);

        PitaTextField schoolName = new PitaTextField();
        schoolName.setPrefixIcon(new ImageIcon(getClass().getResource("/school.png")));
        schoolName.setHint("Имя/ID школы");
        this.add(schoolName, width);

        PitaTextField schoolDomain = new PitaTextField();
        schoolDomain.setPrefixIcon(new ImageIcon(getClass().getResource("/browser.png")));
        schoolDomain.setHint("Домен дневника");
        this.add(schoolDomain, width);

        PitaPasswordField pass = new PitaPasswordField();
        pass.setPrefixIcon(new ImageIcon(getClass().getResource("/pass.png")));
        pass.setHint("Пароль");
        this.add(pass, width);

        Button loginButton = new Button();
        loginButton.setFocusPainted(false);
        loginButton.setBackground(new Color(7, 164, 121));
        loginButton.setForeground(new Color(250, 250, 250));
        loginButton.addActionListener(e -> {
            loginButton.setIcon(new ImageIcon(getClass().getResource("/loading2.gif")));
            loginButton.setDisabledIcon(new ImageIcon(getClass().getResource("/loading2.gif")));
            loginButton.setRolloverIcon(new ImageIcon(getClass().getResource("/loading2.gif")));
            loginButton.setPressedIcon(new ImageIcon(getClass().getResource("/loading2.gif")));
            loginButton.setSelectedIcon(new ImageIcon(getClass().getResource("/loading2.gif")));
            loginButton.setEnabled(false);
            loginButton.setText("");
            callback.doWork(
                    login.getText(),
                    new String(pass.getPassword()),
                    schoolDomain.getText(),
                    schoolName.getText()
            );
        });
        loginButton.setText("Войти");
        this.add(loginButton, width + ", h 40");
    }
}
