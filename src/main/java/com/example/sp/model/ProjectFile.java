package com.example.sp.model;

import java.util.Date;

/**
 *    
 *  @author luo tianyue 
 *  @Date 2020/6/18  
 *  @Time 16:06  
 */
public class ProjectFile {
    private int file_id;
    private String course_id;
    private String pj_id;
    private String user_id;
    private String upload_date;
    private long size;
    private String name;
    private String path;
    private String description;

     public ProjectFile(String course_id, String pj_id, String user_id, String upload_date, long size, String name, String path, String description) {
        this.course_id = course_id;
        this.pj_id = pj_id;
        this.user_id = user_id;
        this.upload_date = upload_date;
        this.size = size;
        this.name = name;
        this.path = path;
        this.description = description;
    }

    public ProjectFile(int file_id, String course_id, String pj_id, String user_id, String upload_date, long size, String name, String path, String description) {
        this.course_id = course_id;
        this.file_id = file_id;
        this.pj_id = pj_id;
        this.user_id = user_id;
        this.upload_date = upload_date;
        this.size = size;
        this.name = name;
        this.path = path;
        this.description = description;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public int getFile_id() {
        return file_id;
    }

    public void setFile_id(int file_id) {
        this.file_id = file_id;
    }

    public String getPj_id() {
        return pj_id;
    }

    public void setPj_id(String pj_id) {
        this.pj_id = pj_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUpload_date() {
        return upload_date;
    }

    public void setUpload_date(String upload_date) {
        this.upload_date = upload_date;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
