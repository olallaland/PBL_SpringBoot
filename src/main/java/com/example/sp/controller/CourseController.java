package com.example.sp.controller;


import com.example.sp.SqlSessionLoader;
import com.example.sp.model.*;
import com.example.sp.request.CourseRequest;
import com.example.sp.request.RegisterRequest;
import com.example.sp.response.ResponseBean;
import com.example.sp.response.genFailedResponse;
import com.example.sp.response.genSuccessfulResponse;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/pbl/course")
public class CourseController {

    /*
     *用于管理获得所有课程信息，学生获得他选的所有课的课程信息，以及老师获得他所教授的课的课程信息
     */
    @RequestMapping("/getCourses")
    @ResponseBody
    public ResponseBean getCourse(@RequestParam(value="type", defaultValue="student") String type,
                                   @RequestParam(value="username") String username) throws IOException {
        System.out.println("username: "+username+" ; type: "+type+"-----------------------------");
        SqlSession sqlSession = SqlSessionLoader.getSqlSession();

        if (type.equals("student")) {
            List<CourseInfo> stus = sqlSession.selectList("example.CourseMapper.StudentSelectCourse", username);
            sqlSession.close();
            return new genSuccessfulResponse(200, "success:获得学生课程成功", stus);

        }
        if (type.equals("teacher")) {
            List<CourseInfo> teas = sqlSession.selectList("example.CourseMapper.TeacherSelectCourse", username);
            sqlSession.close();
            return new genSuccessfulResponse(200, "success:获得老师课程成功", teas);

        }
        List<CourseInfo> cous = sqlSession.selectList("example.CourseMapper.RootSelectCourse");
        sqlSession.close();
        return new genSuccessfulResponse(200, "success:管理员获得课程成功", cous);

    }


    @RequestMapping("/getCourse")
    @ResponseBody
    public ResponseBean getCourseInfo(@RequestParam(value="course_id") String course_id) throws IOException {
        System.out.println("course_id: "+course_id+"-----------------------------");
        SqlSession sqlSession = SqlSessionLoader.getSqlSession();

        CourseInfo cous = sqlSession.selectOne("example.CourseMapper.getCourseInfoByCourse_id",course_id);
        sqlSession.close();
        return new genSuccessfulResponse(200, "success:由课程ID获取课程信息成功", cous);

    }

    //学生加入某一门课程（后续还要补充判断课程是否存在和学生是否存在）
    @RequestMapping("/joinCourse")
    @ResponseBody
    public ResponseBean joinCourse(@RequestParam(value="username") String student_id,
            @RequestParam(value="course_id") String course_id) throws IOException {
        System.out.println("username: "+student_id+"course_id: "+course_id+"-----------------------------");
        SqlSession sqlSession = SqlSessionLoader.getSqlSession();
        TakeCourse tc = new TakeCourse(student_id, course_id);
        try {
            sqlSession.insert("example.CourseMapper.StudentJoinCourse", tc);
        } catch (Exception e) {
             System.out.println(e.getMessage());
             return new genFailedResponse(310, "error:不能重复加入课程", new Date());
        }

        sqlSession.commit();
        sqlSession.close();
        return new genSuccessfulResponse(200, "success:加入课程成功", tc);

    }

    //老师或者管理员删除某个课程。删除课程前需要先删掉take_course里面选这门课的的所有记录和teach_course里面的老师教授这门课的记录
    @RequestMapping("/removeCourse")
    @ResponseBody
    public ResponseBean deleteCourse(@RequestParam(value="course_id") String course_id) throws IOException {
        System.out.println("course_id: "+course_id+"-----------------------------");
        SqlSession sqlSession = SqlSessionLoader.getSqlSession();
        try {
            sqlSession.delete("example.CourseMapper.deleteTakeCourseByCourse_id", course_id);
            sqlSession.delete("example.CourseMapper.deleteTeachCourseByCourse_id", course_id);
            sqlSession.delete("example.CourseMapper.deleteCourseByCourse_id", course_id);
        } catch (Exception e) {
            System.out.println("------删除课程失败------");
            System.out.println(e.getMessage());
            return new genFailedResponse(310, "error:删除课程失败", new Date());
        } finally {
            sqlSession.commit();
            sqlSession.close();
        }

        return new genSuccessfulResponse(200, "success:删除课程成功", new Date());
    }

    @RequestMapping(value = "/addCourse", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean addNewCourse(@RequestBody CourseRequest courseRequest) throws IOException {

        System.out.println("course_id: "+courseRequest.getCourse_id()+" ;teacher_id: "+courseRequest.getTeacher_id()+"-----------------------------");
        //初始化选课人数为0
        courseRequest.setAmount(0);

        SqlSession sqlSession = SqlSessionLoader.getSqlSession();
        Course course = sqlSession.selectOne("example.CourseMapper.selectCourseByUsername", courseRequest.getCourse_id());
        Teacher teacher = sqlSession.selectOne("example.UserMapper.findTeacherByUsername", courseRequest.getTeacher_id());
        System.out.println("course_id: "+(course==null)+" ;teacher_id: "+(teacher!=null)+"-----------------------------");
//        System.out.println(" ;teacher_id: "+teacher.toString()+"-----------------------------");
//        return new genSuccessfulResponse(310, "echo：", teacher);
        if(course==null && teacher!=null){
            Course cou = new Course(courseRequest.getCourse_id(),courseRequest.getCourse_name(),courseRequest.getCourse_time(),courseRequest.getExam_time(),courseRequest.getDesc());
            sqlSession.insert("example.CourseMapper.insertCourse", cou);
            TeachCourse tc = new TeachCourse(courseRequest.getTeacher_id(),courseRequest.getCourse_id(),0);
            sqlSession.insert("example.CourseMapper.insertTeachCourse", tc);
            sqlSession.commit();
            sqlSession.close();
            return new genSuccessfulResponse(200, "success：注册成功", courseRequest);

        }


        sqlSession.close();
        return new genFailedResponse(310, "error:添加课程失败", new Date());
    }

    @RequestMapping(value = "/updateCourse", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean updateCourse(@RequestBody CourseRequest courseRequest) throws IOException {

        System.out.println("course_id: "+courseRequest.getCourse_id()+" ;teacher_id: "+courseRequest.getTeacher_id()+"-----------------------------");
        //初始化选课人数为0
        courseRequest.setAmount(0);

        SqlSession sqlSession = SqlSessionLoader.getSqlSession();
        Course course = sqlSession.selectOne("example.CourseMapper.selectCourseByUsername", courseRequest.getCourse_id());
        Teacher teacher = sqlSession.selectOne("example.UserMapper.findTeacherByUsername", courseRequest.getTeacher_id());
        System.out.println("course_id: "+(course!=null)+" ;teacher_id: "+(teacher!=null)+"-----------------------------");

        if(course!=null && teacher!=null){
            Course cou = new Course(courseRequest.getCourse_id(),courseRequest.getCourse_name(),courseRequest.getCourse_time(),courseRequest.getExam_time(),courseRequest.getDesc());

            if(cou.getName()==null){
                cou.setName(course.getName());
            }
            if(cou.getCourse_time()==null){
                cou.setCourse_time(course.getCourse_time());
            }
            if(cou.getExam_time()==null){
                cou.setExam_time(course.getExam_time());
            }
            if(cou.getDescs()==null){
                cou.setDescs(course.getDescs());
            }

            sqlSession.update("example.CourseMapper.updateCourse", cou);
            TeachCourse tc = new TeachCourse(courseRequest.getTeacher_id(),courseRequest.getCourse_id());
            sqlSession.update("example.CourseMapper.updateTeachCourse", tc);
            sqlSession.commit();
            sqlSession.close();
            return new genSuccessfulResponse(200, "success：更新课程信息成功", courseRequest);

        }


        sqlSession.close();
        return new genFailedResponse(310, "error:更新课程信息失败", new Date());
    }

    /**
     * 根据课程ID获得选课学生名单
     * @param course_id
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/getCourseRoster/{course_id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean getCourseRoster(@PathVariable String course_id) throws IOException {
        System.out.println("course_id: "+course_id+"-----------------------------");
        SqlSession sqlSession = SqlSessionLoader.getSqlSession();
        List<String> roster = sqlSession.selectList("example.CourseMapper.getCourseRoster", course_id);
        sqlSession.commit();
        sqlSession.close();
        return new genSuccessfulResponse(200, "success:获得选课学生名单成功", roster);
    }
}
