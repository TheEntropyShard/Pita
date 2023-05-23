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

package me.theentropyshard.pita.utils.credentials;

import me.theentropyshard.pita.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

public final class CredentialsManager {
    private static final Logger LOG = LogManager.getLogger(CredentialsManager.class);

    private static File credentialsFile;

    public static void init(File dir) {
        CredentialsManager.credentialsFile = Utils.makeFile(new File(dir, "credentials.dat"));
    }

    public static void saveCredentials(Credentials c) {
        if (CredentialsManager.credentialsFile != null) {
            try {
                FileOutputStream fos = new FileOutputStream(CredentialsManager.credentialsFile);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(c);
                oos.close();
            } catch (IOException e) {
                LOG.warn("Не удалось сохранить данные для входа", e);
            }
        } else {
            LOG.debug("credentialsFile is null. Forgot to call init?");
        }
    }

    public static Credentials loadCredentials() {
        if (CredentialsManager.credentialsFile != null) {
            if (CredentialsManager.credentialsFile.length() != 0L) {
                try {
                    FileInputStream fis = new FileInputStream(CredentialsManager.credentialsFile);
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    return (Credentials) ois.readObject();
                } catch (IOException | ClassNotFoundException | ClassCastException e) {
                    LOG.warn("Не удалось загрузить данные для входа", e);
                }
            }
        } else {
            LOG.debug("credentialsFile is null. Forgot to call init?");
        }

        return null;
    }

    private CredentialsManager() {
        throw new UnsupportedOperationException("Class CredentialsManager should not be instantiated");
    }
}
