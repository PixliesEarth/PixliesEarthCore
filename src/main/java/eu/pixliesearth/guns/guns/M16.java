package eu.pixliesearth.guns.guns;

import eu.pixliesearth.guns.PixliesAmmo;
import eu.pixliesearth.guns.PixliesGun;
import eu.pixliesearth.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.UUID;

import static org.bukkit.event.block.Action.*;

public class M16 extends PixliesGun {

    public M16(int ammo, UUID uuid) {
        super(uuid, "§c§lM-16", new ItemBuilder(Material.WOODEN_AXE).setCustomModelData(6).setDisplayName("§c§lM-16 §8| §8[§c30§7/§c30§8]").addLoreLine("§7Ammo: §3RifleAmmo").addLoreLine("§7Range: §315 blocks").addLoreLine("§7Accuracy: §30.1").addLoreLine("§7§oID: " + uuid.toString()).build(), PixliesAmmo.AmmoType.RIFLE_AMMO, 25, ammo,30, 0.1, 400, Arrays.asList(RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK));
    }

    @Override
    public ItemStack reloadItem() {
        return new ItemBuilder(getItem()).setDisplayName("§c§lM-16 §8| §8[§c" + getAmmo() + "§7/§c30§8]").build();
    }

}
