package edu.wgu.cmaxwe3.schooltracker.model;

import java.time.LocalDateTime;

public class Course {

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
