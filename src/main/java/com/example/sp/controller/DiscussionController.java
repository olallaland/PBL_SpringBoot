package com.example.sp.controller;


import com.example.sp.SqlSessionLoader;
import com.example.sp.model.Discussion;
import com.example.sp.model.Discussion_answer;
import com.example.sp.model.Mission;
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
@RequestMapping("/pbl/discussion")
public class DiscussionController {

    //获得某个课程下发起的讨论
    @RequestMapping(value = "/getDiscussionList/{courseID}/{projectID}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean getDiscussion(@PathVariable String courseID,
                                      @PathVariable String projectID) throws IOException {
        System.out.println(" get discussion course_id: "+courseID+" ; pj_id: "+projectID+"-----------------------------");
        ProjectRequest project = new ProjectRequest(courseID, projectID);

        SqlSession sqlSession = SqlSessionLoader.getSqlSession();
        List<Discussion> discussionList = sqlSession.selectList("example.DiscussionMapper.selectDiscussion", project);
        sqlSession.close();
        return new genSuccessfulResponse(200, "success:成功讨论列表", discussionList);
    }

    @RequestMapping(value = "/addDiscussion", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean addDiscussion(@RequestBody Discussion discussion) throws IOException {
        SqlSession sqlSession = SqlSessionLoader.getSqlSession();

        sqlSession.insert("example.DiscussionMapper.insertDiscussion",discussion);
        sqlSession.commit();
        sqlSession.close();
        return new genSuccessfulResponse(200, "success:发起讨论成功", discussion);

    }

    @RequestMapping(value = "/getAnswer", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean getAnswer(@RequestBody Discussion_answer discussion_answer) throws IOException {
        SqlSession sqlSession = SqlSessionLoader.getSqlSession();

        List<Discussion_answer> answerList = sqlSession.selectList("example.DiscussionMapper.selectDiscussion_answer", discussion_answer);
        sqlSession.close();
        return new genSuccessfulResponse(200, "success:成功获取讨论回复", answerList);

    }

    //添加讨论回答
    @RequestMapping(value = "addAnswer", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean addAnswer(@RequestBody Discussion_answer discussion_answer) throws IOException {
        System.out.println("student_id: "+discussion_answer.getUser_id()+" ; pj_id: "+discussion_answer.getDiscussion_id()+"-----------------------------");
        SqlSession sqlSession = SqlSessionLoader.getSqlSession();

        sqlSession.insert("example.DiscussionMapper.insertDiscussion_answer", discussion_answer);

        sqlSession.commit();
        sqlSession.close();
        return new genSuccessfulResponse(200, "success:添加讨论回复成功", discussion_answer);

    }

}
