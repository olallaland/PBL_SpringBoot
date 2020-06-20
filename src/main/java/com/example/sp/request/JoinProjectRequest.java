package com.example.sp.request;

/**
 *    
 *  @author luo tianyue 
 *  @Date 2020/6/19  
 *  @Time 19:07  
 */
public class JoinProjectRequest {
    String course_id;
    String project_id;
    String student_id;

    public JoinProjectRequest() {
    }

    public JoinProjectRequest(String course_id, String project_id, String student_id) {
        this.course_id = course_id;
        this.project_id = project_id;
        this.student_id = student_id;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }
}
