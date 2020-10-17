package eu.pixliesearth.core.custom;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Data
@AllArgsConstructor
public class BlockDrop {

    private ItemStack toDrop;
    private Material block;

}
