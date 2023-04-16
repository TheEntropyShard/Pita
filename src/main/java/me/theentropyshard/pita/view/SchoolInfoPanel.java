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

import me.theentropyshard.netschoolapi.NetSchoolAPI;
import me.theentropyshard.netschoolapi.models.SchoolCard;
import me.theentropyshard.pita.view.component.GradientLabel;
import me.theentropyshard.pita.view.component.PScrollBar;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
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
            this.add(new GradientLabel("Основная информация") {{
                this.setFont(new Font("JetBrains Mono", Font.BOLD, 18));
            }});
        }});

        /*InfoPanel commonInfoPanel = new InfoPanel();

        DataElementPanel dp1 = new DataElementPanel();
        dp1.setKey("Вид организационно-правовой формы ОО");
        dp1.setValue("wjdwdoodkokdowdokdkdokw");

        DataElementPanel dp2 = new DataElementPanel();
        dp2.setKey("dawdwdawdadwwadwdadddwdawdaswsadwdwd");
        dp2.setValue("ololololololololo");

        commonInfoPanel.addDataPanel(dp1);
        commonInfoPanel.addDataPanel(dp2);

        panel.add(commonInfoPanel);*/

        /// Management info
        panel.add(new JPanel() {{
            this.setBackground(Color.WHITE);
            this.add(new GradientLabel("Управление") {{
                this.setFont(new Font("JetBrains Mono", Font.BOLD, 18));
            }});
        }});

        // Contact info
        panel.add(new JPanel() {{
            this.setBackground(Color.WHITE);
            this.add(new GradientLabel("Контактная информация") {{
                this.setFont(new Font("JetBrains Mono", Font.BOLD, 18));
            }});
        }});

        // Other info
        panel.add(new JPanel() {{
            this.setBackground(Color.WHITE);
            this.add(new GradientLabel("Другая информация") {{
                this.setFont(new Font("JetBrains Mono", Font.BOLD, 18));
            }});
        }});

        // Food payment
        panel.add(new JPanel() {{
            this.setBackground(Color.WHITE);
            this.add(new GradientLabel("Оплата питания") {{
                this.setFont(new Font("JetBrains Mono", Font.BOLD, 18));
            }});
        }});

        // Internet connection info
        panel.add(new JPanel() {{
            this.setBackground(Color.WHITE);
            this.add(new GradientLabel("Информация об интернет-соединении") {{
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

    private static class DataElementPanel extends JPanel {
        private final GradientLabel keyLabel;
        private final GradientLabel valueLabel;

        public DataElementPanel() {
            this.keyLabel = new GradientLabel("");
            this.keyLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 14));

            this.valueLabel = new GradientLabel("") {
                @Override
                protected void paintComponent(Graphics g) {
                    Color oldColor = g.getColor();
                    g.setColor(new Color(240, 240, 240));
                    g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 7, 7);
                    g.setColor(oldColor);
                    super.paintComponent(g);
                }
            };
            this.valueLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
            this.valueLabel.setBorder(new EmptyBorder(5, 5, 5, 5));

            this.setLayout(new GridLayout(1, 1));
            this.setBackground(Color.WHITE);

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
