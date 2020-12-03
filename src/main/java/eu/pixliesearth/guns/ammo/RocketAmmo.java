package eu.pixliesearth.guns.ammo;

import eu.pixliesearth.guns.PixliesAmmo;
import eu.pixliesearth.guns.PixliesGun;
import eu.pixliesearth.utils.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class RocketAmmo extends PixliesAmmo {

    public RocketAmmo(Location location, PixliesGun gun) {
        super(location, gun, 3.0);
    }

    @Override
    public RocketAmmo createNewOne(Location location, PixliesGun gun) {
        return new RocketAmmo(location, gun);
    }

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(Material.STICK).setDisplayName("§3Rocket").setCustomModelData(5).addLoreLine("§7§oUsed for RPG-7").build();
    }

}
