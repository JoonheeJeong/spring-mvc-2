package inflearn.kimyounghan.upload.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UploadFile {

    private String uploadedName;
    private String storedName;
}
