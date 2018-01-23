package de.tum.utils;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Formatters {

    public static NumberFormat numberFormat = NumberFormat.getInstance(Locale.GERMAN);
    public static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    public static DateTimeFormatter dateFormatLong = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

}
