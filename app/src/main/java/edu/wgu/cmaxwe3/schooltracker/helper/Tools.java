package edu.wgu.cmaxwe3.schooltracker.helper;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Tools {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String buildDateString(int year, int month, int day) {
        LocalDate localDate = LocalDate.of(year, month, day);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return localDate.format(formatter);
    }

    public static String getYear(String date) {
        String year = date.substring(0, Math.min(date.length(), 4));
        return year;
    }

    public static String getMonth(String date) {
        String month = date.substring(5, 7);
        return month;
    }

    public static String getDay(String date) {
        String day = date.substring(8, 10);
        return day;
    }

    public Calendar getCalFromString(String date) throws ParseException {
        Calendar cal = Calendar.getInstance();
        //        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
        //        cal.setTime(sdf.parse("Mon Mar 14 16:02:37 GMT 2011"));// all done
        cal.setTime(sdf.parse(date));
        return cal;
    }

}
