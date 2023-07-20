package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemVFormRepository {

    private static final Map<Long, ItemVForm> store = new HashMap<>(); //static
    private static long sequence = 0L; //static

    public ItemVForm save(ItemVForm item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public ItemVForm findById(Long id) {
        return store.get(id);
    }

    public List<ItemVForm> findAll() {
        return new ArrayList<>(store.values());
    }

    public void update(Long itemId, ItemVForm updateParam) {
        ItemVForm findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore() {
        store.clear();
    }

}
