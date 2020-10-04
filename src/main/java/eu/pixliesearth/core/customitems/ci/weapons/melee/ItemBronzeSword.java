package eu.pixliesearth.core.customitems.ci.weapons.melee;

import eu.pixliesearth.core.customitems.CustomItem;
import eu.pixliesearth.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ItemBronzeSword implements CustomItem {

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(Material.GOLDEN_SWORD)
                .setDisplayName("§7§lBronze Sword")
                .setDamage(0)
                .setCustomModelData(11)
                .setAttackDamage(6.5)
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
