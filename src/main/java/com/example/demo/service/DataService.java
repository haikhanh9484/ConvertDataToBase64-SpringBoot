package com.example.demo.service;

import com.example.demo.model.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class DataService {

    public static byte[] decodeImage(String imageDataString) {
        return org.apache.tomcat.util.codec.binary.Base64.decodeBase64(imageDataString);
    }

    public Map<String, String> TextToBase64(Data data) {
        data.setValue(Base64.getEncoder().encodeToString(data.getValue().getBytes()));
//        System.out.println(data.getValue());
        HashMap<String, String> map = new HashMap<>();
        map.put("value", data.getValue());
        return map;
    }

    public Map<String, String> Base64ToText(Data data) {
        HashMap<String, String> map = new HashMap<>();
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(data.getValue());
            String decodedString = new String(decodedBytes);
//        System.out.println(decodedString);
            map.put("value", decodedString);
            return map;
        } catch (Exception e) {
            map.put("value", "Error: Input is not base64");
            return map;
        }
    }

    public Map<String, String> ImgToBase64(MultipartFile multipartFile) throws IOException {
        File file = new File(multipartFile.getName());
        try (OutputStream os = new FileOutputStream(file)) {
            os.write(multipartFile.getBytes());
        }

        String base64Image = "";
        try (FileInputStream imageInFile = new FileInputStream(file.getName())) {
            //Reading a Image file from file system
            byte imageData[] = new byte[(int) file.length()];
            imageInFile.read(imageData);
            base64Image = Base64.getEncoder().encodeToString(imageData);
        } catch (FileNotFoundException e) {
            System.out.println("Image not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the Image " + ioe);
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("value", base64Image);
        return map;
    }

    public boolean Base64ToImg(Data data) {
        if (convertStringToImage(data.getValue())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean convertStringToImage(String base64) {
        try {
            byte[] imageByteArray = decodeImage(base64);
            String fileLocation = new File("src\\main\\resources\\static").getAbsolutePath() + "\\demo.jpg";

            FileOutputStream imageOutFile = new FileOutputStream(fileLocation);
            imageOutFile.write(imageByteArray);
            imageOutFile.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
