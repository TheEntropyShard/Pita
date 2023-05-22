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
import me.theentropyshard.pita.Pita;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public final class View {
    public View() {

        /*this.root.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "F1");
        this.root.getActionMap().put("F1", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Pita.getPita().getThemeManager().hotReload();
                View.getView().getRoot().repaint();
            }
        });
        this.root.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "F2");
        this.root.getActionMap().put("F2", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Config.reload();
                Pita.getPita().getThemeManager().loadTheme(Config.getString("selectedTheme"));
                View.getView().getRoot().repaint();
            }
        });*/
    }

    public CardLayout getRootLayout() {
        return null;
    }

    public JPanel getRoot() {
        return null;
    }

    public StudentView getMainPanel() {
        return null;
    }

    public JFrame getFrame() {
        return AppWindow.window;
    }

    private static View view;

    public static View getView() {
        return view;
    }
}
