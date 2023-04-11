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

import me.theentropyshard.netschoolapi.NetSchoolAPI;
import me.theentropyshard.netschoolapi.exceptions.AuthException;
import me.theentropyshard.netschoolapi.exceptions.SchoolNotFoundException;
import me.theentropyshard.pita.view.View;
import okhttp3.OkHttp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.io.*;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.logging.Level;

public final class Pita {
    private final Logger logger;

    private final File credentialsFile;
    private final File attachmentsDir;

    public Pita() {
        if(pita != null) {
            throw new IllegalStateException("Only one Pita instance can exist at a time");
        }
        pita = this;

        File pitaDir = Utils.getAppDir("Pita");

        File logsDir = Utils.makeDirectory(new File(pitaDir, "Logs"));
        System.setProperty("logPath", logsDir.toString());

        this.credentialsFile = Utils.makeFile(new File(pitaDir, "credentials.dat"));
        this.attachmentsDir = Utils.makeDirectory(new File(pitaDir, "Attachments"));

        java.util.logging.Logger.getLogger(OkHttp.class.getSimpleName()).setLevel(Level.FINE);
        this.logger = LogManager.getLogger(Pita.class);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            synchronized (NetSchoolAPI.I) {
                NetSchoolAPI.I.logout();
            }
        }));

        SwingUtilities.invokeLater(View::new);
    }

    public LoginResult login(String address, String schoolName, String login, String password, boolean passwordHashed) {
        try {
            NetSchoolAPI.I.login(address, schoolName, login, password, passwordHashed);
        } catch (UnknownHostException e) {
            this.logger.warn(e);
            return LoginResult.WRONG_ADDRESS;
        } catch (AuthException e) {
            this.logger.warn(e);
            return LoginResult.WRONG_CREDENTIALS;
        } catch (SchoolNotFoundException e) {
            this.logger.warn(e);
            return LoginResult.WRONG_SCHOOL_NAME;
        } catch (IOException e) {
            this.logger.warn(e);
            return LoginResult.ERROR;
        }

        if(!passwordHashed) {
            this.saveCredentials(new Credentials(
                    address, schoolName, login, Utils.md5(password.getBytes(Charset.forName("windows-1251")))
            ));
            this.logger.info("Пароль сохранен");
        }

        return LoginResult.OK;
    }

    public void saveCredentials(Credentials c) {
        try {
            FileOutputStream fos = new FileOutputStream(this.credentialsFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(c);
            oos.close();
        } catch (IOException e) {
            this.logger.warn("Не удалось сохранить данные для входа", e);
        }
    }

    public Credentials loadCredentials() {
        if(this.credentialsFile.length() != 0L) {
            try {
                FileInputStream fis = new FileInputStream(this.credentialsFile);
                ObjectInputStream ois = new ObjectInputStream(fis);
                return (Credentials) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                this.logger.warn("Не удалось загрузить данные для входа", e);
            }
        }

        return null;
    }

    public enum LoginResult {
        OK,
        ERROR,
        WRONG_ADDRESS,
        WRONG_SCHOOL_NAME,
        WRONG_CREDENTIALS
    }

    public Logger getLogger() {
        return this.logger;
    }

    public File getAttachmentsDir() {
        return this.attachmentsDir;
    }

    private static Pita pita;

    public static Pita getPita() {
        return pita;
    }
}
