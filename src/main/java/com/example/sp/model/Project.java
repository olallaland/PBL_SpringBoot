package com.example.sp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;

public class Project {
    String course_id;
    //课程编码

    String pj_id;
    //项目编码

    String name;
    //项目名

    String descs;
    //课程描述

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    Timestamp start;
    //开始时间

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    Timestamp end;
    //结束时间

    String captain;
    //组长

    int amount;
    // 现有成员数量

    public Project(){

    }

    public Project(String course_id, String pj_id) {
        this.course_id = course_id;
        this.pj_id = pj_id;
    }

    public Project(String course_id, String pj_id, String name, String descs, Timestamp start, Timestamp end){
        this.course_id = course_id;
        this.pj_id = pj_id;
        this.name = name;
        this.descs = descs;
        this.start = start;
        this.end = end;
    }

    public Project(String course_id, String pj_id, String name, String descs, Timestamp start, Timestamp end, String captain){
        this.course_id = course_id;
        this.pj_id = pj_id;
        this.name = name;
        this.descs = descs;
        this.start = start;
        this.end = end;
        this.captain = captain;
    }

    public Project(String course_id, String pj_id, String name, String descs, Timestamp start, Timestamp end, String captain, int amount) {
        this.course_id = course_id;
        this.pj_id = pj_id;
        this.name = name;
        this.descs = descs;
        this.start = start;
        this.end = end;
        this.captain = captain;
        this.amount = amount;
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

    public void setDescs(String descs){
        this.descs = descs;
    }
    public String getDescs(){
        return descs;
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

    public void setCaptain(String captain){
        this.captain = captain;
    }
    public String getCaptain(){
        return captain;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
