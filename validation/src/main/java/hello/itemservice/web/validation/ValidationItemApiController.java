package hello.itemservice.web.validation;

import hello.itemservice.domain.item.ItemVForm;
import hello.itemservice.domain.item.ItemVFormRepository;
import hello.itemservice.web.validation.form.ItemSaveForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/validation/api/items")
@RestController
public class ValidationItemApiController {

    private final ItemVFormRepository repository;

    @PostMapping("/add")
    public Object addItem(
            @RequestBody @Valid ItemSaveForm form,
            BindingResult bindingResult) {

        log.info("Item 등록 API");

        Map<String, String> errors = new HashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("errors: {}", bindingResult);
            return bindingResult.getAllErrors();
        }

        return repository.save(ItemVForm.fromItemSaveForm(form));
    }
}
