package com.example.sp.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Discussion {

    String course_id;
    //课程编码

    String pj_id;
    //项目编码


    int discussion_id;
    //任务编码

    String title;
    //讨论标题

    String question;
    //问题表述

    String initiator;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'hh:mm")
    @JsonFormat(pattern = "yyyy-MM-dd'T'hh:mm")
    Timestamp start;
    //讨论发起时间

    List<Discussion_answer> answerList = new ArrayList<>();

    public Discussion(){

    }

    public Discussion(String course_id, String pj_id, int discussion_id, String title, String question, Timestamp start){
        this.course_id = course_id;
        this.pj_id = pj_id;
        this.discussion_id = discussion_id;
        this.title = title;
        this.start = start;
        this.question = question;
    }

    public Discussion(String course_id, String pj_id, int discussion_id, String title, String question, String initiator, Timestamp start) {
        this.course_id = course_id;
        this.pj_id = pj_id;
        this.discussion_id = discussion_id;
        this.title = title;
        this.question = question;
        this.initiator = initiator;
        this.start = start;
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

    public void setDiscussion_id(int discussion_id){
        this.discussion_id = discussion_id;
    }
    public int getDiscussion_id(){
        return discussion_id;
    }

    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return title;
    }

    public void setStart(Timestamp start){
        this.start = start;
    }
    public Timestamp getStart(){
        return start;
    }

    public void setQuestion(String question){
        this.question = question;
    }
    public String getQuestion(){
        return question;
    }

    public String getInitiator() {
        return initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

    public List<Discussion_answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Discussion_answer> answerList) {
        this.answerList = answerList;
    }
}
