package com.example.sp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;

public class Discussion_answer {
    String user_id;
    //学生学号

    int discussion_id;
    //任务编码

    String content;
    //讨论回复

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'hh:mm")
    @JsonFormat(pattern = "yyyy-MM-d'T'hh:mm")
    Timestamp answer_time;
    //讨论发起时间


    public Discussion_answer(){

    }

    public Discussion_answer(String user_id, int discussion_id, String content, Timestamp answer_time) {
        this.user_id = user_id;
        this.discussion_id = discussion_id;
        this.content = content;
        this.answer_time = answer_time;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setDiscussion_id(int discussion_id){
        this.discussion_id = discussion_id;
    }
    public int getDiscussion_id(){
        return discussion_id;
    }

    public void setContent(String content){
        this.content = content;
    }
    public String getContent(){
        return content;
    }

    public void setAnswer_time(Timestamp answer_time){
        this.answer_time = answer_time;
    }
    public Timestamp getAnswer_time(){
        return answer_time;
    }

}
