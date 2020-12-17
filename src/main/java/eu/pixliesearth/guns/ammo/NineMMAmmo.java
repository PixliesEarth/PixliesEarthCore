package eu.pixliesearth.guns.ammo;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import eu.pixliesearth.guns.CustomGun;
import eu.pixliesearth.guns.PixliesAmmo;
import eu.pixliesearth.utils.ItemBuilder;

public class NineMMAmmo extends PixliesAmmo {

    public NineMMAmmo(Location location, CustomGun gun) {
        super(location, gun, 2.0);
    }

    @Override
    public NineMMAmmo createNewOne(Location location, CustomGun gun) {
        return new NineMMAmmo(location, gun);
    }

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(Material.STICK).setDisplayName("§39mm Ammo").setCustomModelData(6).build();
    }

}
