package com.example.researchgate.Service.Impl;


import com.example.researchgate.Config.FileUploadProperties;
import com.example.researchgate.Service.FileService;
import com.example.researchgate.Utils.FileName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FileUploadProperties uploadProperties;

    @Override
    public String uploadFile(MultipartFile file, String path) throws Exception {
        if (!uploadProperties.getAllowTypes().contains(file.getContentType())) {
            return "Wrong type";
        }
        if (file != null) {
            String originName = file.getOriginalFilename();
            String type = "";
            if (originName != null && originName.length() != 0)
                type = originName.substring(originName.lastIndexOf('.'));
            String fileName = "documents/" + path + type;
            try {
                File targetFile = new File(FileName.getFileStorePath(), fileName);
                String resourcePath = new File(FileName.getFileStorePath(), fileName).getAbsolutePath();
                FileName.ensureDirectory(resourcePath);
                boolean ret = targetFile.delete();
                if(!ret)
                    return "delete file fail";
                if (!targetFile.exists()) ret = targetFile.createNewFile();
                if(!ret)
                    return "create file fail";
                file.transferTo(targetFile);
                resourcePath =  "http://43.140.248.122:8090" + targetFile.getAbsolutePath();
                return resourcePath;
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        }
        return "error";
    }
}
