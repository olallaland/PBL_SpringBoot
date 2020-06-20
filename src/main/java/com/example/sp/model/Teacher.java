package com.example.sp.model;

public class Teacher {
    String teacher_id;
    //教师工号
    String password;
    //密码
    String name;
    //学生名字
    String gender;
    //性别
    String picture;
    //头像，暂时用String类型的url表示

    public Teacher(){

    }

    public Teacher(String teacher_id, String password, String name, String gender, String picture){
        this.teacher_id = teacher_id;
        this.name = name;
        this.gender = gender;
        this.picture = picture;
        this.password = password;
    }

    public void setTeacher_id(String teacher_id){
        this.teacher_id = teacher_id;
    }
    public String getTeacher_id(){
        return teacher_id;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public void setGender(String gender){
        this.gender = gender;
    }
    public String getGender(){
        return gender;
    }

    public void setPicture(String picture){
        this.picture = picture;
    }
    public String getPicture(){
        return picture;
    }

    public void setPassword(String password){
        this.password = password;
    }
    public String getPassword(){
        return password;
    }
}
