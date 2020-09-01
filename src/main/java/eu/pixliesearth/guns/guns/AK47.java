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

public class AK47 extends PixliesGun {

    public AK47(int ammo, UUID uuid) {
        super(uuid, "§c§lAK-47", new ItemBuilder(Material.CARROT_ON_A_STICK).setCustomModelData(5).setDisplayName("§c§lAK-47 §8| §8[§c30§7/§c40§8]").addLoreLine("§7Ammo: §3RifleAmmo").addLoreLine("§7Origin: §bRUSSIA/USSR").addLoreLine("§7Range: §315 blocks").addLoreLine("§7Accuracy: §30.07").addNBTTag("gunId", uuid.toString(), NBTTagType.STRING).build(), PixliesAmmo.AmmoType.RIFLE_AMMO, 15, ammo,40, 0.08, 350, Arrays.asList(RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK));
    }

    @Override
    public void reloadItem(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§c§lAK-47 §8| §8[§c" + getAmmo() + "§7/§c" + getMaxAmmo() + "§8]");
        item.setItemMeta(meta);
    }

}