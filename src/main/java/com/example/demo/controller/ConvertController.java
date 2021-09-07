package com.example.demo.controller;

import com.example.demo.model.Data;
import com.example.demo.service.DataService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class ConvertController {

    @Autowired
    public DataService dataService;

    @PostMapping("/convert/encode")
    public Map<String, String> TextToBase64(@RequestBody Data data) {
        return dataService.TextToBase64(data);
    }

    @PostMapping("/convert/decode")
    public Map<String, String> Base64ToText(@RequestBody Data data) {
        return dataService.Base64ToText(data);
    }

    @PostMapping("/convert/img/encode")
    public Map<String, String> ImgToBase64(@RequestParam("imageFile") MultipartFile multipartFile) throws IOException {
        return dataService.ImgToBase64(multipartFile);
    }

    @PostMapping("/convert/img/decode")
    public Map<String, String> Base64ToImg(@RequestBody Data data) {
        convertStringToImage(data.getValue());

        return dataService.Base64ToImg("D:/demo.jpg");
    }

    public void convertStringToImage(String base64) {
        try {
            byte[] imageByteArray = decodeImage(base64);

            FileOutputStream imageOutFile = new FileOutputStream("D:/demo.jpg");
            imageOutFile.write(imageByteArray);
            imageOutFile.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public static byte[] decodeImage(String imageDataString) {
        return Base64.decodeBase64(imageDataString);
    }
}
