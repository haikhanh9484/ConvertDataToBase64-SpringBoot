package com.example.demo.service;

import com.example.demo.model.Data;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class DataService {
    private byte[] imageByteArray;

    public Map<String, String> TextToBase64(Data data) {
        String base64 = Base64.getEncoder().encodeToString(data.getValue().getBytes());
        HashMap<String, String> map = new HashMap<>();
        map.put("value", base64);
        return map;
    }

    public Map<String, String> Base64ToText(Data data) {
        HashMap<String, String> map = new HashMap<>();
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(data.getValue());
            String decodedString = new String(decodedBytes);
            map.put("value", decodedString);
            return map;
        } catch (Exception e) {
            map.put("value", "Error: Input is not base64");
            return map;
        }
    }

    public Map<String, String> ImgToBase64(MultipartFile multipartFile){
        String base64Image = "";
        try{
            //convert MultipartFile to File
            File file = new File(multipartFile.getName());
            FileOutputStream fos = new FileOutputStream(file);
            fos.write( multipartFile.getBytes() );
            fos.close();
            //convert file to base64
            byte[] fileContent = Files.readAllBytes(file.toPath());
            base64Image = Base64.getEncoder().encodeToString(fileContent);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("value", base64Image);
        return map;
    }

    public boolean Base64ToByte(Data data) {
        try {
            imageByteArray = decodeImage(data.getValue());
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            imageByteArray = new byte[]{};
            return false;
        }
    }

    public static byte[] decodeImage(String imageDataString) {
        return org.apache.tomcat.util.codec.binary.Base64.decodeBase64(imageDataString);
    }

    public ResponseEntity<byte[]> ByteToImage(){
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageByteArray);
    }
}
