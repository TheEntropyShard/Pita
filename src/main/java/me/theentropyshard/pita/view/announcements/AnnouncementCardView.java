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

package me.theentropyshard.pita.view.announcements;

import me.theentropyshard.pita.model.announcements.AnnouncementModel;
import me.theentropyshard.pita.view.component.AttachedFilesPanel;
import me.theentropyshard.pita.view.BorderPanel;
import me.theentropyshard.pita.view.component.PTextPane;
import me.theentropyshard.pita.view.component.PGradientLabel;
import net.miginfocom.swing.MigLayout;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class AnnouncementCardView extends BorderPanel {
    public AnnouncementCardView(AnnouncementModel model, ActionListener listener) {
        this.getInternalPanel().setLayout(new MigLayout("nogrid, fillx", "[]", ""));

        PGradientLabel topicLabel = new PGradientLabel();
        topicLabel.setText("Тема: " + model.getSubject());
        topicLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
        this.add(topicLabel, "grow", 0);

        PGradientLabel timeLabel = new PGradientLabel();
        timeLabel.setText(model.getTime());
        timeLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
        this.add(timeLabel, "wrap", 1);

        PGradientLabel authorLabel = new PGradientLabel();
        authorLabel.setText("Автор: " + model.getAuthor());
        authorLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
        this.add(authorLabel, "wrap", 2);

        PTextPane textPane = new PTextPane();
        textPane.setText(model.getText());

        AttachedFilesPanel attachedFiles = new AttachedFilesPanel();

        List<Map.Entry<String, Integer>> attachments = model.getAttachments();
        if(attachments != null && attachments.size() > 0) {
            this.addComponent(textPane, "w 100::98%, grow, wrap");

            for(Map.Entry<String, Integer> attach : attachments) {
                String data = attach.getKey() + "_" + attach.getValue();
                attachedFiles.attachFile(attach.getKey(), data, listener);
            }

            this.addComponent(attachedFiles, "grow");
        } else {
            this.addComponent(textPane, "w 100::98%, grow");
        }
    }
}