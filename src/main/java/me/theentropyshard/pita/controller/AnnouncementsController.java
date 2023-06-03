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

package me.theentropyshard.pita.controller;

import me.theentropyshard.pita.model.announcements.AnnouncementModel;
import me.theentropyshard.pita.netschoolapi.NetSchoolAPI;
import me.theentropyshard.pita.netschoolapi.Urls;
import me.theentropyshard.pita.netschoolapi.announcements.models.Announcement;
import me.theentropyshard.pita.netschoolapi.diary.models.Attachment;
import me.theentropyshard.pita.utils.AbstractCallback;
import me.theentropyshard.pita.utils.Utils;
import me.theentropyshard.pita.view.AppWindow;
import me.theentropyshard.pita.view.announcements.AnnouncementCardView;
import me.theentropyshard.pita.view.announcements.AnnouncementsView;
import me.theentropyshard.pita.view.downloads.DownloadsPanel;
import okhttp3.ResponseBody;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import retrofit2.Call;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

public class AnnouncementsController {
    private static final Logger LOG = LogManager.getLogger(AnnouncementsController.class);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private final AnnouncementsView aView;
    private final File attachDir;

    private int numAnnouncements;

    public AnnouncementsController(AnnouncementsView aView, File attachDir) {
        this.aView = aView;
        this.attachDir = attachDir;

        Action actionScrollTop = new AbstractAction("SCROLL_TO_TOP") {
            @Override
            public void actionPerformed(ActionEvent e) {
                scrollToTop();
            }
        };

        Action actionScrollDown = new AbstractAction("SCROLL_TO_BOTTOM") {
            @Override
            public void actionPerformed(ActionEvent e) {
                scrollToBottom();
            }
        };

        InputMap inputMap = this.aView.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = this.aView.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_HOME, 0), "SCROLL_TO_TOP");
        actionMap.put("SCROLL_TO_TOP", actionScrollTop);

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_END, 0), "SCROLL_TO_BOTTOM");
        actionMap.put("SCROLL_TO_BOTTOM", actionScrollDown);
    }

    public void addAnnouncement(AnnouncementModel model) {
        AnnouncementCardView acw = new AnnouncementCardView(model, e -> {
            String data = e.getActionCommand();
            int index = data.lastIndexOf("_");
            String filename = data.substring(0, index);
            int id = Integer.parseInt(data.substring(index + 1));

            NetSchoolAPI.utilsAPI.downloadAttachment(String.format(Urls.ATTACHMENTS_DOWNLOAD, id))
                    .enqueue(new AbstractCallback<ResponseBody>("FileDownload (" + filename + " " + id + ")") {
                        @Override
                        public void handleResponse(ResponseBody r) {
                            File file = Utils.makeFile(new File(attachDir, filename));
                            try {
                                Files.copy(r.byteStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);

                                SwingUtilities.invokeLater(() -> {
                                    StudentController c = AppWindow.window.getStudentController();
                                    DownloadsPanel panel = c.getStudentView().getDownloadsPanel();
                                    panel.addFile(file);
                                    panel.setVisible(true);
                                });
                            } catch (IOException ex) {
                                LOG.error(ex);
                            }
                        }
                    });
        });

        if (this.numAnnouncements == 0) {
            this.aView.getPanel().add(acw, "grow");
        } else {
            this.aView.getPanel().add(acw, "grow, gapy 4 0");
        }

        this.numAnnouncements++;
    }

    public void loadAnnouncements() {
        Call<Announcement[]> announcementsCall = NetSchoolAPI.announcementsAPI.getAnnouncements(-1);
        announcementsCall.enqueue(new AbstractCallback<Announcement[]>("Announcements") {
            @Override
            public void handleResponse(Announcement[] announcements) {
                numAnnouncements = 0;
                SwingUtilities.invokeLater(() -> {
                    aView.getPanel().removeAll();
                    for (Announcement a : announcements) {
                        String topic = Utils.ellipsize(a.name, 125);
                        String time = LocalDateTime.parse(a.postDate).format(AnnouncementsController.FORMATTER);
                        String text = AnnouncementsController.fixHTMLEntities(a.description);
                        AnnouncementModel model = new AnnouncementModel(topic, a.author.nickName, time, text);
                        List<Map.Entry<String, Integer>> attachments = model.getAttachments();
                        for (Attachment attach : a.attachments) {
                            attachments.add(new AbstractMap.SimpleEntry<>(attach.name, attach.id));
                        }
                        addAnnouncement(model);
                    }
                    scrollToTop();
                    aView.revalidate();
                });
            }
        });
    }

    public void scrollToTop() {
        SwingUtilities.invokeLater(() -> this.aView.getScrollPane().getVerticalScrollBar().setValue(0));
    }

    public void scrollToBottom() {
        SwingUtilities.invokeLater(() -> {
            JScrollPane scrollPane = this.aView.getScrollPane();
            scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
        });
    }

    public static String fixHTMLEntities(String raw) {
        return raw
                .replace("amp;#160", "nbsp")
                .replace("&amp;quot;", "\"")
                .replace("&amp;#171;", "«")
                .replace("&amp;#187;", "»")
                .replace("&amp;#183;", "·")
                .replace("&amp;", "&");
    }
}
