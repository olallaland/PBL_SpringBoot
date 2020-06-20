package com.example.sp.model;

public class Course {
    String course_id;
    //课程编码
    String name;
    //课程名称
    String course_time;
    //上课时间
    String exam_time;
    //考试时间
    String descs;
    //课程描述

    public Course(){

    }

    public Course(String course_id, String name, String course_time, String exam_time, String descs){
        this.course_id = course_id;
        this.name = name;
        this.course_time = course_time;
        this.exam_time = exam_time;
        this.descs = descs;
    }

    public void setCourse_id(String course_id){
        this.course_id = course_id;
    }
    public String getCourse_id(){
        return course_id;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public void setCourse_time(String course_time){
        this.course_time = course_time;
    }
    public String getCourse_time(){
        return course_time;
    }

    public void setExam_time(String exam_time){
        this.exam_time = exam_time;
    }
    public String getExam_time(){
        return exam_time;
    }

    public void setDescs(String descs){
        this.descs = descs;
    }
    public String getDescs(){
        return descs;
    }
}
