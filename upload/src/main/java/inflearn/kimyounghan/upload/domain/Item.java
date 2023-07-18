package inflearn.kimyounghan.upload.domain;

import lombok.Getter;

import java.util.List;

@Getter
public class Item {

    private Long id;
    private final String itemName;
    private final UploadFile attachedFile;
    private final List<UploadFile> imageFiles;

    public void setId(Long id) {
        this.id = id;
    }

    public Item(String itemName, UploadFile attachedFile, List<UploadFile> imageFiles) {
        this.itemName = itemName;
        this.attachedFile = attachedFile;
        this.imageFiles = imageFiles;
    }
}
