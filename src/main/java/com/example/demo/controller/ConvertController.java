package com.example.demo.controller;

import com.example.demo.model.Data;
import com.example.demo.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
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

    @PostMapping("/convert/img/decode")
    public Boolean Base64ToImg(@RequestBody Data data) {
        return dataService.Base64ToByte(data);
    }

    @GetMapping(value = "/img", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> GetImg(){
        return dataService.ByteToImage();
    }
}
