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

package me.theentropyshard.pita.utils;

import me.theentropyshard.pita.view.AppWindow;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;

public class SwingUtils {
    public static JDialog newDialog(String title, boolean modal, JComponent content) {
        JDialog dialog = new JDialog(AppWindow.window, title, modal);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.add(content, BorderLayout.CENTER);
        dialog.pack();
        dialog.setLocationRelativeTo(null);

        return dialog;
    }

    public static Icon getFileIcon(File f) {
         return FileSystemView.getFileSystemView().getSystemIcon(f);
    }

    private SwingUtils() {
        throw new UnsupportedOperationException("Class SwingUtils should not be instantiated");
    }
}
