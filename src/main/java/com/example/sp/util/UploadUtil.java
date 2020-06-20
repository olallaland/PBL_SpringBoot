package com.example.sp.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *    
 *  @author luo tianyue 
 *  @Date 2020/6/20  
 *  @Time 18:06  
 */
public class UploadUtil {
    public static String uploadFile(MultipartFile file) throws IOException {
        //使用时间给上传的文件命名，这种方式没有用uuid命名好，因为同一时间有可能会上传多个文件
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS");
        String res = sdf.format(new Date());
        String originaFilename = file.getOriginalFilename();
        //获取文件的后缀名
        String newFileName = res + originaFilename.substring(originaFilename.lastIndexOf("."));

        String rootPath1 = "/files/";
        String rootPath = "/home/ubuntu/test/static/files/";
        File newFile = new File(rootPath + newFileName);
        System.out.println(rootPath + newFileName);
        //定义向数据库中存取的文件路径
        String src = rootPath1 + newFileName;

//        String rootPath = "/files/";
//        File newFile = new File(rootPath + newFileName);
//        System.out.println(rootPath + newFileName);
//        //定义向数据库中存取的文件路径
//        String src = rootPath + newFileName;

        if (!newFile.getParentFile().exists()) {
            newFile.getParentFile().mkdirs();
        } else {
            System.out.println(newFile.getParentFile());
        }

        System.out.println("absolute path: " + newFile.getAbsolutePath());
        if (!newFile.exists()) {
            try {
                //使用transferTo()方法将文件存到所在服务器上
                file.transferTo(newFile);
            } catch (Exception e) {
                System.out.println("file transfer error------------\n");
                System.out.println(e.getMessage());
            }
        }
        return src;
    }

}
