package edu.wgu.cmaxwe3.schooltracker.model;

import java.time.LocalDateTime;

public class Course {
//    public static final String TABLE_COURSE = "course";
//    public static final String COURSE_ID = "_id";
//    public static final String COURSE_TITLE = "title";
//    public static final String COURSE_STATUS = "status";
//    public static final String COURSE_START_DATE = "start_date";
//    public static final String COURSE_START_DATE_ALERT = "start_date_alert";
//    public static final String COURSE_END_DATE = "end_date";
//    public static final String COURSE_END_DATE_ALERT = "end_date_alert";
//    public static final String COURSE_NOTES = "notes";
//    public static final String COURSE_TERM_ID = "term_id";

    private int id;
    private String title;
    private String status;
    private LocalDateTime startDate;
    private int startAlert;
    private LocalDateTime endDate;
    private int endAlert;
    private String notes;
    private int termId;


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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public int getStartAlert() {
        return startAlert;
    }

    public void setStartAlert(int startAlert) {
        this.startAlert = startAlert;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public int getEndAlert() {
        return endAlert;
    }

    public void setEndAlert(int endAlert) {
        this.endAlert = endAlert;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }


}
