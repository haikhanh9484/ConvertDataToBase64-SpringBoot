package com.example.demo.controller;

import com.example.demo.model.Data;
import com.example.demo.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
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

//    @PostMapping("/convert/img/decode")
//    public Map<String, String> Base64ToImg(@RequestBody Data data) {
//        File serverFile = new File( new File("src\\main\\resources\\static").getAbsolutePath() + "\\demo.jpg");
//        if(serverFile.delete())
//        {
//            System.out.println("File deleted successfully");
//        }
//        else
//        {
//            System.out.println("Failed to delete the file");
//        }
//
//        HashMap<String, String> map = new HashMap<>();
//        if(convertStringToImage(data.getValue())){
//            map.put("value", "true");
//            return map;
//        }else{
//            map.put("value", "false");
//            return map;
//        }
//    }

    @PostMapping("/convert/img/decode")
    public Boolean Base64ToImg(@RequestBody Data data) {
        File serverFile = new File(new File("src\\main\\resources\\static").getAbsolutePath() + "\\demo.jpg");
        if (serverFile.delete()) {
            System.out.println("File deleted successfully");
        } else {
            System.out.println("Failed to delete the file");
        }
        return dataService.Base64ToImg(data);
    }

    @GetMapping(value = "/img", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> GetImg() throws IOException {
        File serverFile = new File(new File("src\\main\\resources\\static").getAbsolutePath() + "\\demo.jpg");

        byte[] fileContent = Files.readAllBytes(serverFile.toPath());
        System.out.println("serverFile : " + serverFile);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(fileContent);
    }
}
