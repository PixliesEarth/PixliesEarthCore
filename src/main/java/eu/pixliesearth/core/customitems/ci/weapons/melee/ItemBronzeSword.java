package eu.pixliesearth.core.customitems.ci.weapons.melee;

import eu.pixliesearth.core.customitems.DamagerCI;
import eu.pixliesearth.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import java.util.List;

public class ItemBronzeSword implements DamagerCI {

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(Material.GOLDEN_SWORD)
                .setDisplayName("§7§lBronze Sword")
                .setDamage(0)
                .setCustomModelData(11)
                .addLoreLine("§2Damage: " + damage())
                .build();
    }

    @Override
    public String getName() {
        return getItem().getItemMeta().getDisplayName();
    }

    @Override
    public List<String> getLore() {
        List<String> returner = new ArrayList<>();
        returner.add("§2Damage: " + damage());
        return returner;
    }

    @Override
    public ItemStack getStatic(int durability) {
        return new ItemBuilder(Material.GOLDEN_SWORD)
                .setDisplayName("§7§lBronze Sword")
                .setDamage(0)
                .setCustomModelData(11)
                .addLoreLine("§2Damage: " + damage())
                .build();
    }

    @Override
    public boolean enchantable() {
        return true;
    }

    @Override
    public void onInteract(PlayerInteractEvent event) {

    }

    @Override
    public double damage() {
        return 6.5;
    }
}
