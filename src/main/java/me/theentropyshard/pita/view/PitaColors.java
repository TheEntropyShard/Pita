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

import java.awt.*;

public enum PitaColors {
    ;

    public static final Color WRONG = new Color(243, 218, 218);

    public static final Color DARK_RED = new Color(105, 0, 0);
    public static final Color LIGHT_RED = new Color(168, 0, 0);
    public static final Color ULTRA_LIGHT_RED = new Color(255, 240, 238);

    public static final Color DARK_BLUE = new Color(0, 0, 105);
    public static final Color LIGHT_BLUE = new Color(0, 0, 195);
    public static final Color ULTRA_LIGHT_BLUE = new Color(240, 238, 255);

    public static final Color DARK_GREEN = new Color(6, 79, 10);
    public static final Color LIGHT_GREEN = new Color(34, 136, 41);
    public static final Color ULTRA_LIGHT_GREEN = new Color(240, 255, 238);

    public static final Color DARK_YELLOW = new Color(125, 125, 0);
    public static final Color LIGHT_YELLOW = new Color(195, 195, 0);
    public static final Color ULTRA_LIGHT_YELLOW = new Color(255, 254, 228);

    public static final Color DARK_COLOR = PitaColors.DARK_GREEN;
    public static final Color LIGHT_COLOR = PitaColors.LIGHT_GREEN;
    public static final Color ULTRA_LIGHT_COLOR = PitaColors.ULTRA_LIGHT_GREEN;
}
