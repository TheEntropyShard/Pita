package me.theentropyshard.pita;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public enum Utils {
    ;

    public static String getTodaysDateRussian() {
        LocalDate localDate = LocalDate.now(ZoneId.of("Europe/Moscow"));
        String month = localDate.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(String.format("dd %s yyyy г.", month));

        return localDate.format(formatter);
    }
}
