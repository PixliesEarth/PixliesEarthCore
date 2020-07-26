package eu.pixliesearth.core.guns.ammoObjects;

import eu.pixliesearth.core.guns.Ammo;
import eu.pixliesearth.utils.ItemBuilder;
import org.bukkit.Material;

public class AKAmmo extends Ammo {

    public AKAmmo() {
        super("7.62mm", new ItemBuilder(Material.WOODEN_AXE).setCustomModelData(8).setDisplayName("§b7.62mm").build());
    }

}
