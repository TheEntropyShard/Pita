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

import me.theentropyshard.pita.view.LoginPanel;
import me.theentropyshard.pita.view.UIConstants;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public final class Pita {

    public Pita(String[] args) {
        if(pita != null) {
            throw new IllegalStateException("Only one Pita instance can exist at a time");
        }
        pita = this;

        JFrame frame = new JFrame();
        frame.setTitle("Pita");
        frame.add(new LoginPanel((login, address, schoolName, password) -> System.out.println("Tried to login")) {{
            this.setPreferredSize(new Dimension(UIConstants.DEFAULT_WIDTH, UIConstants.DEFAULT_HEIGHT));
        }});
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void saveSchoolDomainAndName(String domain, String schoolName) {
        File file = new File("data.txt");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(domain + "\n");
            writer.write(schoolName + "\n");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] getSchoolDomainAndName() {
        File file = new File("data.txt");
        if(file.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String domain = reader.readLine();
                String name = reader.readLine();
                return new String[]{
                        domain == null ? "" : domain,
                        name == null ? "" : name
                };
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new String[]{"", ""};
    }

    private static Pita pita;

    public static Pita getPita() {
        return pita;
    }
}
