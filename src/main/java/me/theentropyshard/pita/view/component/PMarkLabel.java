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

import me.theentropyshard.pita.view.component.ui.PMarkLabelUI;

import javax.swing.*;

public class PMarkLabel extends JLabel {
    private final boolean goodMark;

    public PMarkLabel(int mark) {
        this.setOpaque(false);
        this.setUI(new PMarkLabelUI());

        String strMark;
        switch(mark) {
            case 0:
                strMark = "Оценка";
                this.goodMark = true;
                break;
            case 1:
                strMark = "\u00b7"; // middle dot
                this.goodMark = false;
                break;
            case 2:
                strMark = String.valueOf(mark);
                this.goodMark = false;
                break;
            default:
                this.goodMark = true;
                strMark = String.valueOf(mark);
                break;
        }
        this.setText(strMark);
    }

    public boolean isGoodMark() {
        return this.goodMark;
    }
}
