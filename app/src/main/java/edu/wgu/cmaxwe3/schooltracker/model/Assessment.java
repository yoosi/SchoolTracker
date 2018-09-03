package edu.wgu.cmaxwe3.schooltracker.model;


public class Assessment {

    private int id;
    private String type;
    private String title;
    private String dueDate;
    private String goalDate;
    private int goalDateAlert;
    private int courseId;


    public Assessment() {

    }


    // create with all parameters
    public Assessment(int id, String type, String title, String dueDate,
                      String goalDate, int goalDateAlert, int courseId) {

        this.id = id;
        this.type = type;
        this.title = title;
        this.dueDate = dueDate;
        this.goalDate = goalDate;
        this.goalDateAlert = goalDateAlert;
        this.courseId = courseId;
    }

    // create with all parameters except id
    public Assessment(String type, String title, String dueDate,
                      String goalDate, int goalDateAlert, int courseId) {

        this.type = type;
        this.title = title;
        this.dueDate = dueDate;
        this.goalDate = goalDate;
        this.goalDateAlert = goalDateAlert;
        this.courseId = courseId;
    }


    // create with all parameters except id and courseId
    public Assessment(String type, String title, String dueDate,
                      String goalDate, int goalDateAlert) {

        this.type = type;
        this.title = title;
        this.dueDate = dueDate;
        this.goalDate = goalDate;
        this.goalDateAlert = goalDateAlert;

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

    public String getDueDate() {
        return dueDate;
    }
    public String getDueDateYear(){
        String year = dueDate.substring(0, Math.min(dueDate.length(), 4));
        return year;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getGoalDate() {
        return goalDate;
    }

    public void setGoalDate(String goalDate) {
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
