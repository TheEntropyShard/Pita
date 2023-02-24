package me.theentropyshard.pita.view;

import me.theentropyshard.netschoolapi.diary.models.Announcement;
import me.theentropyshard.pita.Pita;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class ContentPanel extends JPanel {
    private final Header header;
    private final JPanel content;

    public ContentPanel() {
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(1200, 720));

        this.header = new Header();
        this.add(this.header, BorderLayout.NORTH);

        this.content = new JPanel();
        this.content.setLayout(new BorderLayout());
        this.add(this.content, BorderLayout.CENTER);
    }

    public Header getHeader() {
        return this.header;
    }

    public JPanel getContent() {
        return this.content;
    }

    public void addDefaultComponent() {
        NoticeBoard noticeBoard = new NoticeBoard();

        try {
            List<Announcement> announcements = Pita.getPita().getAPI().getAnnouncements(-1);
            for(Announcement announcement : announcements) {
                noticeBoard.addNoticeBoard(new ModelNoticeBoard(
                        Color.RED,
                        announcement.name,
                        announcement.postDate,
                        announcement.description
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.content.add(noticeBoard, BorderLayout.CENTER);
    }
}
