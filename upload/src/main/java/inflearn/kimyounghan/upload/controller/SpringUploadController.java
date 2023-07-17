package inflearn.kimyounghan.upload.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

@Slf4j
@Controller
@RequestMapping("/spring")
public class SpringUploadController {


    @Value("${file.dir}")
    private String fileDir;
    @GetMapping("/upload")
    public String getUploadForm() {
        return "upload-form";
    }

    @PostMapping("/upload")
    public String uploadNewProduct(
            @RequestParam String itemName,
            @RequestParam MultipartFile file) throws IOException {

        log.info("itemName={}", itemName);
        log.info("file={}", file);

        if (!file.isEmpty()) {
            String filename = file.getOriginalFilename();
            log.info("filename={}", filename);

            String fullPath = fileDir + filename;
            log.info("fullPath={}", fullPath);

            file.transferTo(new File(fullPath));
        }

        return "upload-form";
    }
}
