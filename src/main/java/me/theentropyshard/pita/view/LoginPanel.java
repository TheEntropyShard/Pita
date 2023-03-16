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
import me.theentropyshard.pita.view.component.GradientPressEffectButton;
import me.theentropyshard.pita.view.component.PPassField;
import me.theentropyshard.pita.view.component.PTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginPanel extends JPanel {
    public static final String SGO_TEXT = "Сетевой Город. Образование";
    public static final String SGO_LABEL_FONT_NAME = "sansserif";
    public static final int SGO_TEXT_SIZE_LOGIN_PANEL = 30;

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
                this.setFont(new Font(LoginPanel.SGO_LABEL_FONT_NAME, Font.BOLD, LoginPanel.SGO_TEXT_SIZE_LOGIN_PANEL));
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
        PTextField loginField = new PTextField();
        loginField.setPrefixIcon(Utils.getIcon("/images/mail.png"));
        loginField.setHint("Логин");
        loginField.setPreferredSize(new Dimension(sgoLabel.getPreferredSize().width, loginField.getPreferredSize().height));
        this.add(loginField, c);

        c.gridy = 2;
        PTextField sgoAddressField = new PTextField();
        sgoAddressField.setPrefixIcon(Utils.getIcon("/images/browser.png"));
        sgoAddressField.setHint("Сайт дневника");
        sgoAddressField.setPreferredSize(new Dimension(sgoLabel.getPreferredSize().width, sgoAddressField.getPreferredSize().height));
        this.add(sgoAddressField, c);

        c.gridy = 3;
        PTextField schoolNameField = new PTextField();
        schoolNameField.setPrefixIcon(Utils.getIcon("/images/school.png"));
        schoolNameField.setHint("Имя школы");
        schoolNameField.setPreferredSize(new Dimension(sgoLabel.getPreferredSize().width, schoolNameField.getPreferredSize().height));
        this.add(schoolNameField, c);

        c.gridy = 4;
        PPassField passwordField = new PPassField();
        passwordField.setPrefixIcon(Utils.getIcon("/images/pass.png"));
        passwordField.setHint("Пароль");
        passwordField.setPreferredSize(new Dimension(sgoLabel.getPreferredSize().width, passwordField.getPreferredSize().height));
        this.add(passwordField, c);

        c.gridy = 5;
        GradientPressEffectButton loginButton = new GradientPressEffectButton("Войти");
        loginButton.setPreferredSize(new Dimension(sgoLabel.getPreferredSize().width, passwordField.getPreferredSize().height));
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                callback.buttonPressed(
                        loginField.getText(), sgoAddressField.getText(),
                        schoolNameField.getText(), new String(passwordField.getPassword())
                );
            }
        });
        this.add(loginButton, c);
    } //TODO make rotating loading. Try make it by drawing path

    @FunctionalInterface
    public interface LoginButtonCallback {
        void buttonPressed(String login, String address, String schoolName, String password);
    }
}
