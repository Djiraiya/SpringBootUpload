package com.example.SpringBoot.Service;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UploadService {

    public List<String> encodedImages(MultipartFile[] files) throws IOException {
        List<String> imagesList = new ArrayList<>();
        for (MultipartFile file : files) {
            if (file != null && !file.getOriginalFilename().isEmpty()) {
                imagesList.add(Base64.encodeBase64String(file.getBytes()));
            }
        }
        return imagesList;
    }

}
