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

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

public enum Utils {
    ;

    public static BufferedImage getImage(String path) {
        try {
            return ImageIO.read(Objects.requireNonNull(Utils.class.getResourceAsStream(path)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Icon getIcon(String path) {
        ImageIcon icon = new ImageIcon();
        icon.setImage(Utils.getImage(path));
        return icon;
    }

    public static String readStream(InputStream is) {
        StringBuilder builder = new StringBuilder();
        Scanner scanner = new Scanner(is);
        while(scanner.hasNextLine()) {
            builder.append(scanner.nextLine());
        }
        scanner.close();
        return builder.toString();
    }
}
