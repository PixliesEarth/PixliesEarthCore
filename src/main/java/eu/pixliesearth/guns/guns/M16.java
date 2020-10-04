package eu.pixliesearth.guns.guns;

import eu.pixliesearth.guns.PixliesAmmo;
import eu.pixliesearth.guns.PixliesGun;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.NBTTagType;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.UUID;

import static org.bukkit.event.block.Action.RIGHT_CLICK_AIR;
import static org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK;

public class M16 extends PixliesGun {

    public M16(int ammo, UUID uuid) {
        super(uuid, "§c§lM-16", new ItemBuilder(Material.WOODEN_AXE).setCustomModelData(6).setDisplayName("§c§lM-16 §8| §8[§c30§7/§c30§8]").addLoreLine("§7Ammo: §3RifleAmmo").addLoreLine("§7Origin: §bUSA").addLoreLine("§7Range: §325 blocks").addLoreLine("§7Accuracy: §30.1").addNBTTag("gunId", uuid.toString(), NBTTagType.STRING).build(), PixliesAmmo.AmmoType.RIFLE_AMMO, 25, ammo,30, 0.1, 400, Arrays.asList(RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK));
    }

}
