package com.nebula.pdf.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
public class FileUpload {
    public String uploadFile(MultipartFile file) {
        return "File uploaded successfully";
    }
}
