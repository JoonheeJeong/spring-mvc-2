package inflearn.kimyounghan.upload.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class ItemForm {

    private String itemName;
    private MultipartFile attachedFile;
    private List<MultipartFile> imageFiles;
}
