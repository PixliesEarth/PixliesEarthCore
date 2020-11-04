package eu.pixliesearth.core.custom.listeners;

import java.util.ArrayList;
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
import eu.pixliesearth.core.custom.CustomItem;
import eu.pixliesearth.core.custom.CustomListener;
import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.interfaces.Recipeable;
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
					inv.setItem(48, new ItemBuilder(Material.ARROW).setDisplayName("§bBack").addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING).addNBTTag("EXTRA", "MBACK", NBTTagType.STRING).build()); // Back
					inv.setItem(49, new ItemBuilder(Material.BARRIER).setDisplayName("§cClose").addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING).addNBTTag("EXTRA", "MCLOSE", NBTTagType.STRING).build()); // Close
					inv.setItem(50, new ItemBuilder(Material.ARROW).setDisplayName("§bNext").addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING).addNBTTag("EXTRA", "MNEXT", NBTTagType.STRING).build()); // Next
					List<String> array = Methods.convertSetIntoList(getNames2(CustomFeatureLoader.getLoader().getHandler().getCustomItems()));
					Collections.sort(array);
					set(inv, array);
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
		} else if (event.getView().getTitle().equalsIgnoreCase(Recipeable.craftingExampleTitle)) {
			String data = NBTUtil.getTagsFromItem(event.getCurrentItem()).getString("EXTRA");
			if (data==null || data.equalsIgnoreCase("")) {
				
			} else {
				if (data.equalsIgnoreCase("MNEXT")) {
					List<CustomRecipe> list = getRecipesOfUUIDInOrderedList(CustomItemUtil.getUUIDFromItemStack(event.getInventory().getItem(Recipeable.recipeItemSlot)));
					if (list.isEmpty()) {
						event.getWhoClicked().closeInventory();
						event.getWhoClicked().openInventory(getErrorInventory("No recipes found for this item!"));
					} else if (list.size()==1) {
						CustomRecipe r = list.get(0);
						CustomItem c = CustomItemUtil.getCustomItemFromUUID(r.craftedInUUID());
						if (c!=null) {
							if (c instanceof Recipeable) {
								event.getWhoClicked().closeInventory();
								Inventory inv = ((Recipeable)c).getCraftingExample(r);
								inv.setItem(Recipeable.recipeItemSlot, new ItemBuilder(CustomItemUtil.getItemStackFromUUID(r.getResultUUID())).addNBTTag("LIST", Integer.toString(0), NBTTagType.STRING).build());
								inv.setItem(Recipeable.cratinInItemSlot, CustomItemUtil.getItemStackFromUUID(r.craftedInUUID()));
								event.getWhoClicked().openInventory(inv);
							} else {
								event.getWhoClicked().closeInventory();
								event.getWhoClicked().openInventory(getErrorInventory("Unable to get recipe gui"));
							}
						} else {
							event.getWhoClicked().closeInventory();
							event.getWhoClicked().openInventory(getErrorInventory("Unable to get recipe gui"));
						}
					} else {
						String s = NBTUtil.getTagsFromItem(event.getInventory().getItem(Recipeable.recipeItemSlot)).getString("LIST");
						try {
							int i = Integer.parseInt(s)+1;
							CustomRecipe r = list.get(i);
							CustomItem c = CustomItemUtil.getCustomItemFromUUID(r.craftedInUUID());
							if (c!=null) {
								if (c instanceof Recipeable) {
									event.getWhoClicked().closeInventory();
									Inventory inv = ((Recipeable)c).getCraftingExample(r);
									inv.setItem(Recipeable.recipeItemSlot, new ItemBuilder(CustomItemUtil.getItemStackFromUUID(r.getResultUUID())).addNBTTag("LIST", Integer.toString(i), NBTTagType.STRING).build());
									inv.setItem(Recipeable.cratinInItemSlot, CustomItemUtil.getItemStackFromUUID(r.craftedInUUID()));
									event.getWhoClicked().openInventory(inv);
								} else {
									event.getWhoClicked().closeInventory();
									event.getWhoClicked().openInventory(getErrorInventory("Unable to get recipe gui"));
								}
							} else {
								event.getWhoClicked().closeInventory();
								event.getWhoClicked().openInventory(getErrorInventory("Unable to get recipe gui"));
							}
						} catch (Exception e) {
							event.getWhoClicked().closeInventory();
							event.getWhoClicked().openInventory(getErrorInventory("Unable to parse the recipe!"));
						}
					}
				} else if (data.equalsIgnoreCase("MBACK")) {
					// TODO
				} else if (data.equalsIgnoreCase("MCLOSE")) {
					openBase(event.getWhoClicked());
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
					List<String> array = Methods.convertSetIntoList(getNames2(CustomFeatureLoader.getLoader().getHandler().getCustomItems()));
					Collections.sort(array);
					set(event.getInventory(), array);
				} else if (data.equalsIgnoreCase("MBACK")) {
					List<String> array = Methods.convertSetIntoList(getNames2(CustomFeatureLoader.getLoader().getHandler().getCustomItems()));
					Collections.sort(array);
					set2(event.getInventory(), array);
				} else if (data.equalsIgnoreCase("MCLOSE")) {
					openBase(event.getWhoClicked());
				}
			}
			event.setCancelled(true);
		} else if (event.getView().getTitle().equalsIgnoreCase("§6Machines : Recipes")) {
			String data = NBTUtil.getTagsFromItem(event.getCurrentItem()).getString("EXTRA");
			if (data==null || data.equalsIgnoreCase("")) {
				String s = CustomItemUtil.getUUIDFromItemStack(event.getCurrentItem());
				List<CustomRecipe> list = getRecipesOfUUIDInOrderedList(s);
				if (list.isEmpty()) {
					event.getWhoClicked().closeInventory();
					event.getWhoClicked().openInventory(getErrorInventory("This item has no recipes!"));
				} else {
					CustomRecipe r = list.get(0);
					CustomItem c = CustomItemUtil.getCustomItemFromUUID(r.craftedInUUID());
					if (c!=null && c instanceof Recipeable) {
						event.getWhoClicked().closeInventory();
						Inventory inv = ((Recipeable)c).getCraftingExample(r);
						inv.setItem(Recipeable.recipeItemSlot, new ItemBuilder(CustomItemUtil.getItemStackFromUUID(r.getResultUUID())).addNBTTag("LIST", Integer.toString(1), NBTTagType.STRING).build());
						inv.setItem(Recipeable.cratinInItemSlot, CustomItemUtil.getItemStackFromUUID(r.craftedInUUID()));
						event.getWhoClicked().openInventory(inv);
					} else {
						event.getWhoClicked().closeInventory();
						event.getWhoClicked().openInventory(getErrorInventory("Unable to get recipe gui"));
					}
				}
			} else {
				if (data.equalsIgnoreCase("MNEXT")) {
					List<String> array = Methods.convertSetIntoList(getNames(CustomFeatureLoader.getLoader().getHandler().getCustomRecipes()));
					Collections.sort(array);
					set(event.getInventory(), array);
				} else if (data.equalsIgnoreCase("MBACK")) {
					List<String> array = Methods.convertSetIntoList(getNames(CustomFeatureLoader.getLoader().getHandler().getCustomRecipes()));
					Collections.sort(array);
					set2(event.getInventory(), array);
				} else if (data.equalsIgnoreCase("MCLOSE")) {
					openBase(event.getWhoClicked());
				}
			}
			event.setCancelled(true);
		} else if (event.getView().getTitle().equalsIgnoreCase("§6Machines : Error")) {
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
		} else {
			return;
		}
	}
	
	public void set(Inventory inv, List<String> list) {
		int[] ints = {10,11,12,13,14,15,16,19,20,21,22,23,24,25,28,29,30,31,32,33,34,37,38,39,40,41,42,43};
		if (inv.firstEmpty()==-1) {
			ItemStack is = inv.getItem(43);
			if (is==null) return;
			String id = CustomItemUtil.getUUIDFromItemStack(is);
			if (id==null) return;
			int i = 0;
			for (int in = 0; i < list.size(); in++) {
				if (list.get(in).equalsIgnoreCase(id)) {
					i = in;
					break;
				}
			}
			int i2 = (i==0) ? 0 : i+1;
			for (int i3 : ints) {
				try {
					inv.setItem(i3, (CustomItemUtil.getItemStackFromUUID(list.get(i2))));
				} catch (Exception ignore) {
					inv.setItem(i3, (CustomItemUtil.getItemStackFromUUID(CustomInventoryListener.getUnclickableItemUUID())));
				}
				i2++;
			}
		} else {
			int i = 0;
			for (int i2 : ints) {
				try {
					inv.setItem(i2, (CustomItemUtil.getItemStackFromUUID(list.get(i))));
				} catch (Exception ignore) {
					inv.setItem(i2, (CustomItemUtil.getItemStackFromUUID(CustomInventoryListener.getUnclickableItemUUID())));
				}
				i++;
			}
		}
	}
	
	public void set2(Inventory inv, List<String> list) {
		int[] ints = {10,11,12,13,14,15,16,19,20,21,22,23,24,25,28,29,30,31,32,33,34,37,38,39,40,41,42,43};
		if (inv.firstEmpty()==-1) {
			ItemStack is = inv.getItem(10);
			if (is==null) return;
			String id = CustomItemUtil.getUUIDFromItemStack(is);
			if (id==null) return;
			int i = 0;
			for (int in = 0; i < list.size(); in++) {
				if (list.get(in).equalsIgnoreCase(id)) {
					i = in;
					break;
				}
			}
			int i2 = (i==0) ? 0 : i-28;
			for (int i3 : ints) {
				try {
					inv.setItem(i3, (CustomItemUtil.getItemStackFromUUID(list.get(i2))));
				} catch (Exception ignore) {
					inv.setItem(i3, (CustomItemUtil.getItemStackFromUUID(CustomInventoryListener.getUnclickableItemUUID())));
				}
				i2++;
			}
		} else {
			int i = 0;
			for (int i2 : ints) {
				try {
					inv.setItem(i2, (CustomItemUtil.getItemStackFromUUID(list.get(i))));
				} catch (Exception ignore) {
					inv.setItem(i2, (CustomItemUtil.getItemStackFromUUID(CustomInventoryListener.getUnclickableItemUUID())));
				}
				i++;
			}
		}
	}
	
	public Inventory setRecipe(CustomRecipe r) {
		CustomItem c = CustomItemUtil.getCustomItemFromUUID(r.craftedInUUID());
		if (c==null) return null; // TODO: open an error gui
		if (c instanceof Recipeable) {
			return ((Recipeable)c).getCraftingExample(r);
		} else {
			return getErrorInventory("Unable to get recipe gui");
		}
	}
	
	public static Inventory getErrorInventory(String info) {
		Inventory inv = Bukkit.createInventory(null, 3*9, "§6Machines : Error");
		for (int i = 0; i < 3*9; i++)
			inv.setItem(i, CustomItemUtil.getItemStackFromUUID(CustomInventoryListener.getUnclickableItemUUID()));
		inv.setItem(4, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayName("§c§lAn error has occured").addLoreLine("§bInformation:").addLoreLine("§b"+info).addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING).build());
		inv.setItem(22, Recipeable.closeItem);
		return inv;
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
	
	public Set<String> getNames2(Set<CustomItem> ci) {
		Set<String> set = new HashSet<String>();
		for (CustomItem c : ci) {
			set.add(ChatColor.stripColor(c.getUUID()));
		}
		return set;
	}
	
	public Set<CustomRecipe> getRecipesOfUUID(String id) {
		Set<CustomRecipe> set = new HashSet<CustomRecipe>();
		for (CustomRecipe cr : CustomFeatureLoader.getLoader().getHandler().getCustomRecipes()) {
			if (cr.getResultUUID().equalsIgnoreCase(id)) {
				set.add(cr);
			}
		}
		return set;
	}
	
	public static List<CustomRecipe> getRecipesOfUUIDInOrderedList(String id) {
		List<String> list = new ArrayList<>();
		List<CustomRecipe> list2 = new ArrayList<>();
		for (CustomRecipe cr : CustomFeatureLoader.getLoader().getHandler().getCustomRecipes()) {
			if (cr.getResultUUID().equalsIgnoreCase(id)) {
				list.add(cr.getClass().getName());
			}
		}
		Collections.sort(list);
		for (String s : list) {
			for (CustomRecipe cr : CustomFeatureLoader.getLoader().getHandler().getCustomRecipes()) {
				if (cr.getClass().getName().equals(s)) {
					list2.add(cr);
				}
			}
		}
		return list2;
	}
}
