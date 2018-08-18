package edu.wgu.cmaxwe3.schooltracker.helper;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Tools {


    @RequiresApi(api = Build.VERSION_CODES.O)
    public String buildDateString(int year, int month, int day){
        LocalDate localDate = LocalDate.of(year, month, day);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return localDate.format(formatter);
    }
}
