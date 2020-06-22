package com.example.sp.controller;

import com.example.sp.SqlSessionLoader;
import com.example.sp.constant.ConfigConstant;
import com.example.sp.model.Student;
import com.example.sp.model.Teacher;
import com.example.sp.model.User;
import com.example.sp.request.RegisterRequest;
import com.example.sp.request.UserRegisterRequest;
import com.example.sp.response.ResponseBean;
import com.example.sp.response.genFailedResponse;
import com.example.sp.response.genSuccessfulResponse;
import com.example.sp.util.UploadUtil;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/pbl/user")
public class UserController {

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean register(@RequestParam("type") String type,
                             @RequestParam(value = "picture", required = false) MultipartFile picture,
                             @RequestParam("username") String username,
                             @RequestParam("name") String name,
                             @RequestParam("password") String password,
                             @RequestParam(value = "gender", required = false) String gender) throws IOException {

        System.out.println("type: " + type);
        System.out.println("picture: " + picture);
        System.out.println("username: " + username);
        System.out.println("name: " + name);
        System.out.println("password: " + password);
        System.out.println("gender: " + gender.equals("null"));

        String path = null;
        if(picture != null) {
            path = UploadUtil.uploadFile(picture);
        }

        if(gender.equals("null")) {
            gender = null;
        }

        SqlSession sqlSession = SqlSessionLoader.getSqlSession();
        if (type.equals("student")) {
            Student student = sqlSession.selectOne("example.UserMapper.findStudentByUsername", username);
            if (student != null) {
                sqlSession.close();
                return new genFailedResponse(310, "error: 该学生用户已存在", new Date());

            } else {
                Student stu = new Student(username, password, name, gender, path);
                sqlSession.insert("example.UserMapper.addStudent", stu);
                sqlSession.commit();
                sqlSession.close();
                return new genSuccessfulResponse(200, "success：注册成功", stu);
            }
        }

        if (type.equals("teacher")) {
            Teacher teacher = sqlSession.selectOne("example.UserMapper.findTeacherByUsername", username);
            if (teacher != null) {
                sqlSession.close();
                return new genFailedResponse(310, "error: 该教师用户已存在", new Date());

            } else {
                Teacher tea = new Teacher(username, password, name, gender, path);
                sqlSession.insert("example.UserMapper.addTeacher", tea);
                sqlSession.commit();
                sqlSession.close();
                return new genSuccessfulResponse(200, "success：注册成功", tea);
            }
        }
        sqlSession.close();
        return new genFailedResponse(310, "error:管理员不能注册", new Date());
    }

    @RequestMapping(value = "/findUserByID/{type}/{userID}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean register(@PathVariable String type,
                                 @PathVariable String userID) throws IOException {

        SqlSession sqlSession = SqlSessionLoader.getSqlSession();
        if (type.equals("student")) {
            Student student = sqlSession.selectOne("example.UserMapper.findStudentByUsername", userID);
            if (student != null) {
                return new genSuccessfulResponse(200, "success：成功", student);
            } else {
                return new genFailedResponse(310, "error: 找不到该用户", new Date());
            }
        }

        if (type.equals("teacher")) {
            Teacher teacher = sqlSession.selectOne("example.UserMapper.findTeacherByUsername", userID);
            if (teacher == null) {
                return new genFailedResponse(310, "error: 找不到该用户", new Date());
            } else {
                return new genSuccessfulResponse(200, "success：成功", teacher);
            }
        }
        sqlSession.close();
        return new genFailedResponse(310, "error:未知错误", new Date());
    }

    @RequestMapping("/login")
    @ResponseBody
    public ResponseBean login(@RequestParam(value="type", defaultValue="student") String type,
                              @RequestParam(value="username") String username,
                              @RequestParam(value="password") String password) throws IOException {
        System.out.println("username: "+username+" ; password: "+password+"-----------------------------");
        SqlSession sqlSession = SqlSessionLoader.getSqlSession();
        if (type.equals("student")) {
            Student stu = sqlSession.selectOne("example.UserMapper.findStudentByUsername", username);
            if(stu == null) {
                return new genFailedResponse(310, "error: 该学生用户不存在", new Date());
            }
            if (stu.getPassword().equals(password)) {
                sqlSession.close();
                return new genSuccessfulResponse(200, "success:登陆成功", stu);
            } else {
                sqlSession.close();
                return new genFailedResponse(310, "error: 密码错误", new Date());
            }
        }
        if (type.equals("teacher")) {
            Teacher tea = sqlSession.selectOne("example.UserMapper.findTeacherByUsername", username);
            if(tea == null) {
                return new genFailedResponse(310, "error: 该教师用户不存在", new Date());
            }
            if (tea.getPassword().equals(password)) {
                sqlSession.close();
                return new genSuccessfulResponse(200, "success:登陆成功", tea);
            } else {
                sqlSession.close();
                return new genFailedResponse(310, "error: 密码错误", new Date());
            }
        }
        sqlSession.close();
        return new genSuccessfulResponse(200, "success:登陆成功", new String("root"));

    }

    //默认是获得所有student的信息
    @RequestMapping("/getUsers")
    @ResponseBody
    public ResponseBean login() throws IOException {
//        System.out.println("username: "+username+" ; password: "+password+"-----------------------------");
        SqlSession sqlSession = SqlSessionLoader.getSqlSession();
        List<Student> users = sqlSession.selectList("example.UserMapper.listUsers");
        sqlSession.close();
        return new genSuccessfulResponse(200, "success", users);

    }

    //默认是获得所有teacher的信息
    @RequestMapping("/getTeachers")
    @ResponseBody
    public ResponseBean getTeachers() throws IOException {
//        System.out.println("username: "+username+" ; password: "+password+"-----------------------------");
        SqlSession sqlSession = SqlSessionLoader.getSqlSession();
        List<Teacher> teacherList = sqlSession.selectList("example.UserMapper.listTeachers");
        sqlSession.close();
        return new genSuccessfulResponse(200, "success", teacherList);

    }

    @RequestMapping("/removeUser")
    @ResponseBody
    public ResponseBean removeUser(@RequestParam(value="type", defaultValue="student") String type,
                                   @RequestParam(value="username") String username) throws IOException {
        System.out.println("username: "+username+" ; type: "+type+"-----------------------------");
        SqlSession sqlSession = SqlSessionLoader.getSqlSession();
        //List<User> users = sqlSession.selectList("example.UserMapper.listUsers");

        if (type.equals("student")) {
            try {
                sqlSession.delete("example.UserMapper.deleteStudentByUsername", username);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return new genFailedResponse(310, "error:删除学生用户失败", new Date());
            } finally {
                sqlSession.commit();
                sqlSession.close();
            }
            return new genSuccessfulResponse(200, "success:删除学生用户成功", new Date());
        }
        if (type.equals("teacher")) {

            try {
                sqlSession.delete("example.UserMapper.deleteTeacherByUsername", username);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return new genFailedResponse(310, "error:删除老师用户失败", new Date());
            } finally {
                sqlSession.commit();
                sqlSession.close();
            }
            return new genSuccessfulResponse(200, "success:删除老师用户成功", new Date());
        }

        sqlSession.close();
        return new genSuccessfulResponse(310, "error：只有学生和老师用户才可以被删除", new Date());
    }


    @RequestMapping(value = "/infoManagement", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean infoManagement(@RequestParam("type") String type,
                             @RequestParam(value = "picture", required = false) MultipartFile picture,
                             @RequestParam("username") String username,
                             @RequestParam("name") String name,
                             @RequestParam("password") String password,
                             @RequestParam(value = "gender", required = false) String gender) throws IOException {

        System.out.println("type: " + type);
        System.out.println("picture: " + picture);
        System.out.println("username: " + username);
        System.out.println("name: " + name);
        System.out.println("password: " + password);
        System.out.println("gender: " + gender.equals("null"));

        if (gender.equals("null")) {
            gender = null;
        }

        String path = null;
        if (picture != null) {
            path= UploadUtil.uploadFile(picture);
            System.out.println(path);
        }


        SqlSession sqlSession = SqlSessionLoader.getSqlSession();
        if (type.equals("student")) {
            Student student = sqlSession.selectOne("example.UserMapper.findStudentByUsername", username);
            if(path != null) {
                student.setPicture(path);
            }
            student.setGender(gender);
            student.setName(name);
            student.setPassword(password);

            sqlSession.update("example.UserMapper.updateStudentByUsername", student);

            sqlSession.commit();
            sqlSession.close();
            return new genSuccessfulResponse(200, "success：学生信息更新成功", student);
        }

        if (type.equals("teacher")) {
            Teacher teacher = sqlSession.selectOne("example.UserMapper.findTeacherByUsername", username);
           if(path != null) {
                teacher.setPicture(path);
            }
            teacher.setGender(gender);
            teacher.setName(name);
            teacher.setPassword(password);

            sqlSession.update("example.UserMapper.updateTeacherByUsername", teacher);

            sqlSession.commit();
            sqlSession.close();
            return new genSuccessfulResponse(200, "success：老师信息更新成功", teacher);
        }
        sqlSession.close();
        return new genFailedResponse(310, "error:只有教师和学生的信息才能更新", new Date());
    }

}
