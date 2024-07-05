package com.green.jhjh.contentManager.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
public class FileService {
    public String uploadFile(String uploadPath, String originalFilename, byte[] fileData) throws Exception{
        UUID uuid = UUID.randomUUID();

        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

        String savedFileName = uuid.toString() + extension;

        String fileUploadFullUrl = uploadPath + "/" + savedFileName;

        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);

        fos.write(fileData);
        fos.close();

        return savedFileName;
    }

    public boolean deleteFile(String filePath) throws Exception{
        File deleteFile = new File(filePath);

        if (deleteFile.exists()){
            deleteFile.delete();
        }else {
            System.out.println("파일이 존재하지 않습니다.");

        }
        return false;
    }


}
