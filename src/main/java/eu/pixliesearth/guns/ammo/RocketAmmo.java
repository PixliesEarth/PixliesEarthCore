package eu.pixliesearth.guns.ammo;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import eu.pixliesearth.guns.CustomGun;
import eu.pixliesearth.guns.PixliesAmmo;
import eu.pixliesearth.utils.ItemBuilder;

public class RocketAmmo extends PixliesAmmo {

    public RocketAmmo(Location location, CustomGun gun) {
        super(location, gun, 3.0);
    }

    @Override
    public RocketAmmo createNewOne(Location location, CustomGun gun) {
        return new RocketAmmo(location, gun);
    }

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(Material.STICK).setDisplayName("§3Rocket").setCustomModelData(9).addLoreLine("§7§oUsed for RPG-7").build();
    }

}
