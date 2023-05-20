package me.theentropyshard.pita.utils;

import me.theentropyshard.pita.model.announcements.AnnouncementModel;
import me.theentropyshard.pita.netschoolapi.diary.models.Announcement;
import me.theentropyshard.pita.netschoolapi.diary.models.Attachment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap;

public final class ModelConverter {
    private static final DateTimeFormatter TO_POST_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private static String annFixHTMLEntities(String raw) {
        return raw
                .replace("amp;#160", "nbsp")
                .replace("&amp;quot;", "\"")
                .replace("&amp;#171;", "«")
                .replace("&amp;#187;", "»");
    }


    public static AnnouncementModel toAM(Announcement a) {
        AnnouncementModel am = new AnnouncementModel(
                a.name,
                a.author.nickName,
                LocalDateTime.parse(a.postDate).format(ModelConverter.TO_POST_TIME_FORMATTER),
                ModelConverter.annFixHTMLEntities(a.description)
        );
        for(Attachment at : a.attachments) {
            String name = at.name == null ? at.originalFileName : at.name;
            am.getAttachments().add(new AbstractMap.SimpleEntry<>(name, at.id));
        }
        return am;
    }

    private ModelConverter() {
        throw new Error("Class ModelConverter should never be instantiated");
    }
}
