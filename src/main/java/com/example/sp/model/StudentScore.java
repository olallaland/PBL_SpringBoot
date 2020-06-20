package com.example.sp.model;

/**
 *    
 *  @author luo tianyue 
 *  @Date 2020/6/20  
 *  @Time 2:47  
 */
public class StudentScore {
    String course_id;
    String project_id;
    String student_id;
    int mission_score;
    int other_score;
    String scored;

    public StudentScore() {
    }

    public StudentScore(String course_id, String project_id, String student_id, String scored) {
        this.course_id = course_id;
        this.project_id = project_id;
        this.student_id = student_id;
        this.scored = scored;
    }

    public StudentScore(String course_id, String project_id, String student_id, int mission_score, int other_score) {
        this.course_id = course_id;
        this.project_id = project_id;
        this.student_id = student_id;
        this.mission_score = mission_score;
        this.other_score = other_score;
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

    public int getMission_score() {
        return mission_score;
    }

    public void setMission_score(int mission_score) {
        this.mission_score = mission_score;
    }

    public int getOther_score() {
        return other_score;
    }

    public void setOther_score(int other_score) {
        this.other_score = other_score;
    }

    public String getScored() {
        return scored;
    }

    public void setScored(String scored) {
        this.scored = scored;
    }

    @Override
    public String toString() {
        return "StudentScore{" +
                "course_id='" + course_id + '\'' +
                ", project_id='" + project_id + '\'' +
                ", student_id='" + student_id + '\'' +
                ", mission_score=" + mission_score +
                ", other_score=" + other_score +
                ", scored='" + scored + '\'' +
                '}';
    }
}
