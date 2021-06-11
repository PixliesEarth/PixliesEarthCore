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

public class RifleAmmo extends PixliesAmmo {

    public RifleAmmo(Location location, CustomGun gun) {
        super(location, gun, 2.5);
    }

    @Override
    public RifleAmmo createNewOne(Location location, CustomGun gun) {
        return new RifleAmmo(location, gun);
    }
    
    @Override
    public ItemStack getItem() {
        return CustomItemUtil.getItemStackFromUUID("Ammo:Rifle-Ammo");
    }

}
