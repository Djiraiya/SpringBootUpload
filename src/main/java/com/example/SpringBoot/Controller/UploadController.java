package com.example.SpringBoot.Controller;

import com.example.SpringBoot.Model.UploadModel;
import com.example.SpringBoot.Service.UploadService;
import com.google.gson.Gson;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

        String url = "";

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        Gson gson = new Gson();
        StringEntity entity = new StringEntity(gson.toJson(uploadModel));
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setEntity(entity);
        client.execute(httpPost);
        client.close();
        return "uploadview";
    }
}
