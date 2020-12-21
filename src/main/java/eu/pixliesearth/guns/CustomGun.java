package eu.pixliesearth.guns;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.CustomItem;
import eu.pixliesearth.guns.PixliesAmmo.AmmoType;
import eu.pixliesearth.guns.events.PixliesGunShootEvent;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.Methods;
import eu.pixliesearth.utils.NBTTagType;
import eu.pixliesearth.utils.NBTUtil;
import net.minecraft.server.v1_16_R3.AxisAlignedBB;

public abstract class CustomGun extends CustomItem {

	@Override
	public Material getMaterial() {
		return Material.CARROT_ON_A_STICK;
	}

	@Override
	public ItemStack buildItem() {
		return new ItemBuilder(getMaterial()) {{
			setDisplayName(getDefaultDisplayName());
			if (getDefaultLore()!=null) 
				addLoreAll(getDefaultLore());
			if (getCustomModelData()!=null) 
				setCustomModelData(getCustomModelData());
			for (ItemFlag flag : getItemFlags()) 
				addItemFlag(flag);
			addNBTTag("UUID", getUUID(), NBTTagType.STRING);
			addNBTTag("ammo", Integer.toString(0), NBTTagType.STRING);
		}}.build();
	}

	@Override
	public boolean PlayerInteractEvent(PlayerInteractEvent event) {
		if (!event.getHand().equals(EquipmentSlot.HAND)) return true;
		final ItemStack itemStack = event.getItem();
		if (itemStack==null || itemStack.getType().equals(Material.AIR)) return false;
		int ammo = Integer.parseInt(NBTUtil.getTagsFromItem(itemStack).getString("ammo"));
		if (ammo<=0) { // Reload
			CustomItem customItem = CustomItemUtil.getCustomItemFromUUID(CustomItemUtil.getUUIDFromItemStack(itemStack));
			if (customItem instanceof CustomGun) {
				CustomGun customGun = (CustomGun) customItem;
				if (Methods.removeRequiredAmount(customGun.getAmmoType().getAmmo().getItem(), event.getPlayer().getInventory())) {
					event.getPlayer().sendActionBar("§b§lReloading...");
					event.getPlayer().getInventory().setItemInMainHand(null);
					Bukkit.getScheduler().scheduleSyncDelayedTask(CustomFeatureLoader.getLoader().getInstance(), () -> {
						event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.BLOCK_PISTON_EXTEND, 1, 1);
						ItemStack itemStack2 = new ItemBuilder(itemStack).setDisplayName(getName(getMaxAmmo())).addNBTTag("ammo", Integer.toString(getMaxAmmo()), NBTTagType.STRING).build();
						event.getPlayer().sendActionBar("§a§lReloaded!");
						event.getPlayer().getInventory().setItemInMainHand(itemStack2);
					}, 20L * getDelayToReload());
				} else {
					event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 1, 1);
					event.getPlayer().sendActionBar("§c§lNO AMMO!");
				}
			} else {
				event.getPlayer().sendActionBar("§c§lInvalid gun!");
			}
		} else { // Shoot
			CustomItem customItem = CustomItemUtil.getCustomItemFromUUID(CustomItemUtil.getUUIDFromItemStack(itemStack));
			if (customItem instanceof CustomGun) {
				CustomGun customGun = (CustomGun) customItem;
				PixliesAmmo pammo = customGun.getAmmoType().getAmmo().createNewOne(event.getPlayer().getLocation(), this);
				PixliesGunShootEvent shootEvent = new PixliesGunShootEvent(event.getPlayer(), pammo);
				shootEvent.callEvent();
				if (!shootEvent.isCancelled()) {
					event.getPlayer().getInventory().setItemInMainHand(new ItemBuilder(itemStack).setDisplayName(getName(ammo-1)).addNBTTag("ammo", Integer.toString(ammo-1), NBTTagType.STRING).build());
					event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 2, 2);
					// GunFireResult result = pammo.trace(event.getPlayer());
					/*RayTraceResult result = event.getPlayer().getLocation().getWorld().rayTraceEntities(new Location(event.getPlayer().getWorld(), event.getPlayer().getEyeLocation().getX(), y, z), event.getPlayer().getLocation().getDirection(), getRange());
					if (result==null) {
						System.out.println("null");
						return true;
					} else if (result.getHitEntity()!=null) {
						Entity entity = result.getHitEntity();
						double damage = pammo.getDamage();
						if (entity instanceof LivingEntity) {
							LivingEntity livingEntity = (LivingEntity) entity;
							if (result.getHitPosition().equals(livingEntity.getEyeLocation().toVector())) {
								damage *= 2;
								entity.getWorld().playSound(entity.getLocation(), Sound.BLOCK_ANVIL_HIT, 1, 1);
							}
						}
						if (entity instanceof Damageable) {
							Damageable damageable = (Damageable) entity;
							damageable.setLastDamageCause(new EntityDamageByEntityEvent(event.getPlayer(), entity, EntityDamageEvent.DamageCause.ENTITY_ATTACK, damage));
							damageable.setHealth(damageable.getHealth() - damage);
						} else {
							entity.remove();
						}
					}*/
					event.getPlayer().getWorld().playEffect(event.getPlayer().getEyeLocation().add(1, -1, 1), Effect.SMOKE, 2);
					
					LivingEntity result = null;
					boolean headshot = false;
					int maxDistance = getRange();
					Player player = event.getPlayer();
					Location location = player.getEyeLocation();
					Block block = event.getPlayer().getTargetBlock(null, maxDistance);
			        if (block.getType().isSolid()) {
			        	maxDistance = (int) Math.min(maxDistance, block.getLocation().distance(location));
			        }
			        Collection<LivingEntity> entityList = event.getPlayer().getWorld().getNearbyLivingEntities(location, maxDistance);
			        if (!entityList.isEmpty()) {
			        	Vector vector = location.toVector();
			        	x:
			        	for(double distance = 0.0; distance <= maxDistance; distance += getAccuracy()) {
			        		Vector vector2 = vector.clone().add(location.getDirection().clone().multiply(distance));
			        		Location location2 = vector2.toLocation(event.getPlayer().getWorld());
			        		AxisAlignedBB axisAlignedBB = new AxisAlignedBB(location2.getX(), location2.getY(), location2.getZ(), location2.getX(), location2.getY(), location2.getZ());
			        		for(LivingEntity entity : entityList) {
			        			if(entity == null || entity.isDead() || entity.getEntityId() == event.getPlayer().getEntityId()) {
			        				continue;
			        			} else {
			        				AxisAlignedBB axisAlignedBB2 = ((CraftLivingEntity) entity).getHandle().getBoundingBox();
			        				if(axisAlignedBB2.intersects(axisAlignedBB)) {
			        					result = entity;
			        					headshot = location2.distance(entity.getEyeLocation()) <= 0.5;
			        					break x;
			        				}
			        			}
			        		}
			        	}
			        }
			        if (result==null) {
			        	
			        } else {
			        	if (headshot) {
			        		result.setLastDamageCause(new EntityDamageByEntityEvent(event.getPlayer(), player, EntityDamageEvent.DamageCause.ENTITY_ATTACK, (pammo.getDamage()*2)));
			        		// double newHealth = (result.getHealth() - (pammo.getDamage()*2));
			        		// result.setHealth((newHealth<0) ? 0 : newHealth);
							EntityDamageByEntityEvent nevent = new EntityDamageByEntityEvent(player, result, EntityDamageEvent.DamageCause.ENTITY_ATTACK, pammo.getDamage() * 2);
							Bukkit.getPluginManager().callEvent(nevent);
							if (!nevent.isCancelled()) {
								if (result.getHealth() - (pammo.getDamage() * 2.0) <= 0) {
									result.setHealth(0);
								} else {
									result.setHealth(result.getHealth() - (pammo.getDamage()));
								}
								result.getWorld().playSound(result.getLocation(), Sound.BLOCK_ANVIL_HIT, 1, 1);
							}
			        	} else {
			        		result.setLastDamageCause(new EntityDamageByEntityEvent(event.getPlayer(), player, EntityDamageEvent.DamageCause.ENTITY_ATTACK, pammo.getDamage()));
			        		// double newHealth = (result.getHealth() - pammo.getDamage());
			        		// result.setHealth((newHealth<0) ? 0 : newHealth);
							EntityDamageByEntityEvent nevent = new EntityDamageByEntityEvent(player, result, EntityDamageEvent.DamageCause.ENTITY_ATTACK, pammo.getDamage() * 2);
							Bukkit.getPluginManager().callEvent(nevent);
							if (!nevent.isCancelled()) {
								if (result.getHealth() - pammo.getDamage() <= 0) {
									result.setHealth(0);
								} else {
									result.setHealth(result.getHealth() - (pammo.getDamage()));
								}
								result.getWorld().playSound(result.getLocation(), Sound.BLOCK_ANVIL_HIT, 1, 1);
							}
			        	}
			        }
				} else {
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public List<String> getDefaultLore() {
		List<String> list = new ArrayList<>();
		list.add("§7Ammo: §3"+getAmmoType().name());
		list.add("§7Origin: §b"+getOrigin());
		list.add("§7Range: §3"+getRange()+" blocks");
		list.add("§7Accuracy: §3"+getAccuracy());
		return list;
	}

	@Override
	public boolean isUnstackable() {
		return true;
	}

	// NEW METHODS

	public abstract int getMaxAmmo();

	public abstract int getRange();

	public abstract double getAccuracy();
	
	// public abstract long getDelayPerShot();

	public abstract int getDelayToReload();

	public abstract AmmoType getAmmoType();

	public abstract String getOrigin();

	protected String getName(int ammo) {
		return getDefaultDisplayName()+" §8| §8[§c" + ammo + "§7/§c"+ getMaxAmmo() + "§8]";
	}

}
