package com.example.sp.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;

public class ProjectRequest {
    String course_id;
    //课程编码

    String pj_id;
    //项目编码

    String name;
    //项目名

    String describe;
    //课程描述

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    Timestamp start;
    //开始时间

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    Timestamp end;
   //结束时间

    public ProjectRequest(){
    }


    public ProjectRequest(String course_id, String pj_id) {
        this.course_id = course_id;
        this.pj_id = pj_id;
    }

    public ProjectRequest(String course_id, String pj_id, String name, String describe, Timestamp start, Timestamp end){
        this.course_id = course_id;
        this.pj_id = pj_id;
        this.name = name;
        this.describe = describe;
        this.start = start;
        this.end = end;
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

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public void setDescribe(String describe){
        this.describe = describe;
    }
    public String getDescribe(){
        return describe;
    }

    public void setDesc(Timestamp start){
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

}
