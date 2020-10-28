package eu.pixliesearth.core.custom.machines;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import eu.pixliesearth.core.custom.CustomEnergyBlock;
import eu.pixliesearth.core.custom.CustomFeatureHandler;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.listeners.CustomInventoryListener;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.NBTTagType;
import eu.pixliesearth.utils.NBTUtil;
import eu.pixliesearth.utils.NBTUtil.NBTTags;
import eu.pixliesearth.utils.Timer;

@Deprecated
public class MachineNuclearGenerator extends CustomEnergyBlock {
	
	public MachineNuclearGenerator() {
		
	}
	
	public double getCapacity() {
		return 1000000D;
	}
	
	@Override
	public String getPlayerHeadUUID() {
		return "faf0c95ceba34c7fe6d33404feb87b4184ccce143978622c1647feaed2b63274";
	}
	
	@Override
    public String getDefaultDisplayName() {
        return "§6Nuclear Reactor";
	}
    
    @Override
    public String getUUID() {
        return "Machine:Nuclear_Generator2"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
    
    @Override
	public Inventory getInventory() {
		Inventory inv = Bukkit.createInventory(null, 5*9, getInventoryTitle());
		int[] ints = {10,11,12,19,20,21,28,29,30};
		for (int i = 0; i < 5*9; i++)
			inv.setItem(i, CustomItemUtil.getItemStackFromUUID(CustomInventoryListener.getUnclickableItemUUID()));
		for (int i : ints) 
			inv.clear(i);
		return inv;
	}
    
    @Override
	public void onTick(Location loc, Inventory inv, Timer timer) {
		int[] ints = {10,11,12,19,20,21,28,29,30};
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		inv.setItem(43, buildInfoItem(loc));
		inv.setItem(25, buildWaterItem(loc, inv));
		inv.setItem(23, buildTempItem(loc));
		if (timer==null) {
			h.registerTimer(loc, new Timer(250L)); // A quarter of a second per action
			for (int i : ints) {
				ItemStack is = inv.getItem(i);
				if (is==null) continue;
				String id = CustomItemUtil.getUUIDFromItemStack(is);
				if (id==null) continue;
				Double d = h.getTempratureAtLocation(loc);
				if (id.equals("Pixlies:Uranium_Chunk")) {
					if (NBTUtil.getTagsFromItem(is).getString("dmg")!=null) {
						NBTTags tags =  NBTUtil.getTagsFromItem(is);
						tags.addTag("dmg", Integer.parseInt(tags.getString("dmg"))-1, NBTTagType.STRING);
						if (d!=null) {
							if (d<35) {
								h.addTempratureToLocation(loc, 0.05D);
								h.addPowerToLocation(loc, 0.05D);
							} else if (d<100) {
								h.addTempratureToLocation(loc, 0.04D);
								h.addPowerToLocation(loc, 0.5D);
							} else if (d<250) {
								h.addTempratureToLocation(loc, 0.03D);
								h.addPowerToLocation(loc, 0.7D);
							} else if (d<300) {
								h.addTempratureToLocation(loc, 0.03D);
								h.addPowerToLocation(loc, 1.2D);
							} else {
								h.addTempratureToLocation(loc, 0.1D);
								h.addPowerToLocation(loc, 5D);
							}
						}
						if (Integer.parseInt(tags.getString("dmg"))<=0) {
							inv.clear(i);
						} else {
							inv.setItem(i, NBTUtil.addTagsToItem(is, tags));
						}
					} else {
						inv.setItem(i, new ItemBuilder(is).addNBTTag("dmg", "7200"/*30 mins (30x60x4)*/, NBTTagType.STRING).build()); // TODO: make it change texture
					}
				} else if (id.equals("Pixlies:Nuclear_Coolant")) {
					if (NBTUtil.getTagsFromItem(is).getString("dmg")!=null) {
						NBTTags tags =  NBTUtil.getTagsFromItem(is);
						if (d!=null) {
							if (d<35) {
								// Do nothing
							} else if (d<100) {
								h.removeTempratureFromLocation(loc, 0.02D);
								tags.addTag("dmg", Integer.parseInt(tags.getString("dmg"))-1, NBTTagType.STRING);
							} else if (d<250) {
								h.removeTempratureFromLocation(loc, 0.02D);
								tags.addTag("dmg", Integer.parseInt(tags.getString("dmg"))-1, NBTTagType.STRING);
							} else if (d<300) {
								h.removeTempratureFromLocation(loc, 0.02D);
								tags.addTag("dmg", Integer.parseInt(tags.getString("dmg"))-1, NBTTagType.STRING);
							} else {
								h.removeTempratureFromLocation(loc, 35D);
								tags.addTag("dmg", 0, NBTTagType.STRING);
							}
						}
						if (Integer.parseInt(tags.getString("dmg"))<=0) {
							inv.clear(i);
						} else {
							inv.setItem(i, NBTUtil.addTagsToItem(is, tags));
						}
					} else {
						inv.setItem(i, new ItemBuilder(is).addNBTTag("dmg", "2400"/*10 mins (10x60x4)*/, NBTTagType.STRING).build()); // TODO: make it change texture
					}
				} else {
					// Not a usable item
				}
				if (d!=null) {
					for (Block b : getSurroundingBlocks(loc)) {
						if (b!=null) {
							if (b.getType().equals(Material.WATER)) {
								if (d<35) {
									// Do nothing
								} else if (d<100) {
									// Do nothing
								} else if (d<250) {
									if (Math.random()<((d/100D)+0.15D)) {
										Bukkit.getScheduler().scheduleSyncDelayedTask(CustomFeatureLoader.getLoader().getInstance(), new Runnable() { @Override public void run() {b.setType(Material.AIR);}}, 0L);
										h.removeTempratureFromLocation(loc, 0.1D);
									}
								} else if (d<300) {
									Bukkit.getScheduler().scheduleSyncDelayedTask(CustomFeatureLoader.getLoader().getInstance(), new Runnable() { @Override public void run() {b.setType(Material.AIR);}}, 0L);
									h.removeTempratureFromLocation(loc, 0.1D);
								} else {
									Bukkit.getScheduler().scheduleSyncDelayedTask(CustomFeatureLoader.getLoader().getInstance(), new Runnable() { @Override public void run() {b.setType(Material.AIR);}}, 0L);
									h.removeTempratureFromLocation(loc, 0.15D);
								}
							} else if (b.getType().equals(Material.ICE)) {
								if (d<35) {
									// Do nothing
								} else if (d<100) {
									// Do nothing
								} else if (d<250) {
									if (Math.random()<((d/100D)+0.15D)) {
										Bukkit.getScheduler().scheduleSyncDelayedTask(CustomFeatureLoader.getLoader().getInstance(), new Runnable() { @Override public void run() {b.setType(Material.WATER);}}, 0L);
										h.removeTempratureFromLocation(loc, 0.3D);
									}
								} else if (d<300) {
									Bukkit.getScheduler().scheduleSyncDelayedTask(CustomFeatureLoader.getLoader().getInstance(), new Runnable() { @Override public void run() {b.setType(Material.WATER);}}, 0L);
									h.removeTempratureFromLocation(loc, 0.3D);
								} else {
									Bukkit.getScheduler().scheduleSyncDelayedTask(CustomFeatureLoader.getLoader().getInstance(), new Runnable() { @Override public void run() {b.setType(Material.WATER);}}, 0L);
									h.removeTempratureFromLocation(loc, 0.55D);
								}
							} else if (b.getType().equals(Material.FROSTED_ICE)) {
								if (d<35) {
									// Do nothing
								} else if (d<100) {
									// Do nothing
								} else if (d<250) {
									if (Math.random()<((d/100D)+0.15D)) {
										Bukkit.getScheduler().scheduleSyncDelayedTask(CustomFeatureLoader.getLoader().getInstance(), new Runnable() { @Override public void run() {b.setType(Material.ICE);}}, 0L);
										h.removeTempratureFromLocation(loc, 0.5D);
									}
								} else if (d<300) {
									Bukkit.getScheduler().scheduleSyncDelayedTask(CustomFeatureLoader.getLoader().getInstance(), new Runnable() { @Override public void run() {b.setType(Material.ICE);}}, 0L);
									h.removeTempratureFromLocation(loc, 0.5D);
								} else {
									Bukkit.getScheduler().scheduleSyncDelayedTask(CustomFeatureLoader.getLoader().getInstance(), new Runnable() { @Override public void run() {b.setType(Material.ICE);}}, 0L);
									h.removeTempratureFromLocation(loc, 0.75D);
								}
							} else if (b.getType().equals(Material.BLUE_ICE)) {
								if (d<35) {
									// Do nothing
								} else if (d<100) {
									// Do nothing
								} else if (d<250) {
									if (Math.random()<((d/100D)+0.15D)) {
										Bukkit.getScheduler().scheduleSyncDelayedTask(CustomFeatureLoader.getLoader().getInstance(), new Runnable() { @Override public void run() {b.setType(Material.FROSTED_ICE);}}, 0L);
										h.removeTempratureFromLocation(loc, 1.0D);
									}
								} else if (d<300) {
									Bukkit.getScheduler().scheduleSyncDelayedTask(CustomFeatureLoader.getLoader().getInstance(), new Runnable() { @Override public void run() {b.setType(Material.FROSTED_ICE);}}, 0L);
									h.removeTempratureFromLocation(loc, 1.0D);
								} else {
									Bukkit.getScheduler().scheduleSyncDelayedTask(CustomFeatureLoader.getLoader().getInstance(), new Runnable() { @Override public void run() {b.setType(Material.FROSTED_ICE);}}, 0L);
									h.removeTempratureFromLocation(loc, 1.25D);
								}
							}
						}
					}
				}
				if (d!=null) {
					if (d>375) {
						loc.getWorld().createExplosion(loc, 15f);
					} else if (d>350) {
						Bukkit.getServer().getOnlinePlayers().forEach(player -> player.spawnParticle(Particle.CAMPFIRE_SIGNAL_SMOKE, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), 1));
					} else if (d>300) {
						Bukkit.getServer().getOnlinePlayers().forEach(player -> player.spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), 1));
					} else if (d>275) {
						Bukkit.getServer().getOnlinePlayers().forEach(player -> player.spawnParticle(Particle.SMOKE_NORMAL, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), 1));
					}
				}
			}
		} else {
			if (timer.hasExpired()) {
				h.unregisterTimer(loc);
			} else {
				// Do nothing
			}
		}
	}
    
    public ItemStack buildWaterItem(Location location, Inventory inv) {
		int i = 0;
		for (Block b : getSurroundingBlocks(location)) 
			if (b!=null) 
				if (b.getType().equals(Material.WATER) || b.getType().equals(Material.ICE) || b.getType().equals(Material.FROSTED_ICE) || b.getType().equals(Material.BLUE_ICE))
					i++;
		return new ItemBuilder(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setDisplayName("§bCooling Information").addLoreLine("§3Surrounding water sources: "+Integer.toString(i)).addLoreLine("§3Coolants: "+"?"/*TODO: make coolant*/).addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING).build();
    }
    
    public ItemStack buildTempItem(Location location) {
		Double temp = CustomFeatureLoader.getLoader().getHandler().getTempratureAtLocation(location);
		if (temp==null) CustomFeatureLoader.getLoader().getHandler().addTempratureToLocation(location, 0D); // Give temp if null
		return new ItemBuilder((temp==null) ? Material.GRAY_STAINED_GLASS_PANE : (temp<35) ? Material.PINK_STAINED_GLASS_PANE : (temp<100) ? Material.PURPLE_STAINED_GLASS_PANE : (temp<250) ? Material.GREEN_STAINED_GLASS_PANE : (temp<300) ? Material.YELLOW_STAINED_GLASS_PANE : Material.RED_STAINED_GLASS_PANE).setDisplayName((temp==null) ? "§cError!" : "§cTemprature").addLoreLine((temp==null) ? "§cRecieved a null!" : "§c"+temp+"°c").build();
	}
}
