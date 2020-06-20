package com.example.sp.request;

/**
 *    
 *  @author luo tianyue 
 *  @Date 2020/6/20  
 *  @Time 2:09  
 */
public class MissionRequest {
    String course_id;
    String pj_id;
    String student_id;

    public MissionRequest(String course_id, String pj_id, String student_id) {
        this.course_id = course_id;
        this.pj_id = pj_id;
        this.student_id = student_id;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getPj_id() {
        return pj_id;
    }

    public void setPj_id(String pj_id) {
        this.pj_id = pj_id;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }
}
