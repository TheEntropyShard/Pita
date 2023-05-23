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

import me.theentropyshard.pita.netschoolapi.NetSchoolAPI_old;
import me.theentropyshard.pita.utils.Utils;
import me.theentropyshard.pita.utils.credentials.CredentialsManager;
import me.theentropyshard.pita.view.AppWindow;
import me.theentropyshard.pita.view.LoginView;
import me.theentropyshard.pita.view.ThemeManager;
import okhttp3.OkHttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.io.File;
import java.util.logging.Level;

public final class Pita {
    private final Logger logger;

    private final File configFile;
    private final File attachmentsDir;
    private final File themesDir;

    private final ThemeManager themeManager;
    private final File pitaDir;

    public Pita() {
        if (pita != null) {
            throw new IllegalStateException("Only one Pita instance can exist at a time");
        }
        pita = this;

        this.pitaDir = Utils.getAppDir("Pita");

        File logsDir = Utils.makeDirectory(new File(this.pitaDir, "Logs"));
        System.setProperty("logPath", logsDir.toString());

        this.configFile = Utils.makeFile(new File(this.pitaDir, "config.json"));
        this.attachmentsDir = Utils.makeDirectory(new File(this.pitaDir, "Attachments"));
        this.themesDir = Utils.makeDirectory(new File(this.pitaDir, "Themes"));

        java.util.logging.Logger.getLogger(OkHttpClient.class.getName()).setLevel(Level.FINE);
        this.logger = LogManager.getLogger(Pita.class);

        Config.init(this.pitaDir);
        Config.load();
        CredentialsManager.init(this.pitaDir);

        this.themeManager = new ThemeManager();
        this.themeManager.loadTheme(Config.getString("selectedTheme"));

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            synchronized (NetSchoolAPI_old.I) {
                NetSchoolAPI_old.I.logout();
            }
        }));

        SwingUtilities.invokeLater(() -> {
            AppWindow appWindow = new AppWindow();
            appWindow.switchView(LoginView.class.getName());
            appWindow.setVisible(true);
        });
    }

    public Logger getLogger() {
        return this.logger;
    }

    public File getPitaDir() {
        return this.pitaDir;
    }

    public File getConfigFile() {
        return this.configFile;
    }

    public File getAttachmentsDir() {
        return this.attachmentsDir;
    }

    public File getThemesDir() {
        return this.themesDir;
    }

    public ThemeManager getThemeManager() {
        return this.themeManager;
    }

    private static Pita pita;

    public static Pita getPita() {
        return pita;
    }
}
