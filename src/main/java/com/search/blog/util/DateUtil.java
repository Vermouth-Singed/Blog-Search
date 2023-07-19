package com.search.blog.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class DateUtil {
    public static String yyyymmddRegex = "\\d{8}";
    public static String yyyymmddTRegex = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}";
    public static String yyyymmddPattern = "yyyy-MM-dd HH:mm:ss";

    public static String convertStrToDttmStr(String str) {
        try {
            if (Pattern.matches(yyyymmddRegex, str)) {
                return str.substring(0, 4) + "-" + str.substring(4, 6) + "-" + str.substring(6) + " 00:00:00";
            } else if (str.length() >= 19 && Pattern.matches(yyyymmddTRegex, str.substring(0, 19))) {
                LocalDateTime localDateTime = LocalDateTime.parse(str, DateTimeFormatter.ISO_DATE_TIME);

                return localDateTime.format(DateTimeFormatter.ofPattern(yyyymmddPattern));
            }
        } catch (Exception e) {}

        return "";
    }
}
