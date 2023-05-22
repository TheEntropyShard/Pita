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
import me.theentropyshard.pita.controller.LoginController;
import me.theentropyshard.pita.controller.StudentController;
import me.theentropyshard.pita.service.LoginService;

import javax.swing.*;
import java.awt.*;

public class AppWindow extends JFrame {
    public static AppWindow window;

    private final CardLayout rootLayout;
    private final JPanel root;

    private final LoginView loginView;
    private final LoginController loginController;

    private final StudentView studentView;
    private final StudentController studentController;

    private String lastView;

    public AppWindow() {
        super("Pita");

        AppWindow.window = this;

        this.rootLayout = new CardLayout();
        this.root = new JPanel(this.rootLayout);

        this.loginView = new LoginView();
        this.loginController = new LoginController(new LoginService(), this.loginView);

        this.studentView = new StudentView();
        this.studentController = new StudentController(this.studentView);

        this.root.add(this.loginView, LoginView.class.getName());
        this.root.add(this.studentView, StudentView.class.getName());

        boolean startMaximized = Config.getBoolean("startMaximized", false);
        if (!startMaximized) {
            int width = Config.getInt("defaultWidth", 1280);
            int height = Config.getInt("defaultHeight", 720);
            this.root.setPreferredSize(new Dimension(width, height));
            this.add(this.root, BorderLayout.CENTER);
            this.pack();
        } else {
            this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
            this.add(this.root, BorderLayout.CENTER);
        }

        this.setGlassPane(new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(255, 255, 255, 127));
                g.fillRect(0, 0, this.getWidth(), this.getHeight());
                super.paintComponent(g);
            }
        });
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    private boolean isJustLaunched() {
        return this.lastView == null;
    }

    public void switchView(String name) {
        if (name == null) {
            return;
        }

        if (LoginView.class.getName().equals(name)) {
            this.loginController.resetView();
            if (this.isJustLaunched()) {
                this.loginController.loadCredentials();
            }
        } else if (StudentView.class.getName().equals(name)) {
            this.studentController.showPreferredView();
        }

        this.lastView = name;
        this.rootLayout.show(this.root, name);
    }
}
