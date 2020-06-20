package com.example.sp.model;

public class TakeCourse {
    String student_id;

    String course_id;
    //课程编码
    String score;

    public TakeCourse(){

    }

    public TakeCourse(String student_id, String course_id){
        this.course_id = course_id;
        this.student_id = student_id;

    }

    public TakeCourse(String student_id, String course_id, String score){
        this.course_id = course_id;
        this.student_id = student_id;
        this.score = score;
    }

    public void setCourse_id(String course_id){
        this.course_id = course_id;
    }
    public String getCourse_id(){
        return course_id;
    }

    public void setStudent_id(String student_id){
        this.student_id = student_id;
    }
    public String getStudent_id(){
        return student_id;
    }

    public void setScore(String score){
        this.score = score;
    }
    public String getScore(){
        return score;
    }


}
