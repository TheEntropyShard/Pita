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

import me.theentropyshard.netschoolapi.NetSchoolAPI;
import me.theentropyshard.netschoolapi.models.UploadLimits;
import me.theentropyshard.netschoolapi.models.UserModel;
import me.theentropyshard.pita.view.*;
import me.theentropyshard.pita.view.component.*;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicTextFieldUI;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class MailWritePanel extends JPanel {
    private final JTextArea textArea;
    private final PTextField subjectField;
    private final JPanel attachedFilesPanel;
    private final DataElementPanel receiversPanel;

    private final Set<String> receiverIds;
    private final Set<File> attachedFiles;
    private final Set<String> attachedFilesIds;

    public MailWritePanel() {
        super(new BorderLayout());

        this.receiverIds = new HashSet<>();
        this.attachedFiles = new HashSet<>();
        this.attachedFilesIds = new HashSet<>();

        this.addHierarchyListener(e -> {
            JComponent component = (JComponent) e.getSource();

            if((HierarchyEvent.SHOWING_CHANGED & e.getChangeFlags()) != 0 && !component.isShowing()) {
                this.clearFields();
            }
        });

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
        BorderPanel buttonsPanel = new BorderPanel();

        JPanel internalButtonsPanel = new JPanel();
        internalButtonsPanel.setBackground(Color.WHITE);
        internalButtonsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        SimpleButton sendButton = new SimpleButton("Отправить");
        sendButton.setRound(true);

        SimpleButton saveButton = new SimpleButton("Сохранить");
        saveButton.setRound(true);

        internalButtonsPanel.add(sendButton);
        internalButtonsPanel.add(saveButton);
        buttonsPanel.addComponent(internalButtonsPanel);
        panel.add(buttonsPanel);

        //
        BorderPanel controlsPanel = new BorderPanel();

        this.receiversPanel = new DataElementPanel();
        this.receiversPanel.setKey("Кому");
        this.receiversPanel.getValueLabel().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.receiversPanel.getValueLabel().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                View.getView().getFrame().getGlassPane().setVisible(true);

                MailChooseRecipientDialog dialog = new MailChooseRecipientDialog();

                Set<UserModel> recipients = dialog.getRecipients();

                StringJoiner joiner = new StringJoiner("; ");
                for(UserModel model : recipients) {
                    joiner.add(model.name + " " + model.organizationName);
                }
                receiversPanel.getValueLabel().setText(joiner.toString());

                receiverIds.addAll(recipients.stream().map(userModel -> userModel.id).collect(Collectors.toList()));

                View.getView().getFrame().getGlassPane().setVisible(false);
            }
        });

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
        this.subjectField.setSelectionColor(UIConstants.NEAR_WHITE);
        subjectPanel.setValueComponent(this.subjectField);

        DestUserPanel notifyPanel = new DestUserPanel();
        notifyPanel.setBackground(new Color(240, 240, 240));
        notifyPanel.setKey("Уведомить о прочтении");

        PCheckBox notifyCheckBox = new PCheckBox();
        notifyPanel.setValueComponent(notifyCheckBox);

        controlsPanel.addComponent(this.receiversPanel, "growx, width 0:0:100%");
        controlsPanel.addComponent(subjectPanel, "growx, width 0:0:100%");
        controlsPanel.addComponent(notifyPanel, "growx, width 0:0:100%");

        panel.add(controlsPanel, "gapy 4 0");

        //
        BorderPanel textAreaPanel = new BorderPanel();

        this.textArea = new JTextArea();
        this.textArea.setPreferredSize(new Dimension(this.textArea.getPreferredSize().width, 244));
        this.textArea.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));
        textAreaPanel.addComponent(this.textArea);

        panel.add(textAreaPanel, "gapy 4 0");

        BorderPanel attachedFilesPanel = new BorderPanel();

        SimpleButton addNewFileButton = new SimpleButton("Загрузить файл");
        addNewFileButton.setRound(true);

        this.attachedFilesPanel = new JPanel();
        this.attachedFilesPanel.setOpaque(false);
        this.attachedFilesPanel.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(UIConstants.DARK_GREEN, 1),
                        "Прикрепленные файлы",
                        TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
                        new Font("JetBrains Mono", Font.BOLD, 12),
                        UIConstants.DARK_GREEN
                )
        );
        this.attachedFilesPanel.setLayout(new BoxLayout(this.attachedFilesPanel, BoxLayout.PAGE_AXIS));

        addNewFileButton.addActionListener(e -> {
            View.getView().getFrame().getGlassPane().setVisible(true);

            FileUploadDialog dialog = new FileUploadDialog(View.getView().getFrame());
            File file = dialog.getSelectedFile();

            if(file == null) {
                return;
            }

            UploadLimits uploadLimits;
            try {
                uploadLimits = NetSchoolAPI.I.getUploadLimits();
            } catch (IOException ex) {
                ex.printStackTrace();
                uploadLimits = new UploadLimits() {{ this.fileSizeLimit = 8192; }};
            }
            if(file.length() >> 10 > uploadLimits.fileSizeLimit) {
                View.getView().getFrame().getGlassPane().setVisible(true);

                new MessageDialog("Ошибка", "Размер файла больше чем " + uploadLimits.fileSizeLimit + " КБ");

                View.getView().getFrame().getGlassPane().setVisible(false);

                return;
            }

            View.getView().getFrame().getGlassPane().setVisible(false);

            this.attachFile(file);
        });

        ActionListener buttonListener = e -> {
            if(this.checkFields()) {
                return;
            }

            boolean success = true;

            try {
                NetSchoolAPI.I.sendMessage(this.receiverIds, this.attachedFiles, this.attachedFilesIds, this.subjectField.getText(), this.textArea.getText(), notifyCheckBox.isSelected(),
                        e.getSource() == saveButton);
            } catch (IOException ex) {
                ex.printStackTrace();
                success = false;
            }

            MainPanel mainPanel = View.getView().getMainPanel();
            mainPanel.getMailPanel().loadData();
            mainPanel.getContentLayout().show(mainPanel.getContentPanel(), MailPanel.class.getSimpleName());

            View.getView().getFrame().getGlassPane().setVisible(true);

            if(success) {
                new MessageDialog("Внимание", "Ваше письмо успешно отправлено");
            } else {
                new MessageDialog("Ошибка", "Не удалось отправить письмо");
            }

            View.getView().getFrame().getGlassPane().setVisible(false);
        };

        sendButton.addActionListener(buttonListener);
        saveButton.addActionListener(buttonListener);

        attachedFilesPanel.addComponent(addNewFileButton);
        attachedFilesPanel.addComponent(this.attachedFilesPanel);

        panel.add(attachedFilesPanel, "gapy 4 0");
    }

    private boolean checkFields() {
        if(this.textArea.getText().isEmpty() && this.subjectField.getText().isEmpty()) {
            View.getView().getFrame().getGlassPane().setVisible(true);

            new MessageDialog("Ошибка", "Вы должны ввести хотя бы текст письма или тему");

            View.getView().getFrame().getGlassPane().setVisible(false);

            return true;
        }
        return false;
    }

    public void loadData() {

    }

    public void attachFileById(String name, String id) {
        this.attachedFilesIds.add(id);
        GradientLabel label = new GradientLabel(name, UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN);
        label.setFont(new Font("JetBrains Mono", Font.BOLD, 12));
        label.setBorder(new EmptyBorder(0, 5, 3, 0));
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                attachedFilesPanel.remove(label);
                attachedFilesIds.remove(id);
                revalidate();
            }
        });
        this.attachedFilesPanel.add(label);
        this.attachedFilesIds.add(id);
    }

    public void attachFile(File file) {
        GradientLabel label = new GradientLabel(file.getName(), UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN);
        label.setFont(new Font("JetBrains Mono", Font.BOLD, 12));
        label.setBorder(new EmptyBorder(0, 5, 3, 0));
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                attachedFilesPanel.remove(label);
                attachedFiles.remove(file);
                revalidate();
            }
        });
        this.attachedFilesPanel.add(label);
        this.attachedFiles.add(file);
        revalidate();
    }

    public void clearFields() {
        this.receiversPanel.setValue("");
        this.subjectField.setText("");
        this.textArea.setText("");
        this.attachedFilesPanel.removeAll();

        this.receiverIds.clear();
        this.attachedFiles.clear();
    }

    public void setReceivers(UserModel... models) {
        StringJoiner joiner = new StringJoiner("; ");
        for(UserModel model : models) {
            joiner.add(model.name);
        }
        receiversPanel.getValueLabel().setText(joiner.toString());
        List<String> collect = Arrays.stream(models).map(userModel -> userModel.id).collect(Collectors.toList());
        receiverIds.addAll(collect);
    }

    public void setSubject(String subject) {
        this.subjectField.setText(subject);
    }

    public void setMainText(String text) {
        this.textArea.setText(text);
    }

    public static class DestUserPanel extends JPanel {
        private final GradientLabel keyLabel;

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
            component.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
            component.setBorder(new EmptyBorder(5, 5, 5, 5));
            if(this.getComponentCount() == 2) {
                this.remove(1);
            }
            this.add(component);
        }
    }

    public static class DataElementPanel extends JPanel {
        private final GradientLabel keyLabel;
        private final GradientLabel valueLabel;

        public DataElementPanel() {
            this.keyLabel = new GradientLabel("", UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN);
            this.keyLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 14));

            this.valueLabel = new GradientLabel("", UIConstants.DARK_GREEN, UIConstants.LIGHT_GREEN) {
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

        public GradientLabel getValueLabel() {
            return this.valueLabel;
        }
    }
}
