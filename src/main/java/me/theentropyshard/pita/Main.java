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

package me.theentropyshard.pita;

import me.theentropyshard.pita.utils.ResourceManager;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.MetalTheme;
import javax.swing.plaf.metal.OceanTheme;
import java.awt.*;

public class Main {
    public static void exit() {
        System.exit(0);
    }

    private static void initGUI() {
        if (GraphicsEnvironment.isHeadless()) {
            System.err.println("Your graphics environment is headless");
            Main.exit();
        }

        System.setProperty("sun.java2d.d3d", "false");
        System.setProperty("sun.java2d.noddraw", "true");

        UIManager.put("ComboBox.selectionBackground", new ColorUIResource(53, 170, 60));

        ResourceManager.registerFont(ResourceManager.getFont("JetBrainsMono-Regular.ttf"));
        ResourceManager.registerFont(ResourceManager.getFont("JetBrainsMono-Bold.ttf"));
    }

    public static void main(String[] args) {
        Main.initGUI();

        new Pita();
    }
}
