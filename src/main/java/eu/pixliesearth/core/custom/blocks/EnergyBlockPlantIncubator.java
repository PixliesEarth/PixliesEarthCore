package eu.pixliesearth.core.custom.blocks;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import eu.pixliesearth.core.custom.CustomEnergyBlock;
import eu.pixliesearth.core.custom.CustomFeatureHandler;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.MinecraftMaterial;
import eu.pixliesearth.core.custom.listeners.CustomInventoryListener;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.NBTTagType;
import eu.pixliesearth.utils.NBTUtil;
import eu.pixliesearth.utils.Timer;

public class EnergyBlockPlantIncubator extends CustomEnergyBlock {
	
	public EnergyBlockPlantIncubator() {
		
	}
	
	@Override
    public Material getMaterial() {
        return Material.LIME_STAINED_GLASS;
    }
	
	public double getCapacity() {
		return 150000D;
	}
	
	@Override
	public Inventory getInventory() {
		Inventory inv = Bukkit.createInventory(null, 3*9, getInventoryTitle());
		for (int i = 0; i < 3*9; i++)
			inv.setItem(i, CustomItemUtil.getItemStackFromUUID(CustomInventoryListener.getUnclickableItemUUID()));
		// inv.setMaxStackSize(1);
		inv.clear(13); // Seed slot
		return inv;
	}
	
	@Override
	public boolean InventoryClickEvent(InventoryClickEvent event) {
		if (event.getCurrentItem()==null || event.getCurrentItem().getType().equals(Material.AIR)) return false;
		if (isUnclickable(event.getCurrentItem())) return true;
		if (event.getRawSlot()==13) {
			ItemStack itemStack = event.getInventory().getItem(13);
			if (itemStack==null || itemStack.getType().equals(Material.AIR)) return super.InventoryClickEvent(event);
			if (itemStack.getAmount()>1) {
				ItemStack itemStack2 = itemStack.clone();
				itemStack2.setAmount(itemStack.getAmount()-1);
				event.getWhoClicked().setItemOnCursor(itemStack2);
				itemStack.setAmount(1);
				event.getInventory().setItem(13, itemStack);
			}
		}
		return false;
	}
	
	@Override
	public void onTick(Location location, Inventory inventory, Timer timer) {
		
		double costToGrow = 100D;
		long timeToGrow = 4000L;
		
		inventory.setItem(25, buildInfoItem(location));
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		ItemStack is = inventory.getItem(13);
		
		if (is==null) return;
		
		String s = CustomItemUtil.getUUIDFromItemStack(is);
		
		if (s==null) return;
		
		if (timer==null) {
			if (getContainedPower(location)<costToGrow) { // Not enough power
				h.registerTimer(location, new Timer(500L));
				inventory.setItem(22, getSetItem(2, null));
				return;
			} else {
				if (s.equals(MinecraftMaterial.WHEAT_SEEDS.getUUID())) {
					// Default values
				} else if (s.equals(MinecraftMaterial.PUMPKIN_SEEDS.getUUID())) {
					timeToGrow += 3000L;
					costToGrow += 100D;
				} else if (s.equals(MinecraftMaterial.MELON_SEEDS.getUUID())) {
					timeToGrow += 3000L;
					costToGrow += 100D;
				} else if (s.equals(MinecraftMaterial.BEETROOT_SEEDS.getUUID())) {
					// Default values
				} else if (s.equals(MinecraftMaterial.OAK_SAPLING.getUUID())) {
					timeToGrow += 26000L;
					costToGrow += 1400D;
				} else {
					h.registerTimer(location, new Timer(1000L));
					inventory.setItem(22, getSetItem(0, null));
					return;
				}
				if (getContainedPower(location)<costToGrow) { // Not enough power
					h.registerTimer(location, new Timer(1000L));
					inventory.setItem(22, getSetItem(2, null));
					return;
				}
				inventory.setItem(13, new ItemBuilder(Material.YELLOW_STAINED_GLASS_PANE).setDisplayName("§aGrowing!").addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING).addNBTTag("EXTRA", CustomItemUtil.getUUIDFromItemStack(is), NBTTagType.STRING).build());
				h.registerTimer(location, new Timer(timeToGrow));
				h.removePowerFromLocation(location, costToGrow);
			}
		} else {
			if (timer.hasExpired()) {
				String v = NBTUtil.getTagsFromItem(is).getString("EXTRA");
				if (v!=null && !v.equalsIgnoreCase("")) {
					if (v.equals(MinecraftMaterial.WHEAT_SEEDS.getUUID())) {
						dropItem(new ItemStack(Material.WHEAT), location);
					} else if (v.equals(MinecraftMaterial.PUMPKIN_SEEDS.getUUID())) {
						dropItem(new ItemStack(Material.PUMPKIN), location);
					} else if (v.equals(MinecraftMaterial.MELON_SEEDS.getUUID())) {
						dropItem(new ItemStack(Material.MELON), location);
					} else if (v.equals(MinecraftMaterial.BEETROOT_SEEDS.getUUID())) {
						dropItem(new ItemStack(Material.BEETROOT), location);
					} else if (v.equals(MinecraftMaterial.OAK_SAPLING.getUUID())) {
						dropItem(new ItemStack(Material.OAK_LOG), location);
						dropItem(new ItemStack(Material.OAK_LOG), location);
						dropItem(new ItemStack(Material.OAK_LOG), location);
						dropItem(new ItemStack(Material.OAK_LOG), location);
						dropItem(new ItemStack(Material.OAK_LOG), location);
					} else {
						inventory.setItem(22, getSetItem(0, null));
					}
					inventory.clear(13);
				} else if (s.equalsIgnoreCase(CustomInventoryListener.getUnclickableItemUUID())) {
					inventory.setItem(22, getSetItem(0, null));
				}
				h.unregisterTimer(location);
			} else {
				if (is!=null) {
					if (s.equals(CustomInventoryListener.getUnclickableItemUUID())) {
						inventory.setItem(22, getSetItem(1, timer));
					}
				}
			}
		}
	}
	
	public void dropItem(ItemStack is, Location l) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(CustomFeatureLoader.getLoader().getInstance(), new Runnable() {
			@Override
			public void run() {
				l.getWorld().dropItemNaturally(l, is);
			}
		}, 0L);
	}
	
	public ItemStack getSetItem(int i, Timer timer) {
		return new ItemBuilder( (i==0) ? Material.BROWN_STAINED_GLASS_PANE : (i==1) ? Material.GREEN_STAINED_GLASS_PANE : Material.RED_STAINED_GLASS_PANE)
				.setDisplayName( (i==0) ? "§6This item cannot be incubated!" : (i==1) ? "§aGrowing!" : "§cNot enough energy for this!")
				.addLoreLine( (timer==null) ? "" : "§6Time remaning "+timer.getRemainingAsString())
				.addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING)
				.build();
	}

    @Override
    public String getDefaultDisplayName() {
        return "§6Plant Incubator";
    }

    @Override
    public String getUUID() {
        return "Machine:Plant_Incubator"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
	
}
