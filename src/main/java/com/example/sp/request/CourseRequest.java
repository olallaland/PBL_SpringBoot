package com.example.sp.request;

public class CourseRequest {
    String course_id;
    //课程编码
    String course_name;
    //课程名称
    String course_time;
    //上课时间
    String exam_time;
    //考试时间
    String desc;
    //课程描述
    String teacher_id;
    //老师姓名
    int amount;
    //选课人数

    public CourseRequest(){

    }

    public CourseRequest(String course_id, String course_name, String course_time, String exam_time, String desc, String teacher_id, int amount){
        this.course_id = course_id;
        this.course_name = course_name;
        this.course_time = course_time;
        this.exam_time = exam_time;
        this.desc = desc;
        this.teacher_id = teacher_id;
        this.amount = amount;
    }

    public void setCourse_id(String course_id){
        this.course_id = course_id;
    }
    public String getCourse_id(){
        return course_id;
    }

    public void setCourse_name(String course_name){
        this.course_name = course_name;
    }
    public String getCourse_name(){
        return course_name;
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

    public void setDesc(String desc){
        this.desc = desc;
    }
    public String getDesc(){
        return desc;
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
