package edu.wgu.cmaxwe3.schooltracker.model;

import java.time.LocalDateTime;

public class Assessment {

//    public static final String TABLE_ASSESSMENT = "Assessment";
//    public static final String ASSESSMENT_ID = "_id";
//    public static final String ASSESSMENT_TYPE = "type";
//    public static final String ASSESSMENT_TITLE = "title";
//    public static final String ASSESSMENT_DUE_DATE = "due_date";
//    public static final String ASSESSMENT_GOAL_DATE = "goal_date";
//    public static final String ASSESSMENT_GOAL_DATE_ALERT = "goal_date_alert";
//    public static final String ASSESSMENT_COURSE_ID = "course_id";

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
