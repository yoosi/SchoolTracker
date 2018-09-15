package edu.wgu.cmaxwe3.schooltracker.model;

import java.time.LocalDateTime;

public class Course {

    private int id;
    private String title;
    private String status;
    private String startDate;
    private int startAlert;
    private String endDate;
    private int endAlert;
    private String notes;
    private int termId;


    private int mentorId;


    public Course() {

    }

    // construct with all parameters
    public Course(int id, String title, String status, String startDate, int startAlert,
                  String endDate, int endAlert, String notes, int termId) {
        this.setTermId(id);
        this.setTitle(title);
        this.setStatus(status);
        this.setStartDate(startDate);
        this.setStartAlert(startAlert);
        this.setEndDate(endDate);
        this.setEndAlert(endAlert);
        this.setNotes(notes);
        this.setTermId(termId);

    }

    // construct with all parameters except id
    public Course(String title, String status, String startDate, int startAlert,
                  String endDate, int endAlert, String notes, int termId) {
        this.setTitle(title);
        this.setStatus(status);
        this.setStartDate(startDate);
        this.setStartAlert(startAlert);
        this.setEndDate(endDate);
        this.setEndAlert(endAlert);
        this.setNotes(notes);
        this.setTermId(termId);

    }


    // construct with all parameters except id and termId
    public Course(String title, String status, String startDate, int startAlert,
                  String endDate, int endAlert, String notes) {
        this.setTitle(title);
        this.setStatus(status);
        this.setStartDate(startDate);
        this.setStartAlert(startAlert);
        this.setEndDate(endDate);
        this.setEndAlert(endAlert);
        this.setNotes(notes);

    }

    @Override
    public String toString() {
        return this.title;
    }

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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getStartAlert() {
        return startAlert;
    }

    public void setStartAlert(int startAlert) {
        this.startAlert = startAlert;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
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


    public int getMentorId() {
        return mentorId;
    }

    public void setMentorId(int mentorId) {
        this.mentorId = mentorId;
    }


}
