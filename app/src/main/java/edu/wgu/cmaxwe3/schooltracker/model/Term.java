package edu.wgu.cmaxwe3.schooltracker.model;

import java.time.LocalDateTime;

public class Term {

    private int id;
    private String title;
    private String startDate;
    private String endDate;


    public Term() {

    }

    // construct with all parameters
    public Term(int id, String title, String startDate, String endDate) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // construct with all parameters except id
    public Term(String title, String startDate, String endDate) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

}
