package eu.pixliesearth.guns.guns;

import eu.pixliesearth.guns.PixliesAmmo;
import eu.pixliesearth.guns.PixliesGun;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.NBTTagType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.UUID;

import static org.bukkit.event.block.Action.RIGHT_CLICK_AIR;
import static org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK;

public class MP5 extends PixliesGun {

    public MP5(int ammo, UUID uuid) {
        super(uuid, "§c§lMP5", new ItemBuilder(Material.CARROT_ON_A_STICK).setCustomModelData(7).setDisplayName("§c§lMP5 §8| §8[§c15§7/§c15§8]").addLoreLine("§7Ammo: §36.62x51mm NATO").addLoreLine("§7Origin: §cGermany").addLoreLine("§7Range: §320 blocks").addLoreLine("§7Accuracy: §30.1").addNBTTag("gunId", uuid.toString(), NBTTagType.STRING).build(), PixliesAmmo.AmmoType.NATO762x51, 40, ammo,15, 0.1, 300, Arrays.asList(RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK));
    }

}
