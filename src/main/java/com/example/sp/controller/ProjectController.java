package com.example.sp.controller;

import com.example.sp.SqlSessionLoader;
import com.example.sp.model.*;
import com.example.sp.request.JoinProjectRequest;
import com.example.sp.request.ProjectRequest;
import com.example.sp.response.ResponseBean;
import com.example.sp.response.genFailedResponse;
import com.example.sp.response.genSuccessfulResponse;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/pbl/project")
public class ProjectController {

    //获得项目的详细信息
    @RequestMapping(value = "/getPj", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean getProject(@RequestBody ProjectRequest projectRequest) throws IOException {
        System.out.println("course_id: " + projectRequest.getCourse_id() + " ; pj_id: " + projectRequest.getPj_id() + "-----------------------------");
        SqlSession sqlSession = SqlSessionLoader.getSqlSession();


        Project pjs = sqlSession.selectOne("example.ProjectMapper.selectAllProject", projectRequest);
        sqlSession.close();
        return new genSuccessfulResponse(200, "success:获得项目信息成功", pjs);

    }

    @RequestMapping(value = "/getPjList/{course_id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean getProjectList(@PathVariable String course_id) throws IOException {
        System.out.println("course_id: " + course_id + "-----------------------------");
        SqlSession sqlSession = SqlSessionLoader.getSqlSession();

        List<Project> pjs = sqlSession.selectList("example.ProjectMapper.selectCourseProject", course_id);
        sqlSession.close();
        return new genSuccessfulResponse(200, "success:获取项目列表成功", pjs);

    }

    //添加项目
    @RequestMapping(value = "/addPj", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean addProject(@RequestBody ProjectRequest projectRequest) throws IOException {
        System.out.println("course_id: " + projectRequest.getCourse_id() + " ; pj_id: " + projectRequest.getPj_id() + "-----------------------------");
        SqlSession sqlSession = SqlSessionLoader.getSqlSession();

        try {
            sqlSession.insert("example.ProjectMapper.insertProject", projectRequest);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new genFailedResponse(310, "error:创建项目失败", new Date());
        } finally {
            sqlSession.commit();
            sqlSession.close();
        }

        return new genSuccessfulResponse(200, "success:管理员获得课程成功", projectRequest);

    }

    //更新项目信息（需要完善，考虑更新的组长不是这个项目·的组员的情况，后面补）
    @RequestMapping(value = "/updatePj", method = RequestMethod.POST)
    @ResponseBody

    public ResponseBean updateProject(@RequestBody Project project) throws IOException {
        System.out.println("update pj course_id: " + project.getCourse_id() + " ; pj_id: " + project.getPj_id() + "-----------------------------");
        SqlSession sqlSession = SqlSessionLoader.getSqlSession();

        Project pj = sqlSession.selectOne("example.ProjectMapper.selectProjectById", project);
        if (pj != null) {
            if (project.getName() == null) {
                project.setName(pj.getName());
            }
            if (project.getStart() == null) {
                project.setStart(pj.getStart());
            }
            if (project.getEnd() == null) {
                project.setEnd(pj.getEnd());
            }
            if (project.getDescs() == null) {
                project.setDescs(pj.getDescs());
            }
            if (project.getCaptain() == null) {
                project.setCaptain(pj.getCaptain());
            }
            sqlSession.update("example.ProjectMapper.updateProject", project);
            sqlSession.commit();
            sqlSession.close();
            return new genSuccessfulResponse(200, "success:项目信息修改成功", project);
        }

        sqlSession.close();
        return new genFailedResponse(310, "success:项目信息修改失败", project);

    }

    //删除项目
    @RequestMapping(value = "/removePj", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean deleteProject(@RequestBody ProjectRequest projectRequest) throws IOException {
        System.out.println("course_id: " + projectRequest.getCourse_id() + " ; pj_id: " + projectRequest.getPj_id() + "-----------------------------");
        SqlSession sqlSession = SqlSessionLoader.getSqlSession();

        try {
            sqlSession.delete("example.ProjectMapper.deleteProjectById", projectRequest);
        } catch (Exception e) {
            System.out.println("删除项目失败" + e.getMessage());
            return new genFailedResponse(310, "error:删除项目失败", new Date());
        } finally {
            sqlSession.commit();
            sqlSession.close();
        }

        return new genSuccessfulResponse(200, "success:删除项目成功", projectRequest);

    }

    /**
     * 学生加入项目
     *
     * @param courseID
     * @param projectID
     * @param studentID
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/joinPj/{courseID}/{projectID}/{studentID}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean joinPj(@PathVariable String courseID,
                               @PathVariable String projectID,
                               @PathVariable String studentID) throws IOException {
        System.out.println("course_id: " + courseID);
        System.out.println("projectID: " + projectID);
        System.out.println("studentID: " + studentID);

        JoinProjectRequest joinProjectRequest = new JoinProjectRequest(courseID, projectID, studentID);
        SqlSession sqlSession = SqlSessionLoader.getSqlSession();

        try {
            // 添加参与pj信息
            sqlSession.insert("example.ProjectMapper.joinProject", joinProjectRequest);
            // 如果是第一个参与项目的学生，则将其任命为组长
            Project project = sqlSession.selectOne("example.ProjectMapper.selectProjectById", new Project(courseID, projectID));

            if (project.getCaptain() == null) {
                project.setCaptain(studentID);
                sqlSession.update("example.ProjectMapper.updateProject", project);
                System.out.println(" you are the first one to join this project");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new genFailedResponse(310, "error:加入项目失败", new Date());
        } finally {
            sqlSession.commit();
            sqlSession.close();
        }

        return new genSuccessfulResponse(200, "success:删除课程成功", new Date());
    }

    /**
     * 获得参与项目的学生列表
     *
     * @param courseID
     * @param projectID
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/getPjMembers/{courseID}/{projectID}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean getProjectMembers(@PathVariable String courseID,
                                          @PathVariable String projectID) throws IOException {
        System.out.println("course_id: " + courseID);
        System.out.println("projectID: " + projectID);

        ProjectRequest projectRequest = new ProjectRequest(courseID, projectID);
        SqlSession sqlSession = SqlSessionLoader.getSqlSession();
        List<Student> studentList = new ArrayList<>();

        try {
            studentList = sqlSession.selectList("example.ProjectMapper.getProjectMembers", projectRequest);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new genFailedResponse(310, "error:获取项目人员失败", new Date());
        } finally {
            sqlSession.commit();
            sqlSession.close();
        }

        return new genSuccessfulResponse(200, "success:获取成功", studentList);
    }

    /**
     * 获得参与项目的学生列表的得分情况
     *
     * @param courseID
     * @param projectID
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/getStudentScore/{courseID}/{projectID}/{studentID}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean getStudentScore(@PathVariable String courseID,
                                        @PathVariable String projectID,
                                        @PathVariable String studentID) throws IOException {
        System.out.println("course_id: " + courseID);
        System.out.println("projectID: " + projectID);
        System.out.println("studentID: " + studentID);

        JoinProjectRequest projectRequest = new JoinProjectRequest(courseID, projectID, studentID);
        SqlSession sqlSession = SqlSessionLoader.getSqlSession();
        StudentScore studentScore = null;
        try {
            studentScore = sqlSession.selectOne("example.ProjectMapper.getStudentScore", projectRequest);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new genFailedResponse(310, "error:获取得分失败", new Date());
        } finally {
            sqlSession.commit();
            sqlSession.close();
        }

        return new genSuccessfulResponse(200, "success:获取项目得分成功", studentScore);
    }

    /**
     * 更新评分
     * @param studentScore
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/scoreStudent", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean scoreStudent(@RequestBody StudentScore studentScore) throws IOException {
        System.out.println(studentScore);
        SqlSession sqlSession = SqlSessionLoader.getSqlSession();

        try {
            sqlSession.update("example.ProjectMapper.scoreStudent", studentScore);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new genFailedResponse(310, "error:更新分数失败", new Date());
        } finally {
            sqlSession.commit();
            sqlSession.close();
        }
        return new genSuccessfulResponse(200, "success:评分成功", studentScore);
    }
}
