package eu.pixliesearth.core.custom.items;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;

import eu.pixliesearth.core.custom.CustomBlock;
import eu.pixliesearth.core.custom.CustomFeatureHandler;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.CustomItem;
import eu.pixliesearth.core.custom.MinecraftMaterial;
import eu.pixliesearth.utils.CustomItemUtil;

public class ItemICBM extends CustomItem {
	
    public ItemICBM() {

    }

    @Override
    public Material getMaterial() {
        return Material.STICK;
    }

    @Override
    public List<String> getDefaultLore() {
        return null;
    }

    @Override
    public String getDefaultDisplayName() {
        return "§cICBM Launcher";
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
        return null;
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return CreativeTabs.TOOLS;
    }

    @Override
    public String getUUID() {
        return "Pixlies:ICBM_Key"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }

    @Override
    public boolean PlayerInteractEvent(PlayerInteractEvent event) {
    	if (event.getPlayer().getInventory().getItemInMainHand()==null) return false;
		if (event.getClickedBlock() == null || event.getClickedBlock().getType().equals(MinecraftMaterial.AIR.getMaterial())) return false;
		if (!isHoldingAMissileKey(event.getPlayer())) return false;
		if (!isAMissile(event.getClickedBlock())) return false;
		Player p = event.getPlayer();
		Inventory i = getWarHeadInventory(event.getClickedBlock());
		if (i==null) return false;
		if (p.isSneaking()) {
			p.sendMessage("MISSILE FOUND");
			
		} else {
			p.sendMessage("MISSILE FOUND");
		}
        return false;
    }
    
    public boolean isAMissile(Block b) {
		Location bl = b.getLocation().clone();
		World w = b.getLocation().getWorld();
		Block b2 = w.getBlockAt(bl.getBlockX(), bl.getBlockY()-1, bl.getBlockZ());
		Block b3 = w.getBlockAt(bl.getBlockX(), bl.getBlockY()-2, bl.getBlockZ());
		Block b4 = w.getBlockAt(bl.getBlockX(), bl.getBlockY()-2, bl.getBlockZ()-1);
		Block b5 = w.getBlockAt(bl.getBlockX(), bl.getBlockY()-2, bl.getBlockZ()+1);
		Block b6 = w.getBlockAt(bl.getBlockX()-1, bl.getBlockY()-2, bl.getBlockZ());
		Block b7 = w.getBlockAt(bl.getBlockX()+1, bl.getBlockY()-2, bl.getBlockZ());
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		int val = 0;
		if (h.getCustomBlockFromLocation(b.getLocation())!=null) 
			if (h.getCustomBlockFromLocation(b.getLocation()).getUUID().equals("Pixlies:Missile_Warhead_Block")) //Warhead UUID
				val += 1;
		if (h.getCustomBlockFromLocation(b2.getLocation())!=null) 
			if (h.getCustomBlockFromLocation(b2.getLocation()).getUUID().equals("Pixlies:Missile_Block"))
				val += 1;
		if (h.getCustomBlockFromLocation(b3.getLocation())!=null) 
			if (h.getCustomBlockFromLocation(b3.getLocation()).getUUID().equals("Pixlies:Missile_Block"))
				val += 1;
		if (h.getCustomBlockFromLocation(b4.getLocation())!=null) 
			if (h.getCustomBlockFromLocation(b4.getLocation()).getUUID().equals("Pixlies:Missile_Fin"))
				val += 1;
		if (h.getCustomBlockFromLocation(b5.getLocation())!=null) 
			if (h.getCustomBlockFromLocation(b5.getLocation()).getUUID().equals("Pixlies:Missile_Fin"))
				val += 1;
		if (h.getCustomBlockFromLocation(b6.getLocation())!=null) 
			if (h.getCustomBlockFromLocation(b6.getLocation()).getUUID().equals("Pixlies:Missile_Fin"))
				val += 1;
		if (h.getCustomBlockFromLocation(b7.getLocation())!=null) 
			if (h.getCustomBlockFromLocation(b7.getLocation()).getUUID().equals("Pixlies:Missile_Fin"))
				val += 1;
		return val>=7;
	}
	
	public boolean isHoldingAMissileKey(Player player) {
		return CustomItemUtil.getUUIDFromItemStack(player.getInventory().getItemInMainHand()).equals("Pixlies:ICBM_Key");
	}
	
	public Inventory getWarHeadInventory(Block warhead) {
		CustomBlock cb = CustomFeatureLoader.getLoader().getHandler().getCustomBlockFromLocation(warhead.getLocation());
		if (cb==null) return null;
		if (!cb.getUUID().equalsIgnoreCase("Pixlies:Missile_Warhead_Block")) return null;
		Dispenser d = (Dispenser) warhead.getState();
		if (d==null) return null;
		return d.getInventory();
	}
}