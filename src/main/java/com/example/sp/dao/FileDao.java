package com.example.sp.dao;


import com.example.sp.model.ProjectFile;

import java.util.List;
import java.util.Map;

/**
 *    
 *  @author luo tianyue 
 *  @Date 2020/6/18  
 *  @Time 17:13  
 */
public interface FileDao {
    void addFile(ProjectFile file);
    List<ProjectFile> getFileList(Map<String, String> params);
    void deleteFile(int file_id);
}
