package com.example.sp.model;

public class CourseInfo {
    String course_id;
    //课程编码
    String course_name;
    //课程名称
    String course_time;
    //上课时间
    String exam_time;
    //考试时间
    String descs;
    //课程描述
    String teacher_name;
    //老师姓名
    String teacher_id;
    // 老师id
    int amount;
    //选课人数

    public CourseInfo(){

    }

    public CourseInfo(String course_id, String course_name, String course_time, String exam_time, String descs, String teacher_name, String teacher_id, int amount) {
        this.course_id = course_id;
        this.course_name = course_name;
        this.course_time = course_time;
        this.exam_time = exam_time;
        this.descs = descs;
        this.teacher_name = teacher_name;
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

    public void setDescs(String desc){
        this.descs = desc;
    }
    public String getDescs(){
        return descs;
    }

    public void setTeacher_name(String teacher_name){
        this.teacher_name = teacher_name;
    }
    public String getTeacher_name(){
        return teacher_name;
    }

    public void setAmount(int amount){
        this.amount = amount;
    }
    public int getAmount(){
        return amount;
    }

    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }
}
