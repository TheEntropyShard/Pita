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

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {
    public static final int TEXT_FIELD_FONT_SIZE = 16;

    private final CardLayout layout;
    private final JPanel root;

    private final LoginPanel loginPanel;
    private final ContentPanel contentPanel;

    public View() {
        super("Pita");

        this.layout = new CardLayout();
        this.root = new JPanel(this.layout);

        this.contentPanel = new ContentPanel();
        this.root.add(this.contentPanel, ContentPanel.class.getSimpleName());

        this.loginPanel = new LoginPanel(args -> {
            Thread t = new Thread(() -> {
                synchronized (Pita.class) {
                    Pita.getPita().saveSchoolDomainAndName(args[2], args[3]);
                    try {
                        Pita.getPita().getAPI().login(
                                args[0],
                                args[1],
                                args[2],
                                args[3]
                        );
                        showContentPanel();
                        contentPanel.loadComponents();
                    } catch (RuntimeException e) {
                        e.printStackTrace();
                        errorWhileLogin(e);
                    }
                }
            });
            t.start();
        });
        this.root.add(this.loginPanel, LoginPanel.class.getSimpleName());

        this.showLoginPanel();

        this.add(this.root, BorderLayout.CENTER);
        this.pack();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(800, 600));
        this.setLocationRelativeTo(null);
    }

    public void errorWhileLogin(Exception e) {
        this.loginPanel.errorWhileLogin(e);
    }

    public void showLoginPanel() {
        this.loginPanel.fixButton();
        this.layout.show(this.root, LoginPanel.class.getSimpleName());
    }

    public void showContentPanel() {
        this.layout.show(this.root, ContentPanel.class.getSimpleName());
        this.loginPanel.clearFields();
    }

    public LoginPanel getLoginPanel() {
        return this.loginPanel;
    }
}
