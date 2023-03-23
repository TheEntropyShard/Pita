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

import me.theentropyshard.pita.view.View;

import javax.swing.*;
import java.io.*;

public final class Pita {
    public static final File CWD = new File(System.getProperty("user.dir"));
    public static final File PITA_DIR = Utils.getAppDir("Pita");

    private final File credentialsFile;

    public Pita(String[] args) {
        if(pita != null) {
            throw new IllegalStateException("Only one Pita instance can exist at a time");
        }
        pita = this;

        this.credentialsFile = Utils.makeFile(new File(Pita.PITA_DIR, "credentials.dat"));

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {

        }));

        SwingUtilities.invokeLater(View::new);
    }

    public void saveCredentials(Credentials c) {
        try {
            FileOutputStream fos = new FileOutputStream(this.credentialsFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(c);
            oos.close();
        } catch (IOException e) {
            System.err.println("Не удалось сохранить данные для входа:");
            e.printStackTrace();
        }
    }

    public Credentials loadCredentials() {
        if(this.credentialsFile.length() != 0L) {
            try {
                FileInputStream fis = new FileInputStream(this.credentialsFile);
                ObjectInputStream ois = new ObjectInputStream(fis);
                return (Credentials) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Не удалось загрузить данные для входа:");
                e.printStackTrace();
            }
        }

        return null;
    }

    private static Pita pita;

    public static Pita getPita() {
        return pita;
    }
}
