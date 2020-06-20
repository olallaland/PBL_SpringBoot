package com.example.sp.model;

public class Student {
    String student_id;
    //学生学号
    String password;
    //密码
    String name;
    //学生名字
    String gender;
    //性别
    String picture;
    //头像，暂时用String类型的url表示

    public Student(){

    }

    public Student(String student_id, String name) {
        this.student_id = student_id;
        this.name = name;
    }

    public Student(String student_id, String password, String name, String gender, String picture){
        this.student_id = student_id;
        this.name = name;
        this.gender = gender;
        this.picture = picture;
        this.password = password;
    }

    public void setStudent_id(String student_id){
        this.student_id = student_id;
    }
    public String getStudent_id(){
        return student_id;
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
