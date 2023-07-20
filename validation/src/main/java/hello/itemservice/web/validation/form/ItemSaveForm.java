package hello.itemservice.web.validation.form;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ItemSaveForm implements ItemForm {

    @NotBlank
    private String itemName;
    @NotNull
    @Range(min = 1_000, max = 1_000_000)
    private Integer price;
    @NotNull
    @Max(9_999)
    private Integer quantity;

}
