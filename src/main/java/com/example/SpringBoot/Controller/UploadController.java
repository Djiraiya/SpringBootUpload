package com.example.SpringBoot.Controller;

import com.example.SpringBoot.Model.UploadModel;
import com.example.SpringBoot.Service.UploadService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Controller
public class UploadController {

    @Autowired
    UploadService uploadService;

    @RequestMapping("/")
    public String UploadPage(Model model) {
        return "uploadview";
    }

    @RequestMapping("/upload")
    public String upload(Model model, @RequestParam("content") String content, @RequestParam("files") MultipartFile[] files) throws IOException {
        UploadModel uploadModel = new UploadModel();
        List<String> encodedImagesList;
        encodedImagesList = uploadService.encodedImages(files);
        uploadModel.setContent(content);
        uploadModel.setImages(encodedImagesList);

        String urlVk = "https://jd-vk.herokuapp.com/api/data";
        String urlTg = "https://jd-tg.herokuapp.com/api/data";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        Gson gson = new Gson();
        HttpEntity<String> request = new HttpEntity<>(gson.toJson(uploadModel), httpHeaders);

        restTemplate.postForEntity(urlVk, request, String.class);
        restTemplate.postForEntity(urlTg, request, String.class);
        return "uploadview";
    }
}
