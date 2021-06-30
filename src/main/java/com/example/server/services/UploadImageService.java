package com.example.server.services;
;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import org.apache.commons.codec.binary.Base64;

import static com.cloudinary.utils.ObjectUtils.asMap;

@Service
public class UploadImageService {

        @Autowired
        private Cloudinary cloudinaryConfig;

        public String uploadFile(MultipartFile file) {
            try {
                byte[] bytesEncoded = Base64.encodeBase64(file.getBytes());

                StringBuilder sb = new StringBuilder();
                sb.append("data:image/png;base64,");
                sb.append(Base64.encodeBase64URLSafeString(bytesEncoded));
                String img = sb.toString();

                Map uploadResult = cloudinaryConfig.uploader().upload(img,ObjectUtils.asMap("resource_type", "auto"));

                return  uploadResult.get("url").toString();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }




    }

