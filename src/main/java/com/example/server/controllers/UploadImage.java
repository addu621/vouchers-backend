package com.example.server.controllers;

import com.example.server.services.UploadImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UploadImage {
    @Autowired
    private UploadImageService uploadImageService;

    @PostMapping("/upload")
    public Map<String,String> uploadFile(@RequestParam("file") MultipartFile file) {
        String url = uploadImageService.uploadFile(file);
        Map<String, String> res = new HashMap<>();

        res.put("url",url);
        return res;
    }
}
