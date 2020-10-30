package eu.pixliesearth.core.custom.listeners;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.CustomListener;
import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.Methods;
import eu.pixliesearth.utils.NBTTagType;
import eu.pixliesearth.utils.NBTUtil;
import lombok.SneakyThrows;
import net.md_5.bungee.api.ChatColor;
/**
 * 
 * @author BradBot_1
 * 
 * <h3>Handles events for custom inventories</h3>
 * 
 * @apiNote TODO: notes
 */
public class CustomMachineCommandListener extends CustomListener {
	
	@EventHandler
    @SneakyThrows
    public void InventoryClickEvent(InventoryClickEvent event) {
		if (event.getCurrentItem()==null || event.getCurrentItem().getType().equals(Material.AIR)) return;
		if (event.getView().getTitle().equalsIgnoreCase("§6Machines")) {
			String data = NBTUtil.getTagsFromItem(event.getCurrentItem()).getString("EXTRA");
			if (data==null || data.equalsIgnoreCase("")) {
				
			} else {
				if (data.equalsIgnoreCase("MOPEN1")) {
					Inventory inv = Bukkit.createInventory(null, 6*9, "§6Machines : ?");
					int[] ints = {0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,44,45,46,47,48,49,50,51,52,53};
					for (int i : ints)
						inv.setItem(i, CustomItemUtil.getItemStackFromUUID(CustomInventoryListener.getUnclickableItemUUID()));
					// TODO
					inv.setItem(48, new ItemBuilder(Material.ARROW).setDisplayName("§bBack").addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING).addNBTTag("EXTRA", "MBACK", NBTTagType.STRING).build()); // Back
					inv.setItem(49, new ItemBuilder(Material.BARRIER).setDisplayName("§cClose").addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING).addNBTTag("EXTRA", "MCLOSE", NBTTagType.STRING).build()); // Close
					inv.setItem(50, new ItemBuilder(Material.ARROW).setDisplayName("§bNext").addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING).addNBTTag("EXTRA", "MNEXT", NBTTagType.STRING).build()); // Next
					event.getWhoClicked().closeInventory();
					event.getWhoClicked().openInventory(inv);
				} else if (data.equalsIgnoreCase("MOPEN2")) {
					Inventory inv = Bukkit.createInventory(null, 6*9, "§6Machines : Items");
					int[] ints = {0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,44,45,46,47,48,49,50,51,52,53};
					for (int i : ints)
						inv.setItem(i, CustomItemUtil.getItemStackFromUUID(CustomInventoryListener.getUnclickableItemUUID()));
					// TODO
					inv.setItem(48, new ItemBuilder(Material.ARROW).setDisplayName("§bBack").addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING).addNBTTag("EXTRA", "MBACK", NBTTagType.STRING).build()); // Back
					inv.setItem(49, new ItemBuilder(Material.BARRIER).setDisplayName("§cClose").addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING).addNBTTag("EXTRA", "MCLOSE", NBTTagType.STRING).build()); // Close
					inv.setItem(50, new ItemBuilder(Material.ARROW).setDisplayName("§bNext").addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING).addNBTTag("EXTRA", "MNEXT", NBTTagType.STRING).build()); // Next
					event.getWhoClicked().closeInventory();
					event.getWhoClicked().openInventory(inv);
				} else if (data.equalsIgnoreCase("MOPEN3")) {
					Inventory inv = Bukkit.createInventory(null, 6*9, "§6Machines : Recipes");
					int[] ints = {0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,44,45,46,47,48,49,50,51,52,53};
					for (int i : ints)
						inv.setItem(i, CustomItemUtil.getItemStackFromUUID(CustomInventoryListener.getUnclickableItemUUID()));
					inv.setItem(48, new ItemBuilder(Material.ARROW).setDisplayName("§bBack").addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING).addNBTTag("EXTRA", "MBACK", NBTTagType.STRING).build()); // Back
					inv.setItem(49, new ItemBuilder(Material.BARRIER).setDisplayName("§cClose").addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING).addNBTTag("EXTRA", "MCLOSE", NBTTagType.STRING).build()); // Close
					inv.setItem(50, new ItemBuilder(Material.ARROW).setDisplayName("§bNext").addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING).addNBTTag("EXTRA", "MNEXT", NBTTagType.STRING).build()); // Next
					List<String> array = Methods.convertSetIntoList(getNames(CustomFeatureLoader.getLoader().getHandler().getCustomRecipes()));
					Collections.sort(array);
					set(inv, array);
					event.getWhoClicked().closeInventory();
					event.getWhoClicked().openInventory(inv);
				} else {
					
				}
			}
			event.setCancelled(true);
		} else if (event.getView().getTitle().equalsIgnoreCase("§6Machines : ?")) { // TODO: decide what does here
			String data = NBTUtil.getTagsFromItem(event.getCurrentItem()).getString("EXTRA");
			if (data==null || data.equalsIgnoreCase("")) {
				
			} else {
				if (data.equalsIgnoreCase("MNEXT")) {
					
				} else if (data.equalsIgnoreCase("MBACK")) {
					
				} else if (data.equalsIgnoreCase("MCLOSE")) {
					openBase(event.getWhoClicked());
				}
			}
			event.setCancelled(true);
		} else if (event.getView().getTitle().equalsIgnoreCase("§6Machines : Items")) {
			String data = NBTUtil.getTagsFromItem(event.getCurrentItem()).getString("EXTRA");
			if (data==null || data.equalsIgnoreCase("")) {
				
			} else {
				if (data.equalsIgnoreCase("MNEXT")) {
					
				} else if (data.equalsIgnoreCase("MBACK")) {
					
				} else if (data.equalsIgnoreCase("MCLOSE")) {
					openBase(event.getWhoClicked());
				}
			}
			event.setCancelled(true);
		} else if (event.getView().getTitle().equalsIgnoreCase("§6Machines : Recipes")) {
			String data = NBTUtil.getTagsFromItem(event.getCurrentItem()).getString("EXTRA");
			if (data==null || data.equalsIgnoreCase("")) {
				
			} else {
				if (data.equalsIgnoreCase("MNEXT")) {
					List<String> array = Methods.convertSetIntoList(getNames(CustomFeatureLoader.getLoader().getHandler().getCustomRecipes()));
					Collections.sort(array);
					if (array.indexOf((CustomItemUtil.getUUIDFromItemStack(event.getInventory().getItem(43)))+1)>=array.size()) {
						openBase(event.getWhoClicked());
					} else {
						set(event.getInventory(), array);
					}
				} else if (data.equalsIgnoreCase("MBACK")) {
					List<String> array = Methods.convertSetIntoList(getNames(CustomFeatureLoader.getLoader().getHandler().getCustomRecipes()));
					Collections.sort(array);
					if (array.indexOf(CustomItemUtil.getUUIDFromItemStack(event.getInventory().getItem(10)))==0) {
						openBase(event.getWhoClicked());
					} else {
						set2(event.getInventory(), array);
					}
				} else if (data.equalsIgnoreCase("MCLOSE")) {
					openBase(event.getWhoClicked());
				}
			}
			event.setCancelled(true);
		} else {
			return;
		}
	}
	
	public void set(Inventory inv, List<String> list) {
		int[] ints = {10,11,12,13,14,15,16,19,20,21,22,23,24,25,28,29,30,31,32,33,34,37,38,39,40,41,42,43};
		if (inv.firstEmpty()==-1) {
			ItemStack is = inv.getItem(43);
			String id = CustomItemUtil.getUUIDFromItemStack(is);
			if (id==null) return;
			int i = 0;
			for (String s : list)
				if (s.equalsIgnoreCase(id)) 
					i = list.indexOf(s);
			if (i==0) {
				int i2 = 0;
				for (int i3 : ints) {
					inv.setItem(i3, (CustomItemUtil.getItemStackFromUUID(list.get(i2))));
					i2++;
				}
			} else {
				int i2 = i+1;
				for (int i3 : ints) {
					inv.setItem(i3, (CustomItemUtil.getItemStackFromUUID(list.get(i2))));
				}
			}
		} else {
			int i = 0;
			for (int i2 : ints) {
				inv.setItem(i2, (CustomItemUtil.getItemStackFromUUID(list.get(i))));
				i++;
			}
		}
	}
	
	public void set2(Inventory inv, List<String> list) {
		int[] ints = {10,11,12,13,14,15,16,19,20,21,22,23,24,25,28,29,30,31,32,33,34,37,38,39,40,41,42,43};
		if (inv.firstEmpty()==-1) {
			ItemStack is = inv.getItem(10);
			String id = CustomItemUtil.getUUIDFromItemStack(is);
			if (id==null) return;
			int i = 0;
			for (String s : list)
				if (s.equalsIgnoreCase(id)) 
					i = list.indexOf(s);
			if (i==0) {
				int i2 = 0;
				for (int i3 : ints) {
					inv.setItem(i3, (CustomItemUtil.getItemStackFromUUID(list.get(i2))));
					i2++;
				}
			} else {
				int i2 = i-28;
				for (int i3 : ints) {
					inv.setItem(i3, (CustomItemUtil.getItemStackFromUUID(list.get(i2))));
				}
			}
		} else {
			int i = 0;
			for (int i2 : ints) {
				inv.setItem(i2, (CustomItemUtil.getItemStackFromUUID(list.get(i))));
				i++;
			}
		}
	}
	
	public CustomRecipe getRecipeFromUUID(String resultUUID) {
		for (CustomRecipe r : CustomFeatureLoader.getLoader().getHandler().getCustomRecipes()) 
			if (ChatColor.stripColor(r.getResultUUID()).equalsIgnoreCase(resultUUID)) 
				return r;
		return null;
	}
	
	public void openBase(HumanEntity e) {
		Inventory inv = Bukkit.createInventory(null, 3*9, "§6Machines");
		for (int i = 0; i < 3*9; i++)
			inv.setItem(i, CustomItemUtil.getItemStackFromUUID(CustomInventoryListener.getUnclickableItemUUID()));
		inv.setItem(11, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).setDisplayName("§b?").addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING).addNBTTag("EXTRA", "MOPEN1", NBTTagType.STRING).build());
		inv.setItem(13, new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setDisplayName("§bItems").addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING).addNBTTag("EXTRA", "MOPEN2", NBTTagType.STRING).build());
		inv.setItem(15, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayName("§bRecipes").addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING).addNBTTag("EXTRA", "MOPEN3", NBTTagType.STRING).build());
		e.closeInventory();
		e.openInventory(inv);
	}
	
	public Set<String> getNames(Set<CustomRecipe> rs) {
		Set<String> set = new HashSet<String>();
		for (CustomRecipe r : rs) {
			if (r.getResultUUID()==null || r.getResultUUID().equalsIgnoreCase("") || CustomItemUtil.getItemStackFromUUID(r.getResultUUID())==null) continue;
			set.add(ChatColor.stripColor(r.getResultUUID()));
		}
		return set;
	}
}
