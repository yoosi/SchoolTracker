package edu.wgu.cmaxwe3.schooltracker.helper;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Tools {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String buildDateString(int year, int month, int day) {
        LocalDate localDate = LocalDate.of(year, month, day);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return localDate.format(formatter);
    }
}
