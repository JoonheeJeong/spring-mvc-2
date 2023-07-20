package hello.itemservice;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import hello.itemservice.domain.item.ItemVForm;
import hello.itemservice.domain.item.ItemVFormRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final ItemRepository itemRepository;
    private final ItemVFormRepository itemVFormRepository;

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));

        itemVFormRepository.save(new ItemVForm("itemA", 10000, 10));
        itemVFormRepository.save(new ItemVForm("itemB", 20000, 20));
    }

}