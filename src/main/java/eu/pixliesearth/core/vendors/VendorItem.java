package eu.pixliesearth.core.vendors;

import eu.pixliesearth.utils.InventoryUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.inventory.ItemStack;

@Data
@AllArgsConstructor
public class VendorItem {

    private String rawItem;
    private Double buyPrice;
    private Double sellPrice;

    public ItemStack getRawItemStack() {
        return (ItemStack) InventoryUtils.deserialize(rawItem);
    }

}
