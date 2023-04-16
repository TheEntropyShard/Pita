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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
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
    private Map<String, Color> colors;
    private String lastTheme;

    public ThemeManager() {
        this.colors = new HashMap<>();
    }

    @SuppressWarnings("unchecked")
    public void loadTheme(String themeName) {
        this.colors.clear();

        File themeFile = new File(Pita.getPita().getThemesDir(), themeName + ".json");
        if(!themeFile.exists()) {
            Pita.getPita().getLogger().warn("Тема {} не существует (файл {})", themeName, themeFile);
            if(this.saveDefaultTheme()) {
                Pita.getPita().getLogger().warn("Была сохранена стандартная тема");
                this.loadTheme("green");
            }
        }

        JsonDeserializer<Map<String, Color>> deserializer = (element, type, context) -> {
            Map<String, Color> colors = new HashMap<>();

            for(Map.Entry<String, JsonElement> entry : element.getAsJsonObject().entrySet()) {
                colors.put(entry.getKey(), Color.decode(entry.getValue().getAsString()));
            }

            return colors;
        };

        try(FileReader reader = new FileReader(themeFile)) {
            Gson gson = new GsonBuilder().registerTypeAdapter(Map.class, deserializer).create();
            this.colors = gson.fromJson(reader, Map.class);
        } catch (IOException e) {
            Pita.getPita().getLogger().warn("Не удалось загрузить тему", e);
            return;
        }


        this.lastTheme = themeName;
    }

    public void hotReload() {
        this.loadTheme(this.lastTheme);
    }

    public boolean saveDefaultTheme() {
        String json = Utils.readFile(ThemeManager.class.getResourceAsStream("/themes/green.json"));
        try {
            File file = Utils.makeFile(new File(Pita.getPita().getThemesDir(), "green.json"));
            Files.write(file.toPath(), json.getBytes(StandardCharsets.UTF_8), StandardOpenOption.WRITE);
            return true;
        } catch (IOException e) {
            Pita.getPita().getLogger().warn("Не удалось записать стандартную тему", e);
            return false;
        }
    }

    public Color getColor(String name) {
        return this.colors.get(name);
    }
}
