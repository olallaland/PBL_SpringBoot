package com.example.sp.controller;

import com.example.sp.SqlSessionLoader;
import com.example.sp.model.ProjectFile;
import com.example.sp.response.genFailedResponse;
import com.example.sp.util.UploadUtil;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *    
 *  @author luo tianyue 
 *  @Date 2020/6/17  
 *  @Time 18:48  
 */

@Controller
@RequestMapping("/pbl/file")
public class FileController {

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Object upload(@RequestParam("course_id") String courseID,
                             @RequestParam(value = "file", required = false) MultipartFile file,
                             @RequestParam("pj_id") String projectID,
                             @RequestParam("date") String date,
                             @RequestParam("user_id") String userID,
                             @RequestParam("description") String description) throws IOException {
        System.out.println("courseID: " + courseID);
        System.out.println("projectID: " + projectID);
        System.out.println("date: " + date);
        System.out.println("userID: " + userID);
        System.out.println("description: " + description);
        long size = 0;
        String name = "";
        String path = "";

        try {
            System.out.println(file.getSize());
            System.out.println(file.getOriginalFilename());
            size = file.getSize();
            name = file.getOriginalFilename();
            path = UploadUtil.uploadFile(file);
            System.out.println(path);

        } catch (Exception e) {
            return new genFailedResponse(310, "error: 上传文件失败", new Date());
        }

        SqlSession sqlSession = SqlSessionLoader.getSqlSession();
        ProjectFile uploadFile = new ProjectFile(courseID, projectID, userID, date, size, name, path, description);
        sqlSession.insert("com.example.sp.dao.FileDao.addFile", uploadFile);
        sqlSession.commit();
        sqlSession.close();

        return new genFailedResponse(200, "上传成功", new Date());
    }

    @RequestMapping(value = "/getFiles/{courseID}/{projectID}", method = RequestMethod.POST)
    @ResponseBody
    public Object getFileList(@PathVariable String courseID, @PathVariable String projectID) throws IOException {
        System.out.println("courseID: " + courseID);
        System.out.println("projectID: " + projectID);

        Map<String, String> params = new HashMap<>();
        params.put("course_id", courseID);
        params.put("pj_id", projectID);

        SqlSession sqlSession = SqlSessionLoader.getSqlSession();
        List<ProjectFile> fileList = sqlSession.selectList("com.example.sp.dao.FileDao.getFileList", params);
        System.out.println(fileList);
        sqlSession.commit();
        sqlSession.close();

        return new genFailedResponse(200, "上传成功", fileList);
    }

    @RequestMapping(value = "/deleteFile/{fileID}", method = RequestMethod.POST)
    @ResponseBody
    public Object getFileList(@PathVariable int fileID) throws IOException {
        System.out.println("fileID: " + fileID);

        SqlSession sqlSession = SqlSessionLoader.getSqlSession();
        try {
            sqlSession.delete("com.example.sp.dao.FileDao.deleteFile", fileID);
        } catch (Exception e) {
            System.out.println("删除文件失败" + e.getMessage());
            return new genFailedResponse(310, "删除文件失败", new Date());
        } finally {
            sqlSession.commit();
        sqlSession.close();
        }
        return new genFailedResponse(200, "删除成功", new Date());
    }

}
