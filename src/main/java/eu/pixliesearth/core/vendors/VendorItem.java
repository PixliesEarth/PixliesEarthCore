package eu.pixliesearth.core.vendors;

import eu.pixliesearth.core.machines.Machine;
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
        return (ItemStack) Machine.deserialize(rawItem);
    }

}
