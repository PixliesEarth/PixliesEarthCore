package eu.pixliesearth.core.custom.items;

import eu.pixliesearth.core.custom.CustomItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;

import java.util.*;

public class GunRifleStock extends CustomItem {
	
    public GunRifleStock() {

    }

    @Override
    public Material getMaterial() {
        return Material.BRICK;
    }

    @Override
    public List<String> getDefaultLore() {
        return new ArrayList<String>() {/** */private static final long serialVersionUID = 7541112248281122525L;{
            add("§c§lStock for Assault Rifles & SMGs!");
        }};
    }

    @Override
    public String getDefaultDisplayName() {
        return "§6Rifle Stock";
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
        return new HashMap<Enchantment, Integer>();
    }

    @Override
    public Set<ItemFlag> getItemFlags(){
        return new HashSet<ItemFlag>();
    }

    @Override
    public Integer getCustomModelData() {
        return 10;
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return CreativeTabs.REDSTONE;
    }

    @Override
    public String getUUID() {
        return "Pixlies:Rifle_Stock"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }

    @Override
    public boolean PlayerInteractEvent(PlayerInteractEvent event) {
        return false;
    }
}