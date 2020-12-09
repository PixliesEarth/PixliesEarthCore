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

public class M1911 extends PixliesGun {

    public M1911(int ammo, UUID uuid) {
        super(uuid, "§c§lM1911", new ItemBuilder(Material.CARROT_ON_A_STICK).setCustomModelData(8).setDisplayName("§c§lM1911 §8| §8[§c8§7/§c8§8]").addLoreLine("§7Ammo: §39mm").addLoreLine("§7Origin: §cUSA").addLoreLine("§7Range: §340 blocks").addLoreLine("§7Accuracy: §30.1").addNBTTag("gunId", uuid.toString(), NBTTagType.STRING).build(), PixliesAmmo.AmmoType.NINEMM, 40, ammo,8, 0.1, 800, Arrays.asList(RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK));
    }

}
