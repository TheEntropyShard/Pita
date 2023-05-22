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

import me.theentropyshard.pita.Pita;
import me.theentropyshard.pita.utils.ResourceManager;
import me.theentropyshard.pita.view.component.GradientLabel;
import me.theentropyshard.pita.view.component.LoginButton;
import me.theentropyshard.pita.view.component.PassField;
import me.theentropyshard.pita.view.component.TextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LoginView extends JPanel {
    public static final String SGO_TEXT = "Сетевой Город. Образование";
    public static final String SGO_LABEL_FONT_NAME = "sansserif";
    public static final int SGO_TEXT_SIZE = 30;

    private final TextField sgoAddressField;
    private final TextField schoolNameField;
    private final TextField loginField;
    private final PassField passwordField;
    private final LoginButton loginButton;

    public LoginView() {
        this.setLayout(new GridBagLayout());

        ThemeManager tm = Pita.getPita().getThemeManager();
        this.setBackground(tm.getColor("mainColor"));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 0, 5, 0);
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.VERTICAL;

        c.gridx = 0;
        c.gridy = 0;

        GradientLabel sgoLabel = new GradientLabel();
        sgoLabel.setText(LoginView.SGO_TEXT);
        sgoLabel.setFont(new Font(LoginView.SGO_LABEL_FONT_NAME, Font.BOLD, LoginView.SGO_TEXT_SIZE));
        this.add(sgoLabel, c);

        c.gridy = 1;
        this.sgoAddressField = new TextField();
        this.sgoAddressField.setPrefixIcon(ResourceManager.getIcon("/images/browser.png"));
        this.sgoAddressField.setHint("Сайт дневника");
        this.sgoAddressField.setPreferredSize(new Dimension(sgoLabel.getPreferredSize().width, this.sgoAddressField.getPreferredSize().height));
        this.add(this.sgoAddressField, c);

        c.gridy = 2;
        this.schoolNameField = new TextField();
        this.schoolNameField.setPrefixIcon(ResourceManager.getIcon("/images/school.png"));
        this.schoolNameField.setHint("Имя школы");
        this.schoolNameField.setPreferredSize(new Dimension(sgoLabel.getPreferredSize().width, this.sgoAddressField.getPreferredSize().height));
        this.add(this.schoolNameField, c);

        c.gridy = 3;
        this.loginField = new TextField();
        loginField.setPrefixIcon(ResourceManager.getIcon("/images/mail.png"));
        loginField.setHint("Логин");
        loginField.setPreferredSize(new Dimension(sgoLabel.getPreferredSize().width, this.sgoAddressField.getPreferredSize().height));
        this.add(loginField, c);

        c.gridy = 4;
        this.passwordField = new PassField();
        this.passwordField.setPrefixIcon(ResourceManager.getIcon("/images/pass.png"));
        this.passwordField.setHint("Пароль");
        this.passwordField.setPreferredSize(new Dimension(sgoLabel.getPreferredSize().width, this.sgoAddressField.getPreferredSize().height));
        this.add(this.passwordField, c);

        c.gridy = 5;
        this.loginButton = new LoginButton("Войти");
        this.loginButton.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
        this.loginButton.setPreferredSize(new Dimension(sgoLabel.getPreferredSize().width, this.sgoAddressField.getPreferredSize().height));
        this.add(this.loginButton, c);
    }

    @Override
    protected void paintComponent(Graphics g) {
        ThemeManager tm = Pita.getPita().getThemeManager();
        this.setBackground(tm.getColor("mainColor"));
        super.paintComponent(g);
    }

    public LoginButton getLoginButton() {
        return this.loginButton;
    }

    public TextField getSgoAddressField() {
        return this.sgoAddressField;
    }

    public TextField getSchoolNameField() {
        return this.schoolNameField;
    }

    public TextField getLoginField() {
        return this.loginField;
    }

    public PassField getPasswordField() {
        return this.passwordField;
    }
}
