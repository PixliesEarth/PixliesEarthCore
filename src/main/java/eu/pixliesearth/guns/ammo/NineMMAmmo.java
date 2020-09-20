package eu.pixliesearth.guns.ammo;

import eu.pixliesearth.guns.PixliesAmmo;
import eu.pixliesearth.guns.PixliesGun;
import eu.pixliesearth.utils.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class NineMMAmmo extends PixliesAmmo {

    public NineMMAmmo(Location location, PixliesGun gun) {
        super(location, gun, 2.0);
    }

    @Override
    public NineMMAmmo createNewOne(Location location, PixliesGun gun) {
        return new NineMMAmmo(location, gun);
    }

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(Material.STICK).setDisplayName("§39mm Ammo").setCustomModelData(6).build();
    }

}
