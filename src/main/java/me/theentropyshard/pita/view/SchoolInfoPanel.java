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

import me.theentropyshard.pita.netschoolapi.NetSchoolAPI;
import me.theentropyshard.pita.netschoolapi.models.SchoolCard;
import me.theentropyshard.pita.view.component.GradientLabel;
import me.theentropyshard.pita.view.component.PScrollBar;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;

public class SchoolInfoPanel extends JPanel {

    public SchoolInfoPanel() {
        super(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new MigLayout("fillx, flowy", "[fill]"));

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setViewportView(panel);
        scrollPane.setVerticalScrollBar(new PScrollBar());

        this.add(scrollPane, BorderLayout.CENTER);

        /// Common info
        panel.add(new JPanel() {{
            this.setBackground(Color.WHITE);
            this.add(new GradientLabel("Основная информация", UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN) {{
                this.setFont(new Font("JetBrains Mono", Font.BOLD, 18));
            }});
        }});

        InfoPanel commonInfoPanel = new InfoPanel();

        JPanel infoPart1 = new JPanel(new GridLayout(1, 1));
        infoPart1.add(new JPanel(new FlowLayout(FlowLayout.LEFT)) {{
            this.setBackground(Color.WHITE);
            this.add(new GradientLabel("Вид организационно-правовой формы ОО", UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN));
        }});
        infoPart1.add(new GradientLabel("Теjejijpiajipwjipст", UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN));

        DataElementPanel dp1 = new DataElementPanel();
        dp1.setKey("Вид организационно-правовой формы ОО");
        dp1.setValue("wjdwdoodkokdowdokdkdokw");

        DataElementPanel dp2 = new DataElementPanel();
        dp2.setKey("dawdwdawdadwwadwdadddwdawdaswsadwdwd");
        dp2.setValue("ololololololololo");

        commonInfoPanel.addDataPanel(dp1);
        commonInfoPanel.addDataPanel(dp2);

        panel.add(commonInfoPanel);

        /// Management info
        panel.add(new JPanel() {{
            this.setBackground(Color.WHITE);
            this.add(new GradientLabel("Управление", UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN) {{
                this.setFont(new Font("JetBrains Mono", Font.BOLD, 18));
            }});
        }});
        panel.add(new JPanel() {{
            this.setBackground(Color.WHITE);
            this.add(new GradientLabel("Контактная информация", UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN) {{
                this.setFont(new Font("JetBrains Mono", Font.BOLD, 18));
            }});
        }});

        panel.add(new JPanel() {{
            this.setBackground(Color.WHITE);
            this.add(new GradientLabel("Другая информация", UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN) {{
                this.setFont(new Font("JetBrains Mono", Font.BOLD, 18));
            }});
        }});
        panel.add(new JPanel() {{
            this.setBackground(Color.WHITE);
            this.add(new GradientLabel("Оплата питания", UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN) {{
                this.setFont(new Font("JetBrains Mono", Font.BOLD, 18));
            }});
        }});
        panel.add(new JPanel() {{
            this.setBackground(Color.WHITE);
            this.add(new GradientLabel("Информация об интернет-соединении", UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN) {{
                this.setFont(new Font("JetBrains Mono", Font.BOLD, 18));
            }});
        }});
    }

    public void loadData() {
        if(false) {
            try {
                SchoolCard card = NetSchoolAPI.I.getSchoolInfo();
                System.out.println(card);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class InfoPanel extends JPanel {
        private final JPanel internalInfoPanel;

        public InfoPanel() {
            this.setLayout(new MigLayout("nogrid, fillx", "[]", ""));
            this.setOpaque(false);
            this.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    revalidate();
                }
            });
            this.setBorder(BorderFactory.createCompoundBorder(this.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));

            this.internalInfoPanel = new JPanel(new MigLayout("fillx, flowy", "[fill]")) {
                {
                    this.setOpaque(false);
                    this.addComponentListener(new ComponentAdapter() {
                        @Override
                        public void componentResized(ComponentEvent e) {
                            revalidate();
                        }
                    });
                }

                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(Color.WHITE);
                    g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), UIConstants.ARC_WIDTH, UIConstants.ARC_HEIGHT);
                    super.paintComponent(g2);
                }
            };
            this.add(this.internalInfoPanel, "grow");
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(UIConstants.NEAR_WHITE2);
            g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), UIConstants.ARC_WIDTH, UIConstants.ARC_HEIGHT);
            super.paintComponent(g2);
        }

        public void addDataPanel(DataElementPanel dataPanel) {
            this.internalInfoPanel.add(dataPanel);
        }
    }

    private static class DataElementPanel extends JPanel {
        private final GradientLabel keyLabel;
        private final GradientLabel valueLabel;

        public DataElementPanel() {
            this.keyLabel = new GradientLabel("", UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN);
            this.keyLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 14));

            this.valueLabel = new GradientLabel("", UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN);
            this.valueLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
            this.valueLabel.setBorder(new EmptyBorder(5, 5, 5, 5));

            this.setLayout(new GridLayout(1, 1));
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            panel.setBackground(Color.WHITE);
            panel.add(this.keyLabel);
            this.add(panel);
            this.add(this.valueLabel);
        }

        public void setKey(String key) {
            this.keyLabel.setText(key);
        }

        public void setValue(String value) {
            this.valueLabel.setText(value);
        }
    }
}
