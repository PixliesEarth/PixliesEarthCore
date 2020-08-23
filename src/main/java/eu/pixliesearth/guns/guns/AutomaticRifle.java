package eu.pixliesearth.guns.guns;

import eu.pixliesearth.guns.PixliesAmmo;
import eu.pixliesearth.guns.PixliesGun;
import eu.pixliesearth.utils.ItemBuilder;
import org.bukkit.Material;

import java.util.Arrays;

import static org.bukkit.event.block.Action.*;

public class AutomaticRifle extends PixliesGun {

    public AutomaticRifle() {
        super("§c§lAuto-rifle", new ItemBuilder(Material.WOODEN_AXE).setDisplayName("§c§lAuto-rifle §8[§c30§7/§c30§8]").build(), PixliesAmmo.AmmoType.RIFLE_AMMO, 6, 30,30, 1, 400, Arrays.asList(RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK));
    }

}
