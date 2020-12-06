package eu.pixliesearth.core.custom.machines;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import eu.pixliesearth.core.custom.CustomEnergyCrafterMachine;
import eu.pixliesearth.core.custom.CustomFeatureHandler;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.MinecraftMaterial;
import eu.pixliesearth.core.custom.listeners.CustomInventoryListener;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.Timer;

public class EnergyMachineCrusher extends CustomEnergyCrafterMachine {
	
	public EnergyMachineCrusher() {
		
	}
	
	@Override
	public String getPlayerHeadUUID() {
		return "faf0c95ceba34c7fe6d33404feb87b4184ccce143978622c1647feaed2b63274";
	}

    @Override
    public String getDefaultDisplayName() {
        return "§6Crusher";
    }

    @Override
    public String getUUID() {
        return "Machine:Ore_Crusher"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }

	public double getCapacity() {
		return 15000D;
	}
	
	@Override
	public void open(Player player, Location location) {
		Inventory i = CustomFeatureLoader.getLoader().getHandler().getInventoryFromLocation(location);
		if (i==null) return;
		player.openInventory(i);
	}
	
	@Override
	public Inventory getInventory() { 
		Inventory inv = Bukkit.createInventory(null, 5*9, getInventoryTitle());
		int[] ints = {14,15,16,23,24,25,32,33,34};
		for (int i = 0; i < 5*9; i++)
			inv.setItem(i, CustomItemUtil.getItemStackFromUUID(CustomInventoryListener.getUnclickableItemUUID()));
		inv.clear(43);
		inv.clear(19); // Input slot
		// inv.clear(21); // Current slot
		for (int i : ints) 
			inv.clear(i);
		return inv;
	}
	
	@Override
	public void onTick(Location loc, Inventory inv, Timer timer) {
		if (loc==null) return;
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		if (inv==null) return;
		inv.setItem(43, buildInfoItem(loc));
		if (timer==null) {
			h.registerTimer(loc, new Timer(1000L));
			if (getContainedPower(loc)<0.5D) {
				return; // Not enough power
			}
			ItemStack input = inv.getItem(19); // Input slot
			if (input==null || input.getType().equals(Material.AIR)) {
				// inv.setItem(21, CustomItemUtil.getItemStackFromUUID(CustomInventoryListener.getUnclickableItemUUID()));
				return;
			}
			String id = CustomItemUtil.getUUIDFromItemStack(input);
			if (id==null) {
				inv.setItem(21, CustomItemUtil.getItemStackFromUUID(CustomInventoryListener.getUnclickableItemUUID()));
				return;
			} else if (id.equalsIgnoreCase(MinecraftMaterial.IRON_ORE.getUUID())) {
				ItemStack[] table = {
						// 30 iron
						CustomItemUtil.getItemStackFromUUID("Pixlies:Iron_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Iron_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Iron_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Iron_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Iron_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Iron_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Iron_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Iron_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Iron_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Iron_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Iron_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Iron_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Iron_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Iron_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Iron_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Iron_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Iron_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Iron_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Iron_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Iron_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Iron_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Iron_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Iron_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Iron_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Iron_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Iron_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Iron_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Iron_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Iron_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Iron_Dust"),
						// 7 aluminum
						CustomItemUtil.getItemStackFromUUID("Pixlies:Aluminium_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Aluminium_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Aluminium_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Aluminium_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Aluminium_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Aluminium_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Aluminium_Dust"),
						// 5 Lithuim
						CustomItemUtil.getItemStackFromUUID("Pixlies:Lithium_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Lithium_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Lithium_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Lithium_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Lithium_Dust"),
						// 5 Zinc
						CustomItemUtil.getItemStackFromUUID("Pixlies:Zinc_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Zinc_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Zinc_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Zinc_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Zinc_Dust"),
						// 7 Tin
						CustomItemUtil.getItemStackFromUUID("Pixlies:Tin_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Tin_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Tin_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Tin_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Tin_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Tin_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Tin_Dust"),
						// 1 titanium
						CustomItemUtil.getItemStackFromUUID("Pixlies:Titanium_Dust"),
						// 1 Cobalt
						CustomItemUtil.getItemStackFromUUID("Pixlies:Cobalt_Dust"),
						// 1 platinum
						CustomItemUtil.getItemStackFromUUID("Pixlies:Platinum_Dust"),
						// 2 lead
						CustomItemUtil.getItemStackFromUUID("Pixlies:Lead_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Lead_Dust"),
						// 2 silver
						CustomItemUtil.getItemStackFromUUID("Pixlies:Silver_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Silver_Dust")
				};
				addToResult(loc, inv, table[new Random().nextInt(table.length)]);
			} else if (id.equalsIgnoreCase(MinecraftMaterial.GOLD_ORE.getUUID())) {
				ItemStack[] table = {
						// 20 gold
						CustomItemUtil.getItemStackFromUUID("Pixlies:Gold_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Gold_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Gold_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Gold_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Gold_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Gold_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Gold_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Gold_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Gold_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Gold_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Gold_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Gold_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Gold_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Gold_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Gold_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Gold_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Gold_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Gold_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Gold_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Gold_Dust"),
						// 10 bronze
						CustomItemUtil.getItemStackFromUUID("Pixlies:Bronze_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Bronze_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Bronze_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Bronze_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Bronze_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Bronze_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Bronze_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Bronze_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Bronze_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Bronze_Dust"),
						// 10 Copper
						CustomItemUtil.getItemStackFromUUID("Pixlies:Copper_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Copper_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Copper_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Copper_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Copper_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Copper_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Copper_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Copper_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Copper_Dust"),
						CustomItemUtil.getItemStackFromUUID("Pixlies:Copper_Dust")
				};
				addToResult(loc, inv, table[new Random().nextInt(table.length)]);
			} else if (id.equalsIgnoreCase(MinecraftMaterial.COAL_ORE.getUUID()) || id.equalsIgnoreCase(MinecraftMaterial.COAL.getUUID())) {
				ItemStack[] table = {
						CustomItemUtil.getItemStackFromUUID("Pixlies:Carbon_Dust")
				};
				addToResult(loc, inv, table[new Random().nextInt(table.length)]);
			} else if (id.equalsIgnoreCase(MinecraftMaterial.DIAMOND_ORE.getUUID())) {
				ItemStack[] table = {
						CustomItemUtil.getItemStackFromUUID("Pixlies:Carbon")
				};
				addToResult(loc, inv, table[new Random().nextInt(table.length)]);
			} else if(id.equalsIgnoreCase(MinecraftMaterial.ROTTEN_FLESH.getUUID()) || id.equalsIgnoreCase(MinecraftMaterial.WHEAT.getUUID()) || id.equalsIgnoreCase(MinecraftMaterial.POTATO.getUUID()) || id.equalsIgnoreCase(MinecraftMaterial.CARROT.getUUID()) || id.equalsIgnoreCase(MinecraftMaterial.PUMPKIN.getUUID()) || id.equalsIgnoreCase(MinecraftMaterial.PUMPKIN_PIE.getUUID()) || id.equalsIgnoreCase(MinecraftMaterial.BREAD.getUUID()) || id.equalsIgnoreCase(MinecraftMaterial.MELON.getUUID()) || id.equalsIgnoreCase(MinecraftMaterial.MELON_SLICE.getUUID())) {
				ItemStack[] table = {
						CustomItemUtil.getItemStackFromUUID("Pixlies:Biofuel")
				};
				addToResult(loc, inv, table[new Random().nextInt(table.length)]);
			} else {
				return;
			}
			input.setAmount(input.getAmount()-1);
			h.removePowerFromLocation(loc, 0.5D);
		} else {
			if (timer.hasExpired()) {
				h.unregisterTimer(loc);
			} else {
				// Do nothing
			}
		}
	}
	
	@Override
	public boolean InventoryClickEvent(InventoryClickEvent event) {
		if (event.getCurrentItem()==null) return false;
		if (isUnclickable(event.getCurrentItem())) return true;
		return false;
	}
	
	public void addToResult(Location loc, Inventory inv, ItemStack is) {
		int[] ints = {14,15,16,23,24,25,32,33,34};
		for (int i : ints) {
			ItemStack is2 = inv.getItem(i);
			if (is2==null || is2.getType().equals(Material.AIR)) {
				inv.setItem(i, is);
				return;
			} else {
				ItemStack is3 = is2.asOne();
				if (is3.equals(is)) {
					if (is2.getAmount()<64) {
						is2.setAmount(is2.getAmount()+1);
						return;
					} else {
						continue;
					}
				} else {
					continue;
				}
			}
		}
		Bukkit.getScheduler().scheduleSyncDelayedTask(CustomFeatureLoader.getLoader().getInstance(), new Runnable() {@Override public void run() {loc.getWorld().dropItemNaturally(loc, is);}}, 0L);
	}
}
