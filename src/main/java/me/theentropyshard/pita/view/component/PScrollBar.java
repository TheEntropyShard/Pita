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

package me.theentropyshard.pita.view.component;

import me.theentropyshard.pita.view.component.ui.PScrollBarUI;

import javax.swing.*;
import java.awt.*;

public class PScrollBar extends JScrollBar {
    public PScrollBar() {
        this.setUI(new PScrollBarUI());
        this.setPreferredSize(new Dimension(5, 5));
        this.setUnitIncrement(25);
        this.setBackground(Color.WHITE);
    }
}
