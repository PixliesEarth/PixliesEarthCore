package eu.pixliesearth.core.customitems.ci.weapons.melee;

import eu.pixliesearth.core.customitems.CustomItem;
import eu.pixliesearth.utils.ItemBuilder;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static org.bukkit.Material.WOODEN_SWORD;

public class ItemClayDagger implements CustomItem {

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(WOODEN_SWORD).setDisplayName("§cClay Dagger").setCustomModelData(15).setAttackDamage(3).build();
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
