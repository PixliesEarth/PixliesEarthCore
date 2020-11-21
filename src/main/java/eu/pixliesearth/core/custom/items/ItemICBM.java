package eu.pixliesearth.core.custom.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

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
    
    public boolean enabled = true;
    public boolean Dynmap = false;

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
				if (!(event.getClickedBlock().getLocation().clone().distance(location) < (range*100))) {
					p.sendMessage("§cYou do not have the fuel for this distance!");
					return false;
				}
				makeMissileUnbreakable(event.getClickedBlock());
				p.sendMessage("§7Launching in §c"+launchtime+" §7seconds!");
				final Location location2 = location.clone();
				final int e = explosive;
				final int pd = playerdamage;
				Integer i = Bukkit.getScheduler().scheduleSyncDelayedTask(CustomFeatureLoader.getLoader().getInstance(), new Runnable() {
					@Override 
					public void run() {
						p.sendMessage("§c§lLaunching!");
						CustomFeatureLoader.getLoader().getHandler().unregisterLocationEvent(event.getClickedBlock().getLocation());
						launchMissile(event.getClickedBlock().getLocation(), location2, e, pd, p);
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
		h.setCustomBlockToLocation(b4.getLocation(), "Pixlies:Missile_Fin_UNB");
		h.setCustomBlockToLocation(b5.getLocation(), "Pixlies:Missile_Fin_UNB");
		h.setCustomBlockToLocation(b6.getLocation(), "Pixlies:Missile_Fin_UNB");
		h.setCustomBlockToLocation(b7.getLocation(), "Pixlies:Missile_Fin_UNB");
		h.setCustomBlockToLocation(b2.getLocation(), "Pixlies:Missile_Block_UNB");
		h.setCustomBlockToLocation(b3.getLocation(), "Pixlies:Missile_Block_UNB");
		Inventory inv = getWarHeadInventory(b);
		inv.clear();
		/*for (int i = 0; i < 9; i++) {
			ItemStack itemStack = inv.getItem(i);
			if (itemStack==null || itemStack.getType().equals(MinecraftMaterial.AIR.getMaterial())) continue;
			for (int i2 = 0; i2 < itemStack.getAmount()-1; i2++) {
				bl.getWorld().dropItemNaturally(bl, itemStack.asOne());
			}
		}*/
		h.removeCustomBlockFromLocation(bl);
		h.setCustomBlockToLocation(bl, "Pixlies:Missile_Warhead_Block_UNB");
    }
    
    @SuppressWarnings("deprecation")
    public void launchMissile(Location start, Location end, int ex, int pd, Player p) {
    	CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
    	if (h.getCustomBlockFromLocation(start)!=null) {
			if (h.getCustomBlockFromLocation(start).getUUID().equals("Pixlies:Missile_Warhead_Block_UNB")) { //Warhead UUID
				h.removeCustomBlockFromLocation(start);
				h.setCustomBlockToLocation(start, "Pixlies:Missile_Warhead_Block_UNB_2");
			}
    	}
    	final UUID id = UUID.randomUUID();
    	addBS(id, start);
    	
    	Integer i = Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(CustomFeatureLoader.getLoader().getInstance(), new Runnable() {
			public void run() {
				Bukkit.getScheduler().scheduleSyncDelayedTask(CustomFeatureLoader.getLoader().getInstance(), new Runnable() {
					@Override
					public void run() {
						Location l = getBS(id).clone();
						remMissile(l);
						remBS(id);
						l.setY(l.getY()+1D);
						setMissile(l);
						if (l.getY()-3>=256) {
							p.sendMessage("left");
							Bukkit.getScheduler().cancelTask(CustomFeatureLoader.getLoader().getHandler().getLocationEvent(start));
							CustomFeatureLoader.getLoader().getHandler().unregisterLocationEvent(start);
							p.sendMessage("left2");
							Bukkit.getScheduler().scheduleSyncDelayedTask(CustomFeatureLoader.getLoader().getInstance(), new Runnable() {
								@Override
								public void run() {
									p.sendMessage("timer done");
									final UUID id2 = UUID.randomUUID();
									addBS(id2, new Location(end.getWorld(), end.getX(), 60, end.getZ()));
									Integer i2 = Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(CustomFeatureLoader.getLoader().getInstance(), new Runnable() {
										@Override
										public void run() {
											Bukkit.getScheduler().scheduleSyncDelayedTask(CustomFeatureLoader.getLoader().getInstance(), new Runnable() {
												@Override
												public void run() {
													Location l3 = getBS(id2).clone();
													remBS(id2);
													Location l4 = new Location(l3.getWorld(), l3.getX(), l3.getY()-1, l3.getZ());
													p.sendMessage("Dropping down at "+l3.getX()+" "+(l3.getY()-1)+" "+l3.getZ());
													remMissile2(l3.clone());
													if (l4.getBlock()!=null && !l4.getBlock().getType().equals(Material.AIR) && !l4.getBlock().getType().equals(Material.WATER) && !l4.getBlock().getType().equals(Material.LAVA)) {
														p.sendMessage("boom");
														l4.createExplosion((float)ex, true);
														Bukkit.getScheduler().cancelTask(CustomFeatureLoader.getLoader().getHandler().getLocationEvent(start));
														CustomFeatureLoader.getLoader().getHandler().unregisterLocationEvent(start);
													} else {
														setMissile2(l4.clone());
														addBS(id2, l4);
													}
												}
											}, 1l);
										}
									}, 5L, 5L);
									CustomFeatureLoader.getLoader().getHandler().registerLocationEvent(start, i2);
								}
							}, ((long)(start.distance(end)*2l))); // 1 second per 10 blocks
						} else {
							addBS(id, l);
						}
					}
				}, 1l);
			}
		}, 5L, 5L);
    	CustomFeatureLoader.getLoader().getHandler().registerLocationEvent(start, i);
    	/**CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
    	boolean isInDynmapEnabledWorld = (!Dynmap) ? false : true; // TODO: dynmap
    	if (h.getCustomBlockFromLocation(start)!=null) {
			if (h.getCustomBlockFromLocation(start).getUUID().equals("Pixlies:Missile_Warhead_Block_UNB")) { //Warhead UUID
				h.removeCustomBlockFromLocation(start);
				h.setCustomBlockToLocation(start, "Pixlies:Missile_Warhead_Block_UNB_2");
			}
    	}
    	final UUID id = UUID.randomUUID();
    	addBS(id, start);
    	final Marker marker = (isInDynmapEnabledWorld) ? DynmapUtil.getDynmapMissile().addMissileAt(start, ex, pd, end) : null;
    	Integer i = Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(CustomFeatureLoader.getLoader().getInstance(), new Runnable() {
			public void run() {
				Bukkit.getScheduler().scheduleSyncDelayedTask(CustomFeatureLoader.getLoader().getInstance(), new Runnable() {
					@Override
					public void run() {
						Location l = getBS(id).clone();
						remMissile(l);
						remBS(id);
						l.setY(l.getY()+1D);
						setMissile(l);
						if (l.getY()-3>=256) {
							Bukkit.getScheduler().cancelTask(CustomFeatureLoader.getLoader().getHandler().getLocationEvent(start));
							CustomFeatureLoader.getLoader().getHandler().unregisterLocationEvent(start);
							if (isInDynmapEnabledWorld) {
								Bukkit.getScheduler().scheduleAsyncDelayedTask(h.getInstance(), new Runnable() {
									@Override
									public void run() {
										Location loc = start.clone();
										Location loc2 = end.clone();
										Vector v = loc.toVector().subtract(loc2.toVector()).divide(new Vector(10, 1, 10));
										Vector vn = v.normalize();
										boolean b = true;
										int i2 = 0;
										long l = (long) ((start.distance(end)*2l)/10);
										while (b) {
											i2++;
											if (i2 >= loc.distanceSquared(loc2)) {
												marker.deleteMarker();
												b=false;
											} else {
												loc.add(vn);
												marker.setLocation(marker.getWorld(), loc.getX(), loc.getY(), loc.getZ());
												try {
													Thread.sleep(l);
												} catch (InterruptedException ingore) {}
											}
										}
										/*for (int i = 0; i <= loc.distance(loc2); i += 0.5) {
											if (i == loc.distance(loc2)) {
												marker.deleteMarker();
											} else {
												loc.add(vn);
												marker.setLocation(marker.getWorld(), loc.getX(), loc.getY(), loc.getZ());
												try {
													Thread.sleep(250l);
												} catch (InterruptedException ingore) {}
											}
										}
									}
								}, 1l);
							}
							Bukkit.getScheduler().scheduleSyncDelayedTask(CustomFeatureLoader.getLoader().getInstance(), new Runnable() {
								@Override
								public void run() {
									p.sendMessage("Dropping towards the designated target!");
									final UUID id2 = UUID.randomUUID();
									addBS(id2, new Location(end.getWorld(), end.getX(), 256, end.getZ()));
									final Marker marker2 = (isInDynmapEnabledWorld) ? DynmapUtil.getDynmapMissile().addMissileAt(end, ex, pd, end) : null;
									Integer i2 = Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(CustomFeatureLoader.getLoader().getInstance(), new Runnable() {
										@Override
										public void run() {
											Bukkit.getScheduler().scheduleSyncDelayedTask(CustomFeatureLoader.getLoader().getInstance(), new Runnable() {
												@Override
												public void run() {
													Location l3 = (getBS(id2)==null) ? new Location(end.getWorld(), end.getX(), 256, end.getZ()) : getBS(id2).clone();
													remBS(id2);
													Location l4 = new Location(l3.getWorld(), l3.getX(), l3.getY()-1, l3.getZ());
													remMissile2(l3.clone());
													Bukkit.getScheduler().scheduleSyncDelayedTask(CustomFeatureLoader.getLoader().getInstance(), new Runnable() {
														@Override
														public void run() {
															if (l4.getBlock()!=null && !l4.getBlock().getType().equals(Material.AIR) && !l4.getBlock().getType().equals(Material.WATER) && !l4.getBlock().getType().equals(Material.LAVA)) {
																// l4.createExplosion((float)ex, true);
																p.sendMessage("Landed at designated target!");
																if (p.getGameMode().equals(GameMode.CREATIVE)) {
																	p.sendMessage("[Debug] Landed at "+l4.getBlockX()+", "+l4.getBlockY()+", "+l4.getBlockZ());
																}
																ExplosionCalculator calc = new ExplosionCalculator(l4, ex, false);
																calc.explode(true);
																if (p.getGameMode().equals(GameMode.CREATIVE)) {
																	p.sendMessage("[Debug] Exploded "+calc.getExplodeLocations().size()+" blocks");
																}
																Bukkit.getScheduler().cancelTask(CustomFeatureLoader.getLoader().getHandler().getLocationEvent(start));
																CustomFeatureLoader.getLoader().getHandler().unregisterLocationEvent(start);
																if (isInDynmapEnabledWorld) {
																		marker.deleteMarker();
																		marker2.deleteMarker();
																}
															} else {
																setMissile2(l4.clone());
																addBS(id2, l4.clone());
															}
														}
													}, 4l);
													/*if (l4.getBlock()!=null && !l4.getBlock().getType().equals(Material.AIR) && !l4.getBlock().getType().equals(Material.WATER) && !l4.getBlock().getType().equals(Material.LAVA)) {
														// l4.createExplosion((float)ex, true);
														p.sendMessage("Landed at designated target!");
														if (p.getGameMode().equals(GameMode.CREATIVE)) {
															p.sendMessage("[Debug] Landed at "+l4.getBlockX()+", "+l4.getBlockY()+", "+l4.getBlockZ());
														}
														ExplosionCalculator calc = new ExplosionCalculator(l4, ex, false);
														calc.explode(true);
														if (p.getGameMode().equals(GameMode.CREATIVE)) {
															p.sendMessage("[Debug] Exploded "+calc.getExplodeLocations().size()+" blocks");
														}
														Bukkit.getScheduler().cancelTask(CustomFeatureLoader.getLoader().getHandler().getLocationEvent(start));
														CustomFeatureLoader.getLoader().getHandler().unregisterLocationEvent(start);
														if (isInDynmapEnabledWorld) {
																marker.deleteMarker();
																marker2.deleteMarker();
														}
													} else {
														setMissile2(l4.clone());
														addBS(id2, l4.clone());
													}
												}
											}, 1l);
										}
									}, 5L, 5L);
									CustomFeatureLoader.getLoader().getHandler().registerLocationEvent(start, i2);
								}
							}, ((long)(start.distance(end)*2l))); // 1 second per 10 blocks
						} else {
							addBS(id, l);
						}
					}
				}, 1l);
			}
		}, 5L, 5L);
    	CustomFeatureLoader.getLoader().getHandler().registerLocationEvent(start, i);*/
    }
    
    public void onHit(Location location) {
    	CustomFeatureLoader.getLoader().getHandler().unregisterLocationEvent(location);
    }
    
    public void setMissile(Location l) {
    	CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
    	World w = l.getWorld();
    	if (l.getY()==250D) return;
    	h.setCustomBlockToLocation(new Location(w, l.getX(), l.getY(), l.getZ()), "Pixlies:Missile_Warhead_Block_UNB_2");
    	h.setCustomBlockToLocation(new Location(w, l.getX(), l.getY()-2, l.getZ()-1), "Pixlies:Missile_Fin_UNB");
    	h.setCustomBlockToLocation(new Location(w, l.getX(), l.getY()-2, l.getZ()+1), "Pixlies:Missile_Fin_UNB");
    	h.setCustomBlockToLocation(new Location(w, l.getX()-1, l.getY()-2, l.getZ()), "Pixlies:Missile_Fin_UNB");
    	h.setCustomBlockToLocation(new Location(w, l.getX()+1, l.getY()-2, l.getZ()), "Pixlies:Missile_Fin_UNB");
    	h.setCustomBlockToLocation(new Location(w, l.getX(), l.getY()-1, l.getZ()), "Pixlies:Missile_Block_UNB");
    	h.setCustomBlockToLocation(new Location(w, l.getX(), l.getY()-2, l.getZ()), "Pixlies:Missile_Block_UNB");
    }
    
    public void setMissile2(Location l) {
    	CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
    	l.setY(l.getY()-4);
    	World w = l.getWorld();
    	h.setCustomBlockToLocation(new Location(w, l.getX(), l.getY(), l.getZ()), "Pixlies:Missile_Warhead_Block_UNB_2");
    	h.setCustomBlockToLocation(new Location(w, l.getX(), l.getY()+2, l.getZ()-1), "Pixlies:Missile_Fin_UNB");
    	h.setCustomBlockToLocation(new Location(w, l.getX(), l.getY()+2, l.getZ()+1), "Pixlies:Missile_Fin_UNB");
    	h.setCustomBlockToLocation(new Location(w, l.getX()-1, l.getY()+2, l.getZ()), "Pixlies:Missile_Fin_UNB");
    	h.setCustomBlockToLocation(new Location(w, l.getX()+1, l.getY()+2, l.getZ()), "Pixlies:Missile_Fin_UNB");
    	h.setCustomBlockToLocation(new Location(w, l.getX(), l.getY()+1, l.getZ()), "Pixlies:Missile_Block_UNB");
    	h.setCustomBlockToLocation(new Location(w, l.getX(), l.getY()+2, l.getZ()), "Pixlies:Missile_Block_UNB");
    }
    
    public void remMissile(Location l) {
    	CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
    	List<Block> blocks = getMissileBlocks(l.getBlock());
    	for (Block block : blocks) {
    		h.removeCustomBlockFromLocation(block.getLocation());
    	}
    }
    
    public void remMissile2(Location l) {
    	CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
    	List<Block> blocks = getMissileBlocks2(l.getBlock());
    	for (Block block : blocks) {
    		h.removeCustomBlockFromLocation(block.getLocation());
    	}
    }
    
    public List<Block> getMissileBlocks2(Block b) {
    	List<Block> blocks = new ArrayList<Block>();
    	blocks.add(b);
    	blocks.add(b.getWorld().getBlockAt(b.getLocation().getBlockX(), b.getLocation().getBlockY()+1, b.getLocation().getBlockZ()));
    	blocks.add(b.getWorld().getBlockAt(b.getLocation().getBlockX(), b.getLocation().getBlockY()+2, b.getLocation().getBlockZ()));
    	blocks.add(b.getWorld().getBlockAt(b.getLocation().getBlockX(), b.getLocation().getBlockY()+2, b.getLocation().getBlockZ()-1));
    	blocks.add(b.getWorld().getBlockAt(b.getLocation().getBlockX(), b.getLocation().getBlockY()+2, b.getLocation().getBlockZ()+1));
    	blocks.add(b.getWorld().getBlockAt(b.getLocation().getBlockX()-1, b.getLocation().getBlockY()+2, b.getLocation().getBlockZ()));
    	blocks.add(b.getWorld().getBlockAt(b.getLocation().getBlockX()+1, b.getLocation().getBlockY()+2, b.getLocation().getBlockZ()));
    	return blocks;
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
	
	public static HashMap<UUID, Location> bsm = new HashMap<>();
	
	public static Location getBS(UUID id) {
		return bsm.get(id);
	}
	
	public static void addBS(UUID id, Location l) {
		bsm.put(id, l);
	}
	
	public static void remBS(UUID id) {
		bsm.remove(id);
	}
}