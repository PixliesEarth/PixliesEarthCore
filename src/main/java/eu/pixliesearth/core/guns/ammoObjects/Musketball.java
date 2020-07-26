package eu.pixliesearth.core.guns.ammoObjects;

import eu.pixliesearth.core.guns.Ammo;
import eu.pixliesearth.utils.ItemBuilder;
import org.bukkit.Material;

public class Musketball extends Ammo {

    public Musketball() {
        super("Musketball", new ItemBuilder(Material.WOODEN_AXE).setCustomModelData(9).setDisplayName("§bMusketball").build());
    }

}
