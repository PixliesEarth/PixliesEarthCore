package eu.pixliesearth.guns.ammo;

import eu.pixliesearth.core.custom.CustomItem;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.NBTTagType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import eu.pixliesearth.guns.CustomGun;
import eu.pixliesearth.guns.PixliesAmmo;
import eu.pixliesearth.utils.ItemBuilder;

public class NineMMAmmo extends PixliesAmmo {

    public NineMMAmmo(Location location, CustomGun gun) {
        super(location, gun, 1.5);
    }

    @Override
    public NineMMAmmo createNewOne(Location location, CustomGun gun) {
        return new NineMMAmmo(location, gun);
    }

    @Override
    public ItemStack getItem() {
        return CustomItemUtil.getItemStackFromUUID("Ammo:9mm");
    }

}
