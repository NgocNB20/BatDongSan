package project.batdongsan.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
public class ImageUploadController {
    private static final String UPLOAD_DIR = "C:\\BatDongSan\\BatDongSan\\src\\main\\resources\\static\\assets\\img\\";
    @PostMapping("/api/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please select a file to upload.");
        }
        try {
            String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            String filePath = UPLOAD_DIR + uniqueFileName;
            file.transferTo(new File(filePath));

            return ResponseEntity.ok("/download/assets/img/" + uniqueFileName);
        } catch (IOException e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file.");
        }
    }

    @GetMapping("/download/assets/img/{imageName:.+}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imageName) throws IOException {
        // Tạo đường dẫn tới thư mục chứa hình ảnh tĩnh
        Path imagePath = Paths.get(UPLOAD_DIR).resolve(imageName);

        // Đọc hình ảnh từ đường dẫn
        byte[] imageBytes = Files.readAllBytes(imagePath);

        // Trả về hình ảnh với định dạng MediaType.IMAGE_JPEG
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
    }

}

