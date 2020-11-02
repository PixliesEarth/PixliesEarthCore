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

public class Uzi extends PixliesGun {

    public Uzi(int ammo, UUID uuid) {
        super(uuid, "§3§lUzi", new ItemBuilder(Material.CARROT_ON_A_STICK).setCustomModelData(4).setDisplayName("§3§lUzi §8| §8[§c32§7/§c32§8]").addLoreLine("§7Ammo: §39mm").addLoreLine("§7Origin: §3Israel").addLoreLine("§7Range: §340 blocks").addLoreLine("§7Accuracy: §30.04").addNBTTag("gunId", uuid.toString(), NBTTagType.STRING).build(), PixliesAmmo.AmmoType.NINEMM, 40, ammo,32, 0.04, 200, Arrays.asList(RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK));
    }

}
