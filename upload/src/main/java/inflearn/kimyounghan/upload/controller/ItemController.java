package inflearn.kimyounghan.upload.controller;

import inflearn.kimyounghan.upload.domain.Item;
import inflearn.kimyounghan.upload.domain.ItemForm;
import inflearn.kimyounghan.upload.domain.ItemRepository;
import inflearn.kimyounghan.upload.domain.UploadFile;
import inflearn.kimyounghan.upload.file.FileManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;
    private final FileManager fileManager;

    @GetMapping("/items/new")
    public String getRegisterForm(@ModelAttribute ItemForm itemForm) {
        return "item-form";
    }

    @PostMapping("/items/new")
    public String save(
            @ModelAttribute ItemForm itemForm,
            RedirectAttributes redirectAttributes) throws IOException {

        log.info("itemName={}", itemForm.getItemName());
        log.info("attachedFile={}", itemForm.getAttachedFile());
        log.info("imageFiles={}", itemForm.getImageFiles());

        UploadFile attachedFile = fileManager.storeFile(itemForm.getAttachedFile());
        List<UploadFile> imageFiles = fileManager.storeFiles(itemForm.getImageFiles());

        Item item = itemRepository.save(new Item(itemForm.getItemName(), attachedFile, imageFiles));

        redirectAttributes.addAttribute("id", item.getId());
        return "redirect:/items/{id}";
    }

    @GetMapping("/items/{id}")
    public String getItemView(
            @PathVariable Long id,
            Model model) {

        Item item = itemRepository.findById(id);
        model.addAttribute("item", item);
        return "item-view";
    }

    @ResponseBody
    @GetMapping("/images/{storedName}")
    public Resource getImageFile(@PathVariable String storedName) throws MalformedURLException {
        return getResource(storedName);
    }

    @GetMapping("/attach/{itemId}")
    public ResponseEntity<Resource> getAttachedFile(@PathVariable Long itemId) throws MalformedURLException {
        Item item = itemRepository.findById(itemId);
        String storedName = item.getAttachedFile().getStoredName();
        Resource resource = getResource(storedName);

        String uploadedName = item.getAttachedFile().getUploadedName();
        String encodedUploadedName = UriUtils.encode(uploadedName, StandardCharsets.UTF_8);
        String contentDisposition = String.format("attachment; filename=\"%s\"", encodedUploadedName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }

    private UrlResource getResource(String storedName) throws MalformedURLException {
        return new UrlResource("file:" + fileManager.getFullPath(storedName));
    }
}
