package edu.wgu.cmaxwe3.schooltracker.model;

import java.time.LocalDateTime;

public class Term {
//    public static final String TABLE_TERM = "Term";
//    public static final String TERM_ID = "_id";
//    public static final String TERM_TITLE = "title";
//    public static final String TERM_START_DATE = "start_date";
//    public static final String TERM_END_DATE = "end_date";

    private int id;
    private String title;
    private LocalDateTime startDate; // this might be a different type
    private LocalDateTime endDate;   // this might be a different type


    // getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

}
