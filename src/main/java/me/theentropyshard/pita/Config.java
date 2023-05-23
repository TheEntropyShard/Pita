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

import com.fasterxml.jackson.databind.ObjectMapper;
import me.theentropyshard.pita.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Objects;

public final class Config {
    private static Map<String, Object> config;
    private static File configFile;

    public static void init(File dir) {
        Config.configFile = Utils.makeFile(new File(dir, "config.json"));
    }

    @SuppressWarnings("unchecked")
    public static void load() {
        if (Config.configFile.length() == 0L) {
            Config.saveDefault();
        }

        try {
            Config.config = new ObjectMapper().readValue(Config.configFile, Map.class);
        } catch (IOException e) {
            Pita.getPita().getLogger().warn("Не удалось загрузить конфиг", e);
        }
    }

    public static void reload() {
        Config.load();
    }

    public static void saveDefault() {
        InputStream configDefault = Objects.requireNonNull(Config.class.getResourceAsStream("/config.json"));
        try {
            Files.copy(configDefault, Config.configFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            Pita.getPita().getLogger().warn("Не удалось сохранить стандартный конфиг", e);
        }
    }

    public static boolean getBoolean(String s, boolean def) {
        Object o = Config.config.get(s);
        if(o == null) {
            return def;
        }
        return (boolean) o;
    }

    public static boolean getBoolean(String s) {
        return Config.getBoolean(s, false);
    }

    public static int getInt(String s, int def) {
        Object o = Config.config.get(s);
        if(o == null) {
            return def;
        }
        return (int) o;
    }

    public static int getInt(String s) {
        return Config.getInt(s, 0);
    }

    public static String getString(String s, String def) {
        Object o = Config.config.get(s);
        if(o == null) {
            return def;
        }
        return (String) o;
    }

    public static String getString(String s) {
        return Config.getString(s, "");
    }
}
