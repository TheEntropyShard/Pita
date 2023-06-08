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

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CardLayoutPanel extends JPanel {
    private final CardLayout layout;
    private final Map<String, Component> componentMap;

    private Component showingComponent;

    public CardLayoutPanel() {
        this(0, 0);
    }

    public CardLayoutPanel(int hgap, int vgap) {
        super(new CardLayout(hgap, vgap));

        this.layout = (CardLayout) this.getLayout();
        this.componentMap = new HashMap<>();
    }

    public void showComponent(String name) {
        Component c = this.componentMap.get(name);
        if (c == null) {
            return;
        }

        this.showingComponent = c;
        this.layout.show(this, name);
    }

    public void addComponent(Component c, String name) {
        this.add(c, name);
        this.componentMap.put(name, c);
    }

    public Component getShowingComponent() {
        return this.showingComponent;
    }
}
