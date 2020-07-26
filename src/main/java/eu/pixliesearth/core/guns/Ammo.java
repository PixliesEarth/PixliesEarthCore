package eu.pixliesearth.core.guns;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.inventory.ItemStack;

@Data
@AllArgsConstructor
public class Ammo {

    private String name;
    private ItemStack item;

}
