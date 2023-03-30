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
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public enum ResourceManager {
    ;

    private static final Map<String, ImageIcon> ICON_CACHE = new HashMap<>();
    private static final Map<String, BufferedImage> IMAGE_CACHE = new HashMap<>();
    private static final Map<String, Font> FONT_CACHE = new HashMap<>();

    public static Icon getIcon(String path) {
        if(ResourceManager.ICON_CACHE.containsKey(path)) {
            return ResourceManager.ICON_CACHE.get(path);
        }

        ImageIcon icon;
        try {
            InputStream is = Objects.requireNonNull(ResourceManager.class.getResourceAsStream(path));
            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            icon = new ImageIcon(bytes);
            ResourceManager.ICON_CACHE.put(path, icon);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return icon;
    }

    public static BufferedImage getImage(String path) {
        if(ResourceManager.IMAGE_CACHE.containsKey(path)) {
            return ResourceManager.IMAGE_CACHE.get(path);
        }
        BufferedImage image;
        try {
            image = ImageIO.read(Objects.requireNonNull(ResourceManager.class.getResourceAsStream("/images/" + path)));
            ResourceManager.IMAGE_CACHE.put(path, image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return image;
    }

    public static Font registerFont(String path) {
        if(ResourceManager.FONT_CACHE.containsKey(path)) {
            return ResourceManager.FONT_CACHE.get(path);
        }
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(ResourceManager.class.getResourceAsStream("/fonts/" + path)));
            ResourceManager.FONT_CACHE.put(path, font);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
            return font;
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
