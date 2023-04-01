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

package me.theentropyshard.pita;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Различные утилиты
 */
public enum Utils {
    ;

    public static final Charset UTF_8 = Charset.forName("UTF-8");

    public static String getTodaysDateRussian() {
        LocalDate localDate = LocalDate.now(ZoneId.of("Europe/Moscow"));
        String month = localDate.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(String.format("d %s yyyy г.", month));

        return localDate.format(formatter);
    }

    public static File getAppDir(String appName) {
        String userHome = System.getProperty("user.home", ".");
        File appDir;

        switch(EnumOS.getOS()) {
            case WINDOWS:
                String appData = System.getenv("APPDATA");

                if(appData != null) {
                    appDir = new File(appData, appName + '/');
                } else {
                    appDir = new File(userHome, appName + '/');
                }

                break;

            case MACOS:
                appDir = new File(userHome, "Library/Application Support/" + appName);
                break;

            case LINUX:
            case SOLARIS:
                appDir = new File(userHome, ".config/" + appName);
                break;

            default:
                appDir = new File(userHome, appName + '/');
                break;
        }

        if(!appDir.exists() && !appDir.mkdirs()) {
            throw new RuntimeException("Не удалось создать рабочую папку: " + appDir);
        } else {
            return appDir;
        }
    }

    public static File makeFile(File file) {
        if(file.isDirectory()) {
            throw new IllegalArgumentException("File is a directory (expected file): " + file);
        }

        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Could not create file: " + file, e);
            }
        }

        return file;
    }

    public static File makeDirectory(File dir) {
        if(dir.isFile()) {
            throw new IllegalArgumentException("File is a file (expected directory): " + dir);
        }

        if(!dir.exists()) {
            dir.mkdirs();
        }

        return dir;
    }

    /**
     * Hashes input using md5 algorithm
     *
     * @param input Array of bytes to be hashed
     * @return Hex-String
     */
    public static String md5(byte[] input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(input);
            StringBuilder sb = new StringBuilder();
            for(byte b : array) {
                sb.append(Integer.toHexString((b & 0xFF) | 0x100), 1, 3);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     * Wraps {@code URLEncoder.encode(s, enc)} method with exception handling
     *
     * @param s String to be encoded as UTF-8
     * @return URL-encoded String
     */
    public static String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return The start date of the current week in format 2022-10-17 (fullyear-month-day)
     */
    public static String getCurrentWeekStart() {
        LocalDate today = LocalDate.now();
        LocalDate monday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        return monday.toString();
    }

    /**
     * @return The end date of the current week in format 2022-10-17 (fullyear-month-day)
     */
    public static String getCurrentWeekEnd() {
        LocalDate today = LocalDate.now();
        LocalDate saturday = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));
        return saturday.toString();
    }

    /**
     * Checks whether an object array is bad
     *
     * @param array The array to be validated
     * @return True, if array is null or empty. False otherwise
     */
    public static boolean isBadArray(Object[] array) {
        return array == null || array.length < 1;
    }

    /**
     * Checks whether an integer array is bad
     *
     * @param array The array to be validated
     * @return True, if array is null or empty. False otherwise
     */
    public static boolean isBadArray(int[] array) {
        return array == null || array.length < 1;
    }

    /**
     * Compiles an array of arg-value pairs into URL-encoded String
     *
     * @param argsValues Array of arg-value pairs
     * @return URL-encoded String of arg-value pairs
     */
    public static String toFormUrlEncoded(Object... argsValues) {
        if(argsValues == null) {
            throw new IllegalArgumentException("Null array given");
        }

        if(argsValues.length == 0) {
            return "";
        }

        if(argsValues.length % 2 != 0) {
            throw new IllegalArgumentException(String.format("Length of argsValues must be even (%d given)", argsValues.length));
        }

        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < argsValues.length - 1; i++) {
            if(i % 2 == 0) {
                builder.append(Utils.urlEncode(String.valueOf(argsValues[i]))).append("=");
                builder.append(Utils.urlEncode(String.valueOf(argsValues[i + 1]))).append("&");
            }
        }
        return builder.deleteCharAt(builder.length() - 1).toString();
    }

    /**
     * Reads an InputStream to a list of Strings
     *
     * @param is InputStream object
     * @return List of Strings
     */
    public static List<String> readAllLines(InputStream is) {
        List<String> lines = new ArrayList<>();
        Scanner sc = new Scanner(is);
        while(sc.hasNextLine()) {
            lines.add(sc.nextLine());
        }
        sc.close();
        return lines;
    }

    /**
     * Reads an InputStream to a String
     *
     * @param is InputStream object
     * @return Resulted String
     */
    public static String readAsOneLine(InputStream is) {
        StringBuilder builder = new StringBuilder();
        Scanner sc = new Scanner(is);
        while(sc.hasNextLine()) {
            builder.append(sc.nextLine());
        }
        sc.close();
        return builder.toString();
    }
}
