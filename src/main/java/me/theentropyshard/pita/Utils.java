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
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public enum Utils {
    ;

    private static final Map<String, ImageIcon> ICON_CACHE = new HashMap<>();
    private static final Map<String, BufferedImage> IMAGE_CACHE = new HashMap<>();

    public static String getTodaysDateRussian() {
        LocalDate localDate = LocalDate.now(ZoneId.of("Europe/Moscow"));
        String month = localDate.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(String.format("dd %s yyyy г.", month));

        return localDate.format(formatter);
    }

    public static BufferedImage loadImage(String path) {
        if(Utils.IMAGE_CACHE.containsKey(path)) {
            return Utils.IMAGE_CACHE.get(path);
        }
        BufferedImage image;
        try {

            image = ImageIO.read(Objects.requireNonNull(Utils.class.getResourceAsStream(path)));
            Utils.IMAGE_CACHE.put(path, image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return image;
    }

    public static ImageIcon loadIcon(String path) {
        if(Utils.ICON_CACHE.containsKey(path)) {
            return Utils.ICON_CACHE.get(path);
        }

        ImageIcon icon;
        try {
            InputStream is = Objects.requireNonNull(Utils.class.getResourceAsStream(path));
            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            icon = new ImageIcon(bytes);
            Utils.ICON_CACHE.put(path, icon);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return icon;
    }
}
