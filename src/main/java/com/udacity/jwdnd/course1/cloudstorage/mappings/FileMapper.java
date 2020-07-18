package com.udacity.jwdnd.course1.cloudstorage.mappings;

import com.udacity.jwdnd.course1.cloudstorage.model.FileModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE USERID=#{userId}")
    List<FileModel> getFiles(Integer userId);

    @Select("SELECT * FROM FILES WHERE FILEID=#{fileId}")
    FileModel getFile(Integer fileId);

    @Insert("INSERT INTO FILES(filename, contenttype, filesize, userid, filedata) VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer insertFile(FileModel file);

    @Delete("DELETE FROM FILES WHERE FILEID=#{fileId}")
    Integer deleteFile(Integer fileId);

    @Select("SELECT * FROM FILES WHERE FILENAME=#{fileName}")
    FileModel getFileOnName(String fileName);
}
