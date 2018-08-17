package edu.wgu.cmaxwe3.schooltracker.model;

public class Mentor {
//    public static final String TABLE_MENTOR = "Mentor";
//    public static final String MENTOR_ID = "_id";
//    public static final String MENTOR_NAME = "name";
//    public static final String MENTOR_PHONE = "phone";
//    public static final String MENTOR_EMAIL = "email";
//    public static final String MENTOR_COURSE_ID = "course_id";

    private int id;
    private String name;
    private String phone;
    private String email;
    private int courseId;

    public Mentor(){
    }

    // getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

}
