package inflearn.kimyounghan.upload.domain;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ItemRepository {

    private final Map<Long, Item> itemMap = new HashMap<>();
    private long sequence = 0L;

    public Item save(Item item) {
        item.setId(++sequence);
        itemMap.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id) {
        return itemMap.get(id);
    }
}
