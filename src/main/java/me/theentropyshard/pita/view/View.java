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

import me.theentropyshard.pita.netschoolapi.NetSchoolAPI;

import javax.swing.*;
import java.awt.*;

public final class View {
    private final JFrame frame;

    private final CardLayout rootLayout;
    private final JPanel root;

    private final LoginPanel loginPanel;
    private final MainPanel mainPanel;

    public View() {
        if(view != null) {
            throw new IllegalStateException("View is already shown");
        }
        view = this;

        this.frame = new JFrame("Pita");
        this.rootLayout = new CardLayout();
        this.root = new JPanel(this.rootLayout);

        this.root.setPreferredSize(new Dimension(UIConstants.DEFAULT_WIDTH, UIConstants.DEFAULT_HEIGHT));

        this.loginPanel = new LoginPanel(NetSchoolAPI.I::login);
        this.root.add(this.loginPanel, LoginPanel.class.getSimpleName());

        this.mainPanel = new MainPanel();
        this.root.add(this.mainPanel, MainPanel.class.getSimpleName());

        this.rootLayout.show(this.root, LoginPanel.class.getSimpleName());

        this.frame.add(this.root, BorderLayout.CENTER);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.pack();
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);
    }

    public JPanel getRoot() {
        return this.root;
    }

    private static View view;

    public static View getView() {
        return view;
    }
}
