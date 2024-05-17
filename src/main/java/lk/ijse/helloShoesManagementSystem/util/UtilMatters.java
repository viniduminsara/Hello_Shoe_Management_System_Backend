package lk.ijse.helloShoesManagementSystem.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

public class UtilMatters {

    public static String convertBase64(MultipartFile data){
        try {
            return Base64.getEncoder().encodeToString(data.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
