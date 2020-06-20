package com.example.sp.controller;

import com.example.sp.SqlSessionLoader;
import com.example.sp.model.Mission;
import com.example.sp.model.Project;
import com.example.sp.request.MissionRequest;
import com.example.sp.request.ProjectRequest;
import com.example.sp.response.ResponseBean;
import com.example.sp.response.genFailedResponse;
import com.example.sp.response.genSuccessfulResponse;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/pbl/mission")
public class MissionController {

    //获得任务的详细信息
    @RequestMapping(value = "/getMission", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean getMissionInfo(@RequestBody Mission mission) throws IOException {
        System.out.println("course_id: "+mission.getCourse_id()+" ; pj_id: "+mission.getPj_id()+"-----------------------------");
        SqlSession sqlSession = SqlSessionLoader.getSqlSession();


        Mission mi = sqlSession.selectOne("example.MissionMapper.selectMission", mission);
        sqlSession.close();
        return new genSuccessfulResponse(200, "success:成功获取任务详情", mi);

    }

    //获得任务的详细信息
    @RequestMapping(value = "/getMissionList/{courseID}/{projectID}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean getMissionList(@PathVariable String courseID,
                                       @PathVariable String projectID) throws IOException {
        System.out.println("course_id: "+ courseID +" ; pj_id: " + projectID);
        ProjectRequest project = new ProjectRequest(courseID, projectID);
        SqlSession sqlSession = SqlSessionLoader.getSqlSession();
        List<Mission> missionList = sqlSession.selectList("example.MissionMapper.getMissionList", project);
        sqlSession.close();
        return new genSuccessfulResponse(200, "success:成功获取任务列表", missionList);

    }

     //获得某个学生的全部任务信息
    @RequestMapping(value = "/getStudentMission/{courseID}/{projectID}/{studentID}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean getStudentMission(@PathVariable String courseID,
                                          @PathVariable String projectID,
                                          @PathVariable String studentID) throws IOException {
        System.out.println("course_id: "+ courseID +" ; pj_id: " + projectID + "stu_id:" + studentID);
        MissionRequest missionRequest = new MissionRequest(courseID, projectID, studentID);
        SqlSession sqlSession = SqlSessionLoader.getSqlSession();
        List<Mission> missionList = sqlSession.selectList("example.MissionMapper.getStudentMissionInfo", missionRequest);
        sqlSession.close();
        return new genSuccessfulResponse(200, "success:成功获取任务列表", missionList);
    }

    //添加任务
    @RequestMapping(value = "/addMission", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean addMission(@RequestBody Mission mission) throws IOException {
        System.out.println("course_id: "+mission.getCourse_id()+" ; pj_id: "+mission.getPj_id()+"-----------------------------");
        SqlSession sqlSession = SqlSessionLoader.getSqlSession();
        System.out.println(mission.getStart());
        System.out.println(mission.getEnd());

        try {
            sqlSession.insert("example.MissionMapper.insertMission", mission);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new genFailedResponse(310, "success:创建新任务失败", new Date());
        } finally {
            sqlSession.commit();
            sqlSession.close();
        }

        return new genSuccessfulResponse(200, "success:成功添加新任务", mission);
    }

    //更新任务信息（需要完善，考虑更新的组长不是这个项目·的组员的情况，后面补）
    @RequestMapping(value = "/updateMission", method = RequestMethod.POST)
    @ResponseBody

    public ResponseBean updateMission(@RequestBody Mission mission) throws IOException {
        System.out.println("course_id: "+mission.getCourse_id()+" ; pj_id: "+mission.getPj_id()+"-----------------------------");
        SqlSession sqlSession = SqlSessionLoader.getSqlSession();

        Mission mi = sqlSession.selectOne("example.MissionMapper.selectMission", mission);
        if(mi != null){
            if(mission.getStu_id()==null){
                mission.setStu_id(mi.getStu_id());
            }
            if(mission.getMission_name()==null){
                mission.setMission_name(mi.getMission_name());
            }
            if(mission.getStart()==null){
                mission.setStart(mi.getStart());
            }
            if(mission.getEnd()==null){
                mission.setEnd(mi.getEnd());
            }
            if(mission.getLevel()==null){
                mission.setLevel(mi.getLevel());
            }
            if(mission.getStatus()==null){
                mission.setStatus(mi.getStatus());
            }
            sqlSession.update("example.MissionMapper.updateMission", mission);
            sqlSession.commit();
            sqlSession.close();
            return new genSuccessfulResponse(200, "success:任务信息修改成功", mission);
        }

        sqlSession.close();
        return new genFailedResponse(310, "success:任务信息修改失败", mission);
    }

//    //删除项目
//    @RequestMapping(value = "/removeMission", method = RequestMethod.POST)
//    @ResponseBody
//    public ResponseBean deleteMission(@RequestBody Mission mission) throws IOException {
//        System.out.println("course_id: "+mission.getCourse_id()+" ; pj_id: "+mission.getPj_id()+"-----------------------------");
//        SqlSession sqlSession = SqlSessionLoader.getSqlSession();
//
//
//        sqlSession.delete("example.ProjectMapper.deleteMissionById", mission);
//        sqlSession.commit();
//        sqlSession.close();
//        return new genSuccessfulResponse(200, "success:删除课程成功", mission);
//
//    }

}
