package com.example.sp.model;

public class TeachCourse {

    String course_id;
    //课程编码
    String teacher_id;
    //教师工号
    int amount;
    //选课人数
    public TeachCourse(){

    }

    public TeachCourse(String teacher_id, String course_id){
        this.course_id = course_id;
        this.teacher_id = teacher_id;

    }

    public TeachCourse(String teacher_id, String course_id, int amount){
        this.course_id = course_id;
        this.teacher_id = teacher_id;
        this.amount = amount;
    }

    public void setCourse_id(String course_id){
        this.course_id = course_id;
    }
    public String getCourse_id(){
        return course_id;
    }

    public void setTeacher_id(String teacher_id){
        this.teacher_id = teacher_id;
    }
    public String getTeacher_id(){
        return teacher_id;
    }

    public void setAmount(int amount){
        this.amount = amount;
    }
    public int getAmount(){
        return amount;
    }
}
