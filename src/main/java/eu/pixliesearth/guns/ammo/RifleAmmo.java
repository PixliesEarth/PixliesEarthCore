package eu.pixliesearth.guns.ammo;

import eu.pixliesearth.guns.PixliesAmmo;
import eu.pixliesearth.guns.PixliesGun;
import eu.pixliesearth.utils.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class RifleAmmo extends PixliesAmmo {

    public RifleAmmo(Location location, PixliesGun gun) {
        super(location, gun, 3.0);
    }

    @Override
    public RifleAmmo createNewOne(Location location, PixliesGun gun) {
        return new RifleAmmo(location, gun);
    }
    
    @Override
    public ItemStack getItem() {
        return new ItemBuilder(Material.STICK).setDisplayName("§3Rifle Ammo").setCustomModelData(5).addLoreLine("§7§oUsed for rifles").build();
    }

}
