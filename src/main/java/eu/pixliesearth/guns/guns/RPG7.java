package eu.pixliesearth.guns.guns;

import eu.pixliesearth.guns.CustomGun;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.guns.PixliesAmmo;
import eu.pixliesearth.guns.PixliesAmmo.AmmoType;
import eu.pixliesearth.guns.events.PixliesGunShootEvent;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.Methods;
import eu.pixliesearth.utils.NBTTagType;
import eu.pixliesearth.utils.NBTUtil;

public class RPG7 extends CustomGun {

	@Override
	public ItemUseHandler getItemHandler() {
		return (event) -> {
			if (!event.getHand().equals(EquipmentSlot.HAND)) return;
			ItemStack itemStack = event.getItem();
			if (itemStack == null || itemStack.getType().equals(Material.AIR)) return;
			int ammo = Integer.parseInt(NBTUtil.getTagsFromItem(itemStack).getString("ammo"));
			if (ammo == 0) { // Reload
				if (Methods.removeRequiredAmount(this.getAmmoType().getAmmo().getItem(), event.getPlayer().getInventory())) {
					event.getPlayer().getInventory().setItemInMainHand(null);
					event.getPlayer().sendActionBar("§b§lReloading...");
					Bukkit.getScheduler().scheduleSyncDelayedTask(CustomFeatureLoader.getLoader().getInstance(), new Runnable() {

						@Override
						public void run() {
							event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.BLOCK_PISTON_EXTEND, 1, 1);
							ItemStack itemStack2 = new ItemBuilder(itemStack).setDisplayName(getName(getMaxAmmo())).addNBTTag("ammo", Integer.toString(getMaxAmmo()), NBTTagType.STRING).build();
							event.getPlayer().sendActionBar("§a§lReloaded!");
							event.getPlayer().getInventory().setItemInMainHand(itemStack2);
						}

					}, 20 * getDelayToReload());
				} else {
					event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 1, 1);
					event.getPlayer().sendActionBar("§c§lNO AMMO!");
				}
			} else { // Shoot
				PixliesAmmo pammo = this.getAmmoType().getAmmo().createNewOne(event.getPlayer().getLocation(), this);
				PixliesGunShootEvent shootEvent = new PixliesGunShootEvent(event.getPlayer(), pammo);
				shootEvent.callEvent();
				if (!shootEvent.isCancelled()) {
					event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 2, 2);
					event.getPlayer().getInventory().setItemInMainHand(new ItemBuilder(itemStack).setDisplayName(getName(ammo - 1)).addNBTTag("ammo", Integer.toString(ammo - 1), NBTTagType.STRING).build());
					Block block = event.getPlayer().getTargetBlock(getRange());
					if (block == null) {
						return;
					} else {
						event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 2, 2);
						double distance = event.getPlayer().getEyeLocation().distanceSquared(block.getLocation()) * 10000;
						long ticks = 0;
						while (distance > 10000) {
							distance /= 10000;
							ticks++;
						}
						Bukkit.getScheduler().runTaskLater(CustomFeatureLoader.getLoader().getInstance(), () -> block.getLocation().createExplosion(3F, true), ticks);
					}
				}
			}
		};
    }

    public static String getDefaultDisplayName() {
        return "§c§lRPG-7";
    }

    public static Integer getCustomModelData() {
        return 15;
    }

    public static String getUUID() {
        return "Gun:RPG7";
    }

    public static int getMaxAmmo() {
        return 1;
    }

    public static int getRange() {
        return 100;
    }

    public static double getAccuracy() {
        return 0.1;
    }

    public static int getDelayToReload() {
        return 2;
    }

    public static AmmoType getAmmoType() {
        return AmmoType.ROCKET;
    }

    public static String getOrigin() {
        return "Soviet Union/USSR";
    }

    public static long getDelayPerShot() {
        return 1250L;
    }


}
