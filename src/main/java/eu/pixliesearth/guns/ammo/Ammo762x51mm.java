package eu.pixliesearth.guns.ammo;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import eu.pixliesearth.guns.CustomGun;
import eu.pixliesearth.guns.PixliesAmmo;
import eu.pixliesearth.utils.ItemBuilder;

public class Ammo762x51mm extends PixliesAmmo {

    public Ammo762x51mm(Location location, CustomGun gun) {
        super(location, gun, 2.0);
    }

    @Override
    public Ammo762x51mm createNewOne(Location location, CustomGun gun) {
        return new Ammo762x51mm(location, gun);
    }

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(Material.STICK).setDisplayName("§37.62x51mm NATO").setCustomModelData(7).build();
    }

}
