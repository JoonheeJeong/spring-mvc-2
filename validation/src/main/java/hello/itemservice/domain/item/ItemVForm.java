package hello.itemservice.domain.item;

import hello.itemservice.web.validation.form.ItemSaveForm;
import hello.itemservice.web.validation.form.ItemUpdateForm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ItemVForm {

    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;

    public ItemVForm(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }

    public static ItemVForm fromItemSaveForm(ItemSaveForm form) {
        return new ItemVForm(
                form.getItemName(),
                form.getPrice(),
                form.getQuantity());
    }

    public static ItemVForm fromItemUpdateForm(ItemUpdateForm form) {
        return new ItemVForm(
                form.getId(),
                form.getItemName(),
                form.getPrice(),
                form.getQuantity());
    }
}
