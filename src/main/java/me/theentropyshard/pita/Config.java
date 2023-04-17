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

    @SuppressWarnings("unchecked")
    public static void load() {
        File dir = Pita.getPita().getPitaDir();
        Config.configFile = Utils.makeFile(new File(dir, "config.json"));
        if(Config.configFile.length() == 0L) {
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

    public static boolean getBoolean(String s) {
        return (boolean) Config.config.get(s);
    }

    public static int getInt(String s) {
        return (int) Config.config.get(s);
    }

    public static String getString(String s) {
        return (String) Config.config.get(s);
    }
}
