package eu.pixliesearth.core.custom.items;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
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
import org.bukkit.inventory.ItemStack;

import eu.pixliesearth.core.custom.CustomBlock;
import eu.pixliesearth.core.custom.CustomFeatureHandler;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.CustomItem;
import eu.pixliesearth.core.custom.MinecraftMaterial;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.NBTUtil;
import eu.pixliesearth.utils.NBTUtil.NBTTags;

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
    	
    	boolean enabled = false;
    	
    	if (event.getPlayer().getInventory().getItemInMainHand()==null) return false;
		if (event.getClickedBlock() == null || event.getClickedBlock().getType().equals(MinecraftMaterial.AIR.getMaterial())) return false;
		if (!isAMissile(event.getClickedBlock())) {
			CustomBlock customBlock = CustomFeatureLoader.getLoader().getHandler().getCustomBlockFromLocation(event.getClickedBlock().getLocation());
			if (customBlock==null || !customBlock.getUUID().equals("Pixlies:Missile_Warhead_Block_UNB"))
				return false;
			event.getPlayer().sendMessage("This missile is alread activated!");
			return false;
		}
		Player p = event.getPlayer();
		Inventory inv = getWarHeadInventory(event.getClickedBlock());
		int range = 0;
		int playerdamage = 0;
		int explosive = 0;
		int launchtime = 30;
		Location location = null;
		if (inv==null) return false;
		for (int i = 0; i < 9; i++) {
			ItemStack itemStack = inv.getItem(i);
			if (itemStack==null || itemStack.getType().equals(MinecraftMaterial.AIR.getMaterial())) continue;
			String id = CustomItemUtil.getUUIDFromItemStack(itemStack);
			if (id.equals("Pixlies:ICBM_Location_Holder")) {
				NBTTags tags = NBTUtil.getTagsFromItem(itemStack);
				location = new Location(event.getPlayer().getWorld(), Integer.parseInt(tags.getString("x")), Integer.parseInt(tags.getString("y")), Integer.parseInt(tags.getString("z")));
			} else if (id.equals("Pixlies:Fuel_Rod")) {
				range += 5;
				explosive += 1;
				launchtime -= 3;
			} else if (id.equals(MinecraftMaterial.TNT.getUUID())) {
				explosive += 7;
				playerdamage += 3;
				launchtime += 7;
			} else if (id.equals("Pixlies:Nuclear_Bomb") || id.equals("Pixlies:Nuclear_Bomb_Autodetinating")) {
				explosive += 27;
				playerdamage += 100;
				launchtime += 300;
			} else if (id.equals(MinecraftMaterial.FIRE_CHARGE.getUUID())) {
				range += 1;
				playerdamage += 1;
				launchtime -= 1;
			} else if (id.equals("Pixlies:Energy_Core_Unprotected")) {
				range += 15;
				explosive += 72;
				playerdamage += 7;
				launchtime -= 5;
			} else if (id.equals("Pixlies:Energy_Core_Protected")) {
				range += 35;
				explosive += 5;
				playerdamage += 2;
				launchtime -= 7;
			} else if (id.equals("Pixlies:Battery")) {
				explosive += 6;
				launchtime += 1;
			}
			if (launchtime<5) {
				launchtime = 5;
			}
		}
		if (p.isSneaking()) {
			p.sendMessage("§aMissile Statistics");
			if (location==null) {
				event.getPlayer().sendMessage("§aLocation: §rNoLocationSaverFound");
				event.getPlayer().sendMessage("§aDistanceFromTarget: §rNoLocationSaverFound");
				event.getPlayer().sendMessage("§aHasRangeToLaunch: §rNoLocationSaverFound");
			} else {
				event.getPlayer().sendMessage("§aLocation: §r"+location.getBlockX()+"§a,§r"+location.getBlockY()+"§a,§r"+location.getBlockZ());
				event.getPlayer().sendMessage("§aDistanceFromTarget: §r"+event.getClickedBlock().getLocation().clone().distance(location));
				event.getPlayer().sendMessage("§aHasRangeToLaunch: §r"+(event.getClickedBlock().getLocation().clone().distance(location) < (range*100)));
			}
			event.getPlayer().sendMessage("§aRange: §r"+range+" hundered blocks");
			event.getPlayer().sendMessage("§aExplosive: §r"+explosive);
			event.getPlayer().sendMessage("§aDamage: §r"+playerdamage);
			event.getPlayer().sendMessage("§aLaunchTime: §r"+launchtime);
			return false;
		} else {
			if (!enabled) {
				p.sendMessage("§cLaunching is currently disabled!");
				return false;
			} else {
				if (launchtime<5) 
					launchtime = 5;
				if (range<=0) {
					p.sendMessage("§cYou do not have any fuel!");
					return false;
				}
				if (event.getClickedBlock().getLocation().clone().distance(location) < (range*100)) {
					p.sendMessage("§cYou do not have the fuel for this distance!");
					return false;
				}
				makeMissileUnbreakable(event.getClickedBlock());
				p.sendMessage("§aLaunching in "+launchtime+" seconds!");
				Bukkit.getScheduler().scheduleSyncDelayedTask(CustomFeatureLoader.getLoader().getInstance(), new Runnable() {
					@Override 
					public void run() {
						p.sendMessage("§aLaunching!");
					}
				}, (20*launchtime));
				return false;
			}
		}
    }
    
    public void makeMissileUnbreakable(Block b) {
    	if (!isAMissile(b)) return;
    	Location bl = b.getLocation().clone();
		World w = b.getLocation().getWorld();
		Block b2 = w.getBlockAt(bl.getBlockX(), bl.getBlockY()-1, bl.getBlockZ());
		Block b3 = w.getBlockAt(bl.getBlockX(), bl.getBlockY()-2, bl.getBlockZ());
		Block b4 = w.getBlockAt(bl.getBlockX(), bl.getBlockY()-2, bl.getBlockZ()-1);
		Block b5 = w.getBlockAt(bl.getBlockX(), bl.getBlockY()-2, bl.getBlockZ()+1);
		Block b6 = w.getBlockAt(bl.getBlockX()-1, bl.getBlockY()-2, bl.getBlockZ());
		Block b7 = w.getBlockAt(bl.getBlockX()+1, bl.getBlockY()-2, bl.getBlockZ());
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		h.removeCustomBlockFromLocation(b2.getLocation());
		h.removeCustomBlockFromLocation(b3.getLocation());
		h.removeCustomBlockFromLocation(b4.getLocation());
		h.removeCustomBlockFromLocation(b5.getLocation());
		h.removeCustomBlockFromLocation(b6.getLocation());
		h.removeCustomBlockFromLocation(b7.getLocation());
		h.setCustomBlockToLocation(b2.getLocation(), "Pixlies:Missile_Block_UNB");
		h.setCustomBlockToLocation(b3.getLocation(), "Pixlies:Missile_Block_UNB");
		h.setCustomBlockToLocation(b4.getLocation(), "Pixlies:Missile_Fin_UNB");
		h.setCustomBlockToLocation(b5.getLocation(), "Pixlies:Missile_Fin_UNB");
		h.setCustomBlockToLocation(b6.getLocation(), "Pixlies:Missile_Fin_UNB");
		h.setCustomBlockToLocation(b7.getLocation(), "Pixlies:Missile_Fin_UNB");
		Inventory inv = getWarHeadInventory(b);
		for (int i = 0; i < 9; i++) {
			ItemStack itemStack = inv.getItem(i);
			if (itemStack==null || itemStack.getType().equals(MinecraftMaterial.AIR.getMaterial())) continue;
			for (int i2 = 0; i2 < itemStack.getAmount()-1; i2++) {
				bl.getWorld().dropItemNaturally(bl, itemStack.asOne());
			}
		}
		h.removeCustomBlockFromLocation(bl);
		h.setCustomBlockToLocation(bl, "Pixlies:Missile_Warhead_Block_UNB");
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