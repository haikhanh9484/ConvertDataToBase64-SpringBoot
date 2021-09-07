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
    public Map<String, String> TextToBase64(Data data) {
        data.setValue(Base64.getEncoder().encodeToString(data.getValue().getBytes()));
        System.out.println(data.getValue());

        HashMap<String, String> map = new HashMap<>();
        map.put("value", data.getValue());
        return map;
    }

    public Map<String, String> Base64ToText(Data data) {
        byte[] decodedBytes = Base64.getDecoder().decode(data.getValue());
        String decodedString = new String(decodedBytes);
        System.out.println(decodedString);

        HashMap<String, String> map = new HashMap<>();
        map.put("value", decodedString);
        return map;
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

    public Map<String, String> Base64ToImg(String string) {
        HashMap<String, String> map = new HashMap<>();
        map.put("value", string);
        return map;
    }
}
