package edu.wgu.cmaxwe3.schooltracker.model;

import java.time.LocalDateTime;

public class Assessment {

    private int id;
    private String type;
    private String title;
    private LocalDateTime dueDate;
    private LocalDateTime goalDate;
    private int goalDateAlert;
    private int courseId;


    // getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getGoalDate() {
        return goalDate;
    }

    public void setGoalDate(LocalDateTime goalDate) {
        this.goalDate = goalDate;
    }

    public int getGoalDateAlert() {
        return goalDateAlert;
    }

    public void setGoalDateAlert(int goalDateAlert) {
        this.goalDateAlert = goalDateAlert;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
