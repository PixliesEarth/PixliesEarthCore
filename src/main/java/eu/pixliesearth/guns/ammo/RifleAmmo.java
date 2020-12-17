package eu.pixliesearth.guns.ammo;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import eu.pixliesearth.guns.CustomGun;
import eu.pixliesearth.guns.PixliesAmmo;
import eu.pixliesearth.utils.ItemBuilder;

public class RifleAmmo extends PixliesAmmo {

    public RifleAmmo(Location location, CustomGun gun) {
        super(location, gun, 3.0);
    }

    @Override
    public RifleAmmo createNewOne(Location location, CustomGun gun) {
        return new RifleAmmo(location, gun);
    }
    
    @Override
    public ItemStack getItem() {
        return new ItemBuilder(Material.STICK).setDisplayName("§3Rifle Ammo").setCustomModelData(5).addLoreLine("§7§oUsed for rifles").build();
    }

}
