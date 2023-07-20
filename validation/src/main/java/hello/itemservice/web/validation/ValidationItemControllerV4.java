package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemVForm;
import hello.itemservice.domain.item.ItemVFormRepository;
import hello.itemservice.web.validation.form.ItemForm;
import hello.itemservice.web.validation.form.ItemSaveForm;
import hello.itemservice.web.validation.form.ItemUpdateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/validation/v4/items")
@RequiredArgsConstructor
public class ValidationItemControllerV4 {

    private final ItemVFormRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<ItemVForm> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v4/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        ItemVForm item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v4/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v4/addForm";
    }

    @PostMapping("/add")
    public String addItem(
            @Valid @ModelAttribute("item") ItemSaveForm form,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        validateGlobal(form, bindingResult);

        // 에러 발생 시 에러 결과를 모델에 담아서 상품 등록 폼으로 포워딩
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "validation/v4/addForm";
        }

        ItemVForm itemVForm = ItemVForm.fromItemSaveForm(form);

        // 정상 로직
        ItemVForm savedItem = itemRepository.save(itemVForm);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v4/items/{itemId}";
    }

    private void validateGlobal(ItemForm form, BindingResult bindingResult) {
        // 검증 로직, 복합 룰
        if (form.getPrice() != null && form.getQuantity() != null) {
            final int MIN_TOTAL_PRICE = 10_000;
            int totalPrice = form.getPrice() * form.getQuantity();
            if (totalPrice < MIN_TOTAL_PRICE) {
                // level.1으로 자동 적용
                bindingResult.reject("minTotalPrice", new Object[]{MIN_TOTAL_PRICE, totalPrice}, null);
            }
        }
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        ItemVForm item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v4/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(
            @PathVariable Long itemId,
            @Valid @ModelAttribute("item") ItemUpdateForm form,
            BindingResult bindingResult) {

        validateGlobal(form, bindingResult);

        if (bindingResult.hasErrors()) {
            log.info("bindingResult={}", bindingResult);
            return "validation/v4/editForm";
        }

        ItemVForm item = ItemVForm.fromItemUpdateForm(form);

        itemRepository.update(itemId, item);
        return "redirect:/validation/v4/items/{itemId}";
    }

}

