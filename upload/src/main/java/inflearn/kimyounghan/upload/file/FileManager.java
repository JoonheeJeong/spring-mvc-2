package inflearn.kimyounghan.upload.file;

import inflearn.kimyounghan.upload.domain.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileManager {

    @Value("${file.dir}")
    private String fileDir;

    public List<UploadFile> storeFiles(List<MultipartFile> imageFiles) throws IOException {
        List<UploadFile> ret = new ArrayList<>();
        for (MultipartFile file : imageFiles)
            if (!file.isEmpty())
                ret.add(storeFile(file));
        return ret;
    }

    public UploadFile storeFile(MultipartFile file) throws IOException {
        if (file.isEmpty())
            return null;

        String filename = file.getOriginalFilename();
        String storedName = getStoredName(filename);

        String fullPath = getFullPath(storedName);
        file.transferTo(new File(fullPath));

        return new UploadFile(filename, storedName);
    }

    public String getFullPath(String storedName) {
        return fileDir + storedName;
    }

    private String getStoredName(String filename) {
        String extension = getExtension(filename);
        String uuid = UUID.randomUUID().toString();
        return uuid + '.' + extension;
    }

    private String getExtension(String filename) {
        return filename.substring(filename.lastIndexOf('.') + 1);
    }
}
