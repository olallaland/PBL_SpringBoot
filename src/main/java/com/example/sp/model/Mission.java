package com.example.sp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;

public class Mission {
    String course_id;
    //课程编码

    String pj_id;
    //项目编码

    String mission_id;
    //任务编码

    String mission_name;
    //任务名

    String stu_id;
    //负责人

    String level;
    //任务优先级

    String status;
    //任务状态

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'hh:mm")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    Timestamp start;
    //开始时间

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'hh:mm")
    @JsonFormat(pattern = "yyyy-MM-dd'T'hh:mm")
    Timestamp end;
    //结束时间



    public Mission(){

    }
    public Mission(String course_id, String pj_id, String mission_id, String stu_id, String mission_name, String level, String status, Timestamp start, Timestamp end){
        this.course_id = course_id;
        this.pj_id = pj_id;
        this.mission_id = mission_id;
        this.stu_id = stu_id;
        this.start = start;
        this.end = end;
        this.mission_name = mission_name;
        this.level = level;
        this.status = status;
    }

    public void setCourse_id(String course_id){
        this.course_id = course_id;
    }
    public String getCourse_id(){
        return course_id;
    }

    public void setPj_id(String pj_id){
        this.pj_id = pj_id;
    }
    public String getPj_id(){
        return pj_id;
    }

    public void setStu_id(String stu_id){
        this.stu_id = stu_id;
    }
    public String getStu_id(){
        return stu_id;
    }

    public void setMission_id(String mission_id){
        this.mission_id = mission_id;
    }
    public String getMission_id(){
        return mission_id;
    }

    public void setStart(Timestamp start){
        this.start = start;
    }
    public Timestamp getStart(){
        return start;
    }

    public void setEnd(Timestamp end){
        this.end = end;
    }
    public Timestamp getEnd(){
        return end;
    }

    public void setMission_name(String mission_name){
        this.mission_name = mission_name;
    }
    public String getMission_name(){
        return mission_name;
    }

    public void setLevel(String level){
        this.level = level;
    }
    public String getLevel(){
        return level;
    }

    public void setStatus(String status){
        this.status = status;
    }
    public String getStatus(){
        return status;
    }
}
