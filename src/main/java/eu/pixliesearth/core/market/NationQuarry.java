package eu.pixliesearth.core.market;

import eu.pixliesearth.utils.InventoryUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@Data
@AllArgsConstructor
public class NationQuarry {

    private Map<String, Integer> items;
    private Map<String, Double> prices;
    private Map<String, TariffType> tariffs;

    enum TariffType {

        EMBARGO,
        TIMES_2,
        TIMES_4

    }

    public boolean contains(ItemStack item) {
        return items.containsKey(InventoryUtils.serialize(item.asQuantity(1)));
    }

}
