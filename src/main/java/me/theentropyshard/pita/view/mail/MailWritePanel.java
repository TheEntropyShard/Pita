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

package me.theentropyshard.pita.view.mail;

import me.theentropyshard.pita.netschoolapi.NetSchoolAPI;
import me.theentropyshard.pita.view.UIConstants;
import me.theentropyshard.pita.view.View;
import me.theentropyshard.pita.view.component.GradientLabel;
import me.theentropyshard.pita.view.component.PScrollBar;
import me.theentropyshard.pita.view.component.PTextField;
import me.theentropyshard.pita.view.component.SimpleButton;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicTextFieldUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class MailWritePanel extends JPanel {

    private final JTextArea textArea;
    private final PTextField subjectField;

    public MailWritePanel() {
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

        //
        InfoPanel buttonsPanel = new InfoPanel();

        JPanel internalButtonsPanel = new JPanel();
        internalButtonsPanel.setBackground(Color.WHITE);
        internalButtonsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        SimpleButton sendButton = new SimpleButton("Отправить");
        sendButton.setRound(true);

        SimpleButton saveButton = new SimpleButton("Сохранить");
        saveButton.setRound(true);

        internalButtonsPanel.add(sendButton);
        internalButtonsPanel.add(saveButton);
        buttonsPanel.addDataPanel(internalButtonsPanel);
        panel.add(buttonsPanel);

        //
        InfoPanel controlsPanel = new InfoPanel();

        DestUserPanel receiversPanel = new DestUserPanel();
        receiversPanel.setKey("Кому");

        PTextField receiversField = new PTextField();
        receiversField.setDefaultColor(new Color(240, 240, 240));
        receiversField.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        receiversField.setEditable(false);
        receiversField.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("Pressed receiver field");
            }
        });
        receiversPanel.setValueComponent(receiversField);

        DestUserPanel subjectPanel = new DestUserPanel();
        subjectPanel.setKey("Тема");

        this.subjectField = new PTextField() {{
            this.setUI(new BasicTextFieldUI() {
                @Override
                protected void paintSafely(Graphics g) {
                    super.paintSafely(g);
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                    g2.setPaint(new GradientPaint(0, 0, UIConstants.DARK_GREEN, g2.getFontMetrics().stringWidth(getText()), getHeight(), UIConstants.LIGHT_GREEN));
                    g2.drawString(getText(), getInsets().left, getHeight() / 2 + g2.getFontMetrics().getAscent() / 2 - 2);
                }
            });
        }};
        this.subjectField.setDefaultColor(new Color(240, 240, 240));
        this.subjectField.setSelectionColor(UIConstants.NEAR_WHITE2);
        subjectPanel.setValueComponent(this.subjectField);

        DestUserPanel notifyPanel = new DestUserPanel();
        notifyPanel.setBackground(new Color(240, 240, 240));
        notifyPanel.setKey("Уведомить о прочтении");

        // See https://github.com/DJ-Raven/raven-project/blob/main/src/checkbox/JCheckBoxCustom.java
        JCheckBox notifyCheckBox = new JCheckBox() {
            {
                this.setCursor(new Cursor(Cursor.HAND_CURSOR));
                this.setOpaque(false);
                this.setBorder(new EmptyBorder(5, 5, 5, 5));
                this.setBackground(UIConstants.DARK_GREEN);
            }

            private final int border = 5;

            @Override
            public void paint(Graphics g) {
                super.paint(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int ly = (getHeight() - 16) / 2;
                if(isSelected()) {
                    if(isEnabled()) {
                        g2.setColor(this.getBackground());
                    } else {
                        g2.setColor(Color.GRAY);
                    }
                    g2.fillRoundRect(6, ly, 16, 16, this.border, this.border);
                    //  Draw Check icon
                    int[] px = {9, 13, 19, 17, 13, 11};
                    int[] py = {ly + 8, ly + 14, ly + 5, ly + 3, ly + 10, ly + 6};
                    g2.setColor(Color.WHITE);
                    g2.fillPolygon(px, py, px.length);
                } else {
                    g2.setColor(Color.GRAY);
                    g2.fillRoundRect(6, ly, 15, 16, this.border, this.border);
                    g2.setColor(Color.WHITE);
                    g2.fillRoundRect(6, ly + 1, 14, 14, this.border, this.border);
                }
                g2.dispose();
            }
        };
        notifyPanel.setValueComponent(notifyCheckBox);

        controlsPanel.addDataPanel(receiversPanel);
        controlsPanel.addDataPanel(subjectPanel);
        controlsPanel.addDataPanel(notifyPanel);

        panel.add(controlsPanel, "gapy 4 0");

        //
        InfoPanel textAreaPanel = new InfoPanel();

        this.textArea = new JTextArea();
        this.textArea.setPreferredSize(new Dimension(this.textArea.getPreferredSize().width, 244));
        this.textArea.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));
        textAreaPanel.addDataPanel(this.textArea);

        panel.add(textAreaPanel, "gapy 4 0");

        InfoPanel attachedFilesPanel = new InfoPanel();

        SimpleButton addNewFileButton = new SimpleButton("Загрузить файл");
        addNewFileButton.setRound(true);

        JPanel attachedFiles = new JPanel();
        attachedFiles.setOpaque(false);
        attachedFiles.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(UIConstants.DARK_GREEN, 1),
                        "Прикрепленные файлы",
                        TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
                        new Font("JetBrains Mono", Font.BOLD, 12),
                        UIConstants.DARK_GREEN
                )
        );
        attachedFiles.setLayout(new BoxLayout(attachedFiles, BoxLayout.PAGE_AXIS));

        addNewFileButton.addActionListener(e -> {
            GradientLabel label = new GradientLabel(new Random().nextInt() + "", UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN);
            label.setFont(new Font("JetBrains Mono", Font.BOLD, 12));
            label.setBorder(new EmptyBorder(0, 5, 3, 0));
            label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    attachedFiles.remove(label);
                    revalidate();
                }
            });
            attachedFiles.add(label);
            revalidate();
        });

        ActionListener buttonListener = e -> {
            if(this.checkFields()) {
                return;
            }

            List<String> receiverIds = new ArrayList<>();
            List<File> files = new ArrayList<>();
            try {
                NetSchoolAPI.I.sendMessage(receiverIds, files, subjectField.getText(), textArea.getText(), notifyCheckBox.isSelected(),
                        e.getSource() == saveButton);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        };

        sendButton.addActionListener(buttonListener);
        saveButton.addActionListener(buttonListener);

        attachedFilesPanel.addDataPanel(addNewFileButton);
        attachedFilesPanel.addDataPanel(attachedFiles);

        panel.add(attachedFilesPanel, "gapy 4 0");
    }

    private boolean checkFields() {
        if(this.textArea.getText().isEmpty() && this.subjectField.getText().isEmpty()) {
            View.getView().getFrame().getGlassPane().setVisible(true);

            JDialog dialog = new JDialog(View.getView().getFrame(), "Ошибка", true);

            InfoPanel infoPanel = new InfoPanel();

            JPanel panel = new JPanel();
            panel.setBackground(Color.WHITE);
            panel.setLayout(new MigLayout("fillx, flowy", "[fill]"));
            panel.add(infoPanel);

            GradientLabel label = new GradientLabel("Вы должны ввести хотя бы текст письма или тему", UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN);
            label.setFont(new Font("JetBrains Mono", Font.BOLD, 16));
            infoPanel.addDataPanel(label);

            dialog.add(panel, BorderLayout.CENTER);
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);

            View.getView().getFrame().getGlassPane().setVisible(false);

            return true;
        }
        return false;
    }

    public void loadData() {

    }

    public static class DestUserPanel extends CustomPanel {
        private final GradientLabel keyLabel;
        private JComponent valueComponent;

        public DestUserPanel() {
            this.keyLabel = new GradientLabel("", UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN);
            this.keyLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 14));

            this.setLayout(new GridLayout(1, 1));
            this.setBackground(Color.WHITE);

            JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            panel.setBackground(Color.WHITE);
            panel.add(this.keyLabel);
            this.add(panel);
        }

        public void setKey(String key) {
            this.keyLabel.setText(key);
        }

        public void setValueComponent(JComponent component) {
            this.valueComponent = component;
            this.valueComponent.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
            this.valueComponent.setBorder(new EmptyBorder(5, 5, 5, 5));
            if(this.getComponentCount() == 2) {
                this.remove(1);
            }
            this.add(this.valueComponent);
        }
    }
}
