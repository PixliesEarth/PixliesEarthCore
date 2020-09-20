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

public class K98K extends PixliesGun {

    public K98K(int ammo, UUID uuid) {
        super(uuid, "§c§lKarabiner98k", new ItemBuilder(Material.CARROT_ON_A_STICK).setCustomModelData(6).setDisplayName("§c§lKarabiner98k §8| §8[§c5§7/§c5§8]").addLoreLine("§7Ammo: §3Rifle Ammo").addLoreLine("§7Origin: §cGermany").addLoreLine("§7Range: §340 blocks").addLoreLine("§7Accuracy: §30.04").addNBTTag("gunId", uuid.toString(), NBTTagType.STRING).build(), PixliesAmmo.AmmoType.RIFLE_AMMO, 40, ammo,5, 0.04, 800, Arrays.asList(RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK));
    }

}
