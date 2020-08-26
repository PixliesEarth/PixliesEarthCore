package eu.pixliesearth.guns.guns;

import eu.pixliesearth.guns.PixliesAmmo;
import eu.pixliesearth.guns.PixliesGun;
import eu.pixliesearth.utils.ItemBuilder;
import org.bukkit.Material;

import java.util.Arrays;

import static org.bukkit.event.block.Action.*;

public class AutomaticRifle extends PixliesGun {

    public AutomaticRifle(int ammo) {
        super("§c§lAuto-rifle", new ItemBuilder(Material.WOODEN_AXE).setCustomModelData(6).setDisplayName("§c§lAuto-rifle §8| §8[§c30§7/§c30§8]").addLoreLine("§7Distance: §36 blocks").addLoreLine("§7Accuracy: §30.1").build(), PixliesAmmo.AmmoType.RIFLE_AMMO, 6, ammo,30, 0.1, 400, Arrays.asList(RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK));
    }

}
