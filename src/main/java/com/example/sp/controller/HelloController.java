package com.example.sp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *    
 *  @author luo tianyue 
 *  @Date 2020/6/20  
 *  @Time 21:24  
 */
@Controller
public class HelloController {
    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        return "Hello, SpringBoot With Docker";
    }
}