package eu.pixliesearth.core.custom.items;

import eu.pixliesearth.core.custom.*;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.NBTUtil;
import eu.pixliesearth.utils.NBTUtil.NBTTags;
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

import java.util.*;

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
    public Rarity getRarity() {
    	return Rarity.RARE;
    }

    @Override
    public boolean PlayerInteractEvent(PlayerInteractEvent event) {
    	
    	boolean enabled = false;
    	
    	if (event.getPlayer().getInventory().getItemInMainHand()==null) return false;
		if (event.getClickedBlock() == null || event.getClickedBlock().getType().equals(MinecraftMaterial.AIR.getMaterial())) return false;
		if (!isAMissile(event.getClickedBlock())) {
			CustomBlock customBlock = CustomFeatureLoader.getLoader().getHandler().getCustomBlockFromLocation(event.getClickedBlock().getLocation());
			if (customBlock==null)
				return false;
			if (customBlock.getUUID().equals("Pixlies:Missile_Warhead_Block_UNB")) {
				event.getPlayer().sendMessage("This missile is alread activated!");
			} else if (customBlock.getUUID().equals("Pixlies:Missile_Warhead_Block_UNB_2")) {
				event.getPlayer().sendMessage("This missile has launched!");
			}
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
			} else if (id.equals("Pixlies:ICBM_Card")) {
				NBTTags tags = NBTUtil.getTagsFromItem(itemStack);
				range += Integer.parseInt(tags.getString("r"));
				explosive += Integer.parseInt(tags.getString("e"));
				launchtime += Integer.parseInt(tags.getString("l"));
				playerdamage += Integer.parseInt(tags.getString("d"));
			}
			if (launchtime<5) {
				launchtime = 5;
			}
		}
		if (p.isSneaking()) {
			p.sendMessage("§6§lMissile Statistics");
			if (location==null) {
				event.getPlayer().sendMessage("§7» §eLocation: §cNoLocationSaverFound");
				event.getPlayer().sendMessage("§7» §eDistanceFromTarget: §cNoLocationSaverFound");
				event.getPlayer().sendMessage("§7» §eHasRangeToLaunch: §cNoLocationSaverFound");
			} else {
				event.getPlayer().sendMessage("§7» §eLocation: §b"+location.getBlockX()+"§e, §b"+location.getBlockY()+"§e, §b"+location.getBlockZ());
				event.getPlayer().sendMessage("§7» §eDistanceFromTarget: §b"+event.getClickedBlock().getLocation().clone().distance(location));
				event.getPlayer().sendMessage("§7» §eHasRangeToLaunch: §b"+(event.getClickedBlock().getLocation().clone().distance(location) < (range*100)));
			}
			event.getPlayer().sendMessage("§7» §eRange: §b"+range+" hundered blocks");
			event.getPlayer().sendMessage("§7» §eExplosive: §b"+explosive);
			event.getPlayer().sendMessage("§7» §eDamage: §b"+playerdamage);
			event.getPlayer().sendMessage("§7» §eLaunchTime: §b"+launchtime);
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
				p.sendMessage("§7Launching in §c"+launchtime+" §7seconds!");
				Integer i = Bukkit.getScheduler().scheduleSyncDelayedTask(CustomFeatureLoader.getLoader().getInstance(), new Runnable() {
					@Override 
					public void run() {
						p.sendMessage("§c§lLaunching!");
						CustomFeatureLoader.getLoader().getHandler().unregisterLocationEvent(event.getClickedBlock().getLocation());
						launchMissile(event.getClickedBlock());
					}
				}, (20*launchtime));
				CustomFeatureLoader.getLoader().getHandler().registerLocationEvent(location, i);
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
    
    @SuppressWarnings("deprecation")
    public void launchMissile(Block b) {
    	CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
    	if (h.getCustomBlockFromLocation(b.getLocation())!=null) {
			if (h.getCustomBlockFromLocation(b.getLocation()).getUUID().equals("Pixlies:Missile_Warhead_Block_UNB")) { //Warhead UUID
				h.removeCustomBlockFromLocation(b.getLocation());
				h.setCustomBlockToLocation(b.getLocation(), "Pixlies:Missile_Warhead_Block_UNB_2");
			}
    	}
    	Location bl = b.getLocation();
    	
    	final UUID id = UUID.randomUUID();
    	addBS(id, b);
    	
    	Integer i = Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(CustomFeatureLoader.getLoader().getInstance(), new Runnable() {
			
			public void run() {
				List<Block> blocks = getMissileBlocks(getBS(id));
				for (Block block : blocks) {
					CustomBlock cb = h.getCustomBlockFromLocation(block.getLocation());
					h.removeCustomBlockFromLocation(block.getLocation());
					Location loc = block.getLocation().clone();
					loc.setY(loc.getY()+1);
					h.setCustomBlockToLocation(loc, cb.getUUID());
					if (cb.getUUID().equals("Pixlies:Missile_Warhead_Block_UNB_2")) {
						remBS(id);
						addBS(id, block);
					}
				}
			}
		}, 5L, 5L);
    	CustomFeatureLoader.getLoader().getHandler().registerLocationEvent(bl, i);
    }
    
    public void onHit(Location location) {
    	CustomFeatureLoader.getLoader().getHandler().unregisterLocationEvent(location);
    }
    
    public List<Block> getMissileBlocks(Block b) {
    	List<Block> blocks = new ArrayList<Block>();
    	blocks.add(b);
    	blocks.add(b.getWorld().getBlockAt(b.getLocation().getBlockX(), b.getLocation().getBlockY()-1, b.getLocation().getBlockZ()));
    	blocks.add(b.getWorld().getBlockAt(b.getLocation().getBlockX(), b.getLocation().getBlockY()-2, b.getLocation().getBlockZ()));
    	blocks.add(b.getWorld().getBlockAt(b.getLocation().getBlockX(), b.getLocation().getBlockY()-2, b.getLocation().getBlockZ()-1));
    	blocks.add(b.getWorld().getBlockAt(b.getLocation().getBlockX(), b.getLocation().getBlockY()-2, b.getLocation().getBlockZ()+1));
    	blocks.add(b.getWorld().getBlockAt(b.getLocation().getBlockX()-1, b.getLocation().getBlockY()-2, b.getLocation().getBlockZ()));
    	blocks.add(b.getWorld().getBlockAt(b.getLocation().getBlockX()+1, b.getLocation().getBlockY()-2, b.getLocation().getBlockZ()));
    	return blocks;
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
	
	public static HashMap<UUID, Block> bsm = new HashMap<UUID, Block>();
	
	public static Block getBS(UUID id) {
		return bsm.get(id);
	}
	
	public static void addBS(UUID id, Block b) {
		bsm.put(id, b);
	}
	
	public static void remBS(UUID id) {
		bsm.remove(id);
	}
}