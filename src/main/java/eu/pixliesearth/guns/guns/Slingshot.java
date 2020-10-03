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

public class Slingshot extends PixliesGun {

    public Slingshot(int ammo, UUID uuid) {
        super(uuid, "§6Slingshot", new ItemBuilder(Material.GOLDEN_HOE).setCustomModelData(55).setDisplayName("§6Slingshot §8| §8[§c1§7/§c1§8]").addLoreLine("§7Ammo: §3Cobblestone").addLoreLine("§7Origin: §4Unknown").addLoreLine("§7Range: §320 blocks").addLoreLine("§7Accuracy: §30.04").addNBTTag("gunId", uuid.toString(), NBTTagType.STRING).build(), PixliesAmmo.AmmoType.COBBLESTONE, 40, ammo,1, 0.04, 1000, Arrays.asList(RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK));
    }

}
