package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappings.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.FileModel;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.lang.module.Configuration;
import java.sql.Blob;
import java.util.List;

@Service
public class FileService {
    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public List<FileModel> getFiles(Integer userId){
        return fileMapper.getFiles(userId);
    }

    public int addFile(MultipartFile file, Integer userId) throws IOException{
        String fileName = file.getOriginalFilename();
        String contentType = file.getContentType();
        String fileSize = String.valueOf(file.getSize());
        InputStream fis = file.getInputStream();
        byte[] data = fis.readAllBytes();

        fis.close();

        return fileMapper.insertFile(new FileModel(null,fileName,contentType,fileSize,userId, data));

    }

    public int deleteFile(Integer fileId){
        return fileMapper.deleteFile(fileId);
    }

    public FileModel getFile(Integer fileId){
        return fileMapper.getFile(fileId);
    }

}
