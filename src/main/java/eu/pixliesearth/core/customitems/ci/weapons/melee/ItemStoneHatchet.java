package eu.pixliesearth.core.customitems.ci.weapons.melee;

import eu.pixliesearth.core.customitems.CustomItem;
import eu.pixliesearth.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ItemStoneHatchet implements CustomItem {

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(Material.STONE_AXE)
                .setDisplayName("§7§lStone Hatchet")
                .setDurability((short) 100)
                .setCustomModelData(5)
                .setAttackDamage(2.0)
                .build();
    }

    @Override
    public String getName() {
        return getItem().getItemMeta().getDisplayName();
    }

    @Override
    public List<String> getLore() {
        return getItem().getLore();
    }

    @Override
    public ItemStack getStatic(int durability) {
        return getItem();
    }

    @Override
    public boolean enchantable() {
        return true;
    }

    @Override
    public void onInteract(PlayerInteractEvent event) {

    }


}
