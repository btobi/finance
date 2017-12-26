package de.tum.utils;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Formatters {

    public static NumberFormat numberFormat = NumberFormat.getInstance(Locale.GERMAN);
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    public static SimpleDateFormat dateFormatLong = new SimpleDateFormat("dd.MM.yyyy mm:HH:ss");

}
