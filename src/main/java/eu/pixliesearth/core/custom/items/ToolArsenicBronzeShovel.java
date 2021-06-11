package eu.pixliesearth.core.custom.items;

import eu.pixliesearth.core.custom.CustomItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;

import java.util.*;

public class ToolArsenicBronzeShovel extends CustomItem {

    public ToolArsenicBronzeShovel() {

    }

    @Override
    public Material getMaterial() {
        return Material.IRON_SHOVEL;
    }

    @Override
    public List<String> getDefaultLore() {
        return null;
    }

    @Override
    public String getDefaultDisplayName() {
        return "§7§lArsenic Bronze Shovel";
    }

    @Override
    public boolean isGlowing() {
        return false;
    }

    @Override
    public boolean isUnbreakable() {
        return false;
    }

    @Override
    public Map<String, Object> getDefaultNBT() {
        return new HashMap<String, Object>();
    }

    @Override
    public Map<Enchantment, Integer> getDefaultEnchants() {
        return new HashMap<Enchantment, Integer>(){private static final long serialVersionUID = -3854903160949154792L;{
            put(Enchantment.DIG_SPEED, 2);
        }};
    }

    @Override
    public Set<ItemFlag> getItemFlags(){
        return new HashSet<ItemFlag>();
    }

    @Override
    public Integer getCustomModelData() {
        return 65;
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return CreativeTabs.TOOLS;
    }

    @Override
    public String getUUID() {
        return "Pixlies:Arsenic_Bronze_Shovel"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }

    @Override
    public boolean PlayerInteractEvent(PlayerInteractEvent event) {
        return false;
    }

}
