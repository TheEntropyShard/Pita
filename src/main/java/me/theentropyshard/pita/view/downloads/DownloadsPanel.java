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

package me.theentropyshard.pita.view.downloads;

import me.theentropyshard.pita.utils.SwingUtils;
import me.theentropyshard.pita.view.BorderPanel;
import me.theentropyshard.pita.view.component.PScrollBar;
import me.theentropyshard.pita.view.component.PSimpleButton;
import net.miginfocom.swing.MigLayout;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class DownloadsPanel extends JPanel {
    private static final Logger LOG = LogManager.getLogger(DownloadsPanel.class);

    private final BorderPanel borderPanel;

    public DownloadsPanel() {
        this.borderPanel = new BorderPanel();
        this.borderPanel.getInternalPanel().setLayout(new FlowLayout(FlowLayout.LEFT));

        JPanel panel = new JPanel(new MigLayout("fillx", "", ""));
        panel.setBackground(Color.WHITE);
        panel.add(this.borderPanel, "grow");

        BorderPanel borderPanel2 = new BorderPanel();
        borderPanel2.getInternalPanel().setLayout(new FlowLayout(FlowLayout.CENTER));
        JPanel buttonPanel = new JPanel(new MigLayout("fillx", "", ""));
        buttonPanel.add(borderPanel2);
        buttonPanel.setBackground(Color.WHITE);
        PSimpleButton closeButton = new PSimpleButton("X");
        closeButton.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));
        closeButton.setRoundCorners(true);
        closeButton.addActionListener(e -> DownloadsPanel.this.setVisible(false));
        borderPanel2.addComponent(closeButton);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setViewportView(panel);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        PScrollBar scrollBar = new PScrollBar();
        scrollBar.setOrientation(JScrollBar.HORIZONTAL);
        scrollPane.setHorizontalScrollBar(scrollBar);

        this.setMinimumSize(new Dimension(500, 50));
        this.setLayout(new BorderLayout());

        this.add(scrollPane, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.EAST);
    }

    public void addFile(File f) {
        PSimpleButton fileButton = new PSimpleButton(f.getName());
        fileButton.setIcon(SwingUtils.getFileIcon(f));
        fileButton.setRoundCorners(true);
        fileButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!Desktop.isDesktopSupported()) {
                    return;
                }

                Desktop desktop = Desktop.getDesktop();
                int button = e.getButton();

                try {
                    if (button == MouseEvent.BUTTON1) {
                        desktop.open(f);
                    } else if (button == MouseEvent.BUTTON3) {
                        desktop.open(f.getParentFile());
                    }
                } catch (IOException ex) {
                    LOG.error(ex);
                }
            }
        });
        this.borderPanel.addComponent(fileButton);
    }
}
