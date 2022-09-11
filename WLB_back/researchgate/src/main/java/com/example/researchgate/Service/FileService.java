package com.example.researchgate.Service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String uploadFile(MultipartFile file, String path) throws Exception;
}
