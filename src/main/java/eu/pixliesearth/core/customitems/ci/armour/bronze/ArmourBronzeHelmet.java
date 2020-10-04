package eu.pixliesearth.core.customitems.ci.armour.bronze;

import eu.pixliesearth.core.customitems.CustomItem;
import eu.pixliesearth.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ArmourBronzeHelmet implements CustomItem {
    @Override
    public ItemStack getItem() {
        return new ItemBuilder(Material.GOLDEN_HELMET)
                .setDisplayName("§6Bronze Helmet")
                .setCustomModelData(17)
                .setArmour(2)
                .setArmourToughness(1) // This makes it better than iron!
                .build();
    }

    @Override
    public String getName() {
        return getItem().getItemMeta().getDisplayName();
    }

    @Override
    public List<String> getLore() {
        List<String> lore = getItem().getItemMeta().getLore();
        return lore;
    }

    @Override
    public ItemStack getStatic(int durability) {
        return getItem();
    }
    
    public ItemStack getStatic() {
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
