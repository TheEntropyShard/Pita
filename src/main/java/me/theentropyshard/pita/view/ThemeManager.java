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

import com.fasterxml.jackson.databind.ObjectMapper;
import me.theentropyshard.pita.Pita;
import me.theentropyshard.pita.Utils;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

public class ThemeManager {
    private final Map<String, Color> colors;
    private String lastTheme;

    public ThemeManager() {
        this.colors = new HashMap<>();
    }

    @SuppressWarnings({"rawtypes"})
    public void loadTheme(String themeName) {
        this.colors.clear();

        File themeFile = new File(Pita.getPita().getThemesDir(), themeName + ".json");
        if(!themeFile.exists()) {
            Pita.getPita().getLogger().warn("Тема '{}' не существует (файл {})", themeName, themeFile);
            if(this.saveDefaultThemes()) {
                Pita.getPita().getLogger().info("Были сохранены стандартные темы");
                this.loadTheme("green");
            } else {
                return;
            }
        }

        try(FileReader reader = new FileReader(themeFile)) {
            Map map = new ObjectMapper().readValue(reader, Map.class);
            for(Object entry : map.entrySet()) {
                Object key = ((Map.Entry) entry).getKey();
                Object value = ((Map.Entry) entry).getValue();

                this.colors.put((String) key, Color.decode((String) value));
            }
        } catch (IOException e) {
            Pita.getPita().getLogger().warn("Не удалось загрузить тему", e);
            return;
        }

        this.lastTheme = themeName;
    }

    public void hotReload() {
        this.loadTheme(this.lastTheme);
    }

    public boolean saveDefaultThemes() {
        String[] themes = {"blue", "dark", "green", "red", "yellow"};
        for(String theme : themes) {
            String json = Utils.readFile(ThemeManager.class.getResourceAsStream("/themes/" + theme + ".json"));
            try {
                File file = Utils.makeFile(new File(Pita.getPita().getThemesDir(), theme + ".json"));
                Files.write(file.toPath(), json.getBytes(StandardCharsets.UTF_8), StandardOpenOption.WRITE);
            } catch (IOException e) {
                Pita.getPita().getLogger().warn("Не удалось записать стандартную тему", e);
                return false;
            }
        }
        return true;
    }

    public Color getColor(String name) {
        return this.colors.get(name);
    }
}
