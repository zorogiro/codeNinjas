package com.esprit.tn.forum.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtil {

    public static void saveFile(String fileName, MultipartFile multipartFile) throws IOException {
        Path uploadDirectory= Paths.get("C:\\Users\\Mon Pc\\Documents\\GitHub\\codeNinjas1\\src\\main\\java\\com\\esprit\\tn\\forum\\FilesUpload");
        try {
            InputStream inputStream=multipartFile.getInputStream();
            Path filePath=uploadDirectory.resolve(fileName);
            Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException e){
            throw  new IOException("Error saving uploaded file: "+fileName,e);
        }
    }
}
