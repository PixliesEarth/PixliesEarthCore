package eu.pixliesearth.core.custom;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import eu.pixliesearth.guns.CustomGun;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import eu.pixliesearth.core.custom.interfaces.IConsumable;
import eu.pixliesearth.core.custom.interfaces.IRedstoneable;
import eu.pixliesearth.core.custom.interfaces.Tickable;
import eu.pixliesearth.core.custom.skills.SimpleSkill;
import eu.pixliesearth.core.custom.skills.SkillHandler;
import eu.pixliesearth.core.files.FileBase;
import eu.pixliesearth.core.files.JSONFile;
import eu.pixliesearth.core.vendors.Vendor;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.InventoryUtils;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.NBTTagType;
import eu.pixliesearth.utils.Timer;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author BradBot_1
 * 
 * <h3>Handles anything custom!</h3>
 *
 * @apiNote Do not initiate this class directly! do it threw creating a new instance of {@link CustomFeatureLoader}
 * 
 */
public class CustomFeatureHandler {
	
	private @Getter final Set<CustomItem> customItems;
	private @Getter final Set<Listener> customListeners;
	private @Getter final Set<CustomCommand> customCommands;
	private @Getter final Set<CustomPermission> customPermissions;
	private @Getter final Set<CustomBlock> customBlocks;
	private @Getter final Set<Tickable> tickables;
	private @Getter final Set<CustomRecipe> customRecipes;
	private @Getter final Set<CustomQuest> customQuests;
	private @Getter final Set<CustomMachine> customMachines;
	@Deprecated private @Getter final Map<Material, List<BlockDrop>> dropMap;
	private @Getter final Map<String, Vendor> vendorMap;
	/**
	 *  Allows to get an item from a CustomItem instance
	 */
	private final Map<CustomItem, ItemStack> customItemsToItemStackMap;
	private final Map<Location, String> locationToUUIDMap;
	private final Map<Location, Integer> locationToEventMap;
	private final Map<Location, Inventory> locationToInventoryMap;
	private final Map<Location, Timer> locationToTimerMap;
	private final Map<Location, Double> locationToPowerMap;
	private final Map<Location, Double> locationToTempratureMap;
	private final Map<Location, UUID> locationToPrivateMap; // Used to lock things to a player
	/**
	 * The instance of {@link CustomFeatureLoader} that initiated this class
	 */
	private @Getter @Setter CustomFeatureLoader loader;
	
	private @Getter final Map<CustomItem.Category, List<String>> categoriesForItems;
	private @Getter final Map<String, CustomItem.Category> itemsForCategories;

	/**
	 * Initiates a new {@link CustomFeatureHandler} based of the {@link CustomFeatureLoader} provided
	 * 
	 * @param cfl The {@link CustomFeatureLoader} that is trying to initiate this class
	 */
	public CustomFeatureHandler(CustomFeatureLoader cfl) {
		setLoader(cfl);
		new CustomLiquidHandler(cfl, this);
		this.customItems = new HashSet<CustomItem>();
		this.customListeners = new HashSet<Listener>();
		this.tickables = new HashSet<Tickable>();
		this.customCommands = new HashSet<CustomCommand>();
		this.customPermissions = new HashSet<CustomPermission>();
		this.customBlocks = new HashSet<CustomBlock>();
		this.customItemsToItemStackMap = new ConcurrentHashMap<CustomItem, ItemStack>();
		this.locationToUUIDMap = new ConcurrentHashMap<Location, String>();
		this.customQuests = new HashSet<CustomQuest>();
		this.locationToEventMap = new ConcurrentHashMap<Location, Integer>();
		this.customMachines = new HashSet<CustomMachine>();
		this.locationToInventoryMap = new ConcurrentHashMap<Location, Inventory>();
		this.locationToTimerMap = new ConcurrentHashMap<Location, Timer>();
		this.locationToPowerMap = new ConcurrentHashMap<Location, Double>();
		this.locationToPrivateMap = new HashMap<Location, UUID>();
		this.locationToTempratureMap = new HashMap<Location, Double>();

		categoriesForItems = new HashMap<>();
		itemsForCategories = new HashMap<>();

		this.dropMap = new HashMap<>();

		this.customRecipes = new HashSet<CustomRecipe>();

		this.vendorMap = new HashMap<>();
		
		new BukkitRunnable() {

			@Override
			public void run() {
				System.out.println("Machine tickable enabled!");
				new BukkitRunnable() {
					 @Override
					 public void run() {
						 for (Tickable t : getTickables()) {
							 if (t==null) continue;
							 t.onTick();
						 }
			         }
			     }.runTaskTimerAsynchronously(getInstance(), 1L, 1L);
			}
		}.runTaskLater(getInstance(), 400L);
		
		loadCustomBlockTickable();
		loadMachineTickable();
	}
	
	public void registerSkills() {
		SkillHandler skillHandler = SkillHandler.getSkillHandler();
		skillHandler.registerSkill(new SimpleSkill("Lumbering"));//
		skillHandler.registerSkill(new SimpleSkill("Mining"));//
		skillHandler.registerSkill(new SimpleSkill("Building"));//
		skillHandler.registerSkill(new SimpleSkill("Traveling"));//
		skillHandler.registerSkill(new SimpleSkill("Farming"));//
		skillHandler.registerSkill(new SimpleSkill("Hunting"));//
		skillHandler.registerSkill(new SimpleSkill("Enchanting"));//
		skillHandler.registerSkill(new SimpleSkill("Fishing"));//
		skillHandler.registerSkill(new SimpleSkill("Taming"));//
		skillHandler.registerSkill(new SimpleSkill("Questing"));
		System.out.println("Registered skills");
	}
	
	
	public void loadIRedstoneableTickable() {
		registerTickable(new Tickable() {
			@Override
			public void onTick() {
				for (Entry<Location, String> entry : locationToUUIDMap.entrySet()) {
					CustomBlock cb = getCustomBlockFromLocation(entry.getKey());
					if (cb instanceof IRedstoneable) {
						if (entry.getKey().getBlock().isBlockPowered() || entry.getKey().getBlock().isBlockIndirectlyPowered()) {
							((IRedstoneable) cb).onRecievedRedstoneSignal(entry.getKey());
						}
					}
				}
			}
		});
	}
	
	public void loadCustomBlockTickable() {
		registerTickable(new Tickable() {
			@Override
			public void onTick() {
				for (Entry<Location, String> entry : locationToUUIDMap.entrySet()) {
					CustomBlock cb = getCustomBlockFromLocation(entry.getKey());
					if (cb == null) {
						locationToUUIDMap.remove(entry.getKey());
						continue;
					}
					cb.onTick(entry.getKey());
				}
			}
		});
	}
	
	public void loadMachineTickable() {
		registerTickable(new Tickable() {
			@Override
			public void onTick() {
				for (Entry<Location, String> entry : locationToUUIDMap.entrySet()) {
					if (getCustomBlockFromLocation(entry.getKey()) instanceof CustomMachine) {
						if (getInventoryFromLocation(entry.getKey())==null) {
							Inventory inv = ((CustomMachine)getCustomBlockFromLocation(entry.getKey())).getInventory();
							if (inv!=null) {
								setInventoryFromLocation(entry.getKey(), inv);
							}
						}
						try {
							((CustomMachine)getCustomBlockFromLocation(entry.getKey())).onTick(entry.getKey(), getInventoryFromLocation(entry.getKey()), getTimerFromLocation(entry.getKey()));
						} catch (Exception ignore) {
							
						}
					}
				}
			}
		});
	}
	/**
	 * Gets and returns the instance of {@link JavaPlugin} saved in {@link CustomFeatureHandler#getLoader()}
	 * 
	 * @return The instance of {@link JavaPlugin} saved in {@link CustomFeatureHandler#getLoader()}
	 */
	public JavaPlugin getInstance() {
		return getLoader().getInstance();
	}
	/**
	 * Adds a {@link Listener} to {@link CustomFeatureHandler#customListeners}
	 * 
	 * @param listener The {@link Listener} to be registered
	 */
	public void registerListener(Listener listener) {
		this.customListeners.add(listener);
		// System.out.println("Registered the listener "+listener.getClass().getName());
	}
	/**
	 * 
	 */
	public void registerRecipe(CustomRecipe customRecipe) {
		this.customRecipes.add(customRecipe);
		// System.out.println("Registered the recipe "+customRecipe.getResultUUID());
	}
	/**
	 * Adds a {@link CustomItem} to {@link CustomFeatureHandler#customItems}
	 * 
	 * @param customitem The {@link CustomItem} to be registered
	 */
	public void registerItem(CustomItem customitem) {
		this.customItems.add(customitem);
		this.customItemsToItemStackMap.put(customitem, customitem.buildItem());
		if (customitem.getUUID().contains("_Pickaxe") || customitem.getUUID().contains("_Axe") || customitem.getUUID().contains("_Shovel") || customitem.getUUID().contains("_Hoe")) {
			categoriesForItems.putIfAbsent(CustomItem.Category.TOOLS, new ArrayList<>());
			categoriesForItems.get(CustomItem.Category.TOOLS).add(customitem.getUUID());
			itemsForCategories.put(customitem.getUUID(), CustomItem.Category.TOOLS);
		} else if (customitem.getUUID().contains("_Dust") || customitem.getUUID().contains("_Ingot") || customitem.getUUID().contains("Plastic_") || customitem.getUUID().contains("Chunk")) {
			categoriesForItems.putIfAbsent(CustomItem.Category.MATERIAL, new ArrayList<>());
			categoriesForItems.get(CustomItem.Category.MATERIAL).add(customitem.getUUID());
			itemsForCategories.put(customitem.getUUID(), CustomItem.Category.MATERIAL);
		} else if (customitem instanceof CustomWeapon || customitem instanceof CustomGun) {
			categoriesForItems.putIfAbsent(CustomItem.Category.WEAPONS, new ArrayList<>());
			categoriesForItems.get(CustomItem.Category.WEAPONS).add(customitem.getUUID());
			itemsForCategories.put(customitem.getUUID(), CustomItem.Category.WEAPONS);
		} else if (customitem instanceof CustomArmour) {
			categoriesForItems.putIfAbsent(CustomItem.Category.ARMOR, new ArrayList<>());
			categoriesForItems.get(CustomItem.Category.ARMOR).add(customitem.getUUID());
			itemsForCategories.put(customitem.getUUID(), CustomItem.Category.ARMOR);
		} else if (customitem.getUUID().startsWith("Food:") || customitem instanceof IConsumable) {
			categoriesForItems.putIfAbsent(CustomItem.Category.FOOD, new ArrayList<>());
			categoriesForItems.get(CustomItem.Category.FOOD).add(customitem.getUUID());
			itemsForCategories.put(customitem.getUUID(), CustomItem.Category.FOOD);
		} else {
			categoriesForItems.putIfAbsent(CustomItem.Category.ITEMS, new ArrayList<>());
			categoriesForItems.get(CustomItem.Category.ITEMS).add(customitem.getUUID());
			itemsForCategories.put(customitem.getUUID(), CustomItem.Category.ITEMS);
		}
		// System.out.println("Registered the item "+customitem.getUUID());
	}
	/**
	 * Registers the {@link CustomBlock} provided
	 * 
	 * @param customBlock The {@link CustomBlock} to register
	 */
	public void registerBlock(CustomBlock customBlock) {
		this.customBlocks.add(customBlock);
		this.customItems.add(customBlock);
		this.customItemsToItemStackMap.put(customBlock, customBlock.buildItem());
		categoriesForItems.putIfAbsent(CustomItem.Category.BLOCKS, new ArrayList<>());
		categoriesForItems.get(CustomItem.Category.BLOCKS).add(customBlock.getUUID());
		// System.out.println("Registered the custom block "+customBlock.getUUID());
	}
	/**
	 * Registers the {@link CustomMachine} provided
	 * 
	 * @param customMachine The {@link CustomMachine} to register
	 */
	public void registerMachine(CustomMachine customMachine) {
		this.customMachines.add(customMachine);
		this.customBlocks.add(customMachine);
		this.customItems.add(customMachine);
		this.customItemsToItemStackMap.put(customMachine, customMachine.buildItem());
		categoriesForItems.putIfAbsent(CustomItem.Category.MACHINES, new ArrayList<>());
		categoriesForItems.get(CustomItem.Category.MACHINES).add(customMachine.getUUID());
		// System.out.println("Registered the custom machine "+customMachine.getUUID());
	}
	/**
	 * Registers the command
	 * 
	 * @param customCommand The {@link CustomCommand} instance to register 
	 */
	public void registerCommand(CustomCommand customCommand) {
		this.customCommands.add(customCommand);
		// System.out.println("Registered the command "+customCommand.getCommandName());
	}
	/**
	 * Adds a {@link Tickable} to be called every 1000ms
	 * 
	 * @param tickable The {@link Tickable} instance
	 */
	public void registerTickable(Tickable tickable) {
		this.tickables.add(tickable);
		// System.out.println("Registered the tickable "+tickable.getClass().getName());
	}
	/**
	 * Registers the {@link CustomPermission} provided
	 * 
	 * @param customPermission
	 */
	public void registerPermission(CustomPermission customPermission) {
		this.customPermissions.add(customPermission);
		// System.out.println("Registered the permission "+customPermission.getName());
	}
	/**
	 * Registers the {@link CustomMachineRecipe} provided
	 * 
	 * @param customMachineRecipe The {@link CustomMachineRecipe} to register
	 * @deprecated This has not been implemented yet! I will work on adding it later on -BB1
	 */
	@Deprecated
	public void registerCustomMachineRecipe(CustomMachineRecipe customMachineRecipe) {
		
	}
	// TODO: notes
	public void registerLocationEvent(Location location, Integer eventid) {
		locationToEventMap.put(location, eventid);
	}
	// TODO: notes
	public Integer getLocationEvent(Location location) {
		return locationToEventMap.get(location);
	}
	// TODO: notes
	public void unregisterLocationEvent(Location location) {
		locationToEventMap.remove(location);
	}
	// TODO: notes
	public Timer getTimerFromLocation(Location location) {
		return this.locationToTimerMap.get(location);
	}
	/**
	 * Disables a {@link Listener} based on its class
	 * 
	 * @param clazz The {@link Listener}'s class
	 */
	public void disableListener(Class<?> clazz) {
		for (Listener l : getCustomListeners()) 
			if (l.getClass().equals(clazz)) {
				HandlerList.unregisterAll(l);
				this.customListeners.remove(l);
				break;
			}
	}
	/**
	 * Removes a permission from the game
	 * 
	 * @param clazz The class of the permission
	 */
	public void disableCustomPermission(Class<? extends CustomPermission> clazz) {
		for (CustomPermission c : getCustomPermissions()) 
			if (c.getClass().equals(clazz)) {
				getLoader().getInstance().getServer().getPluginManager().removePermission(c.getAsRegisterablePermission());
				this.customPermissions.remove(c);
			}
	}
	/**
	 * Gives the {@link Player} provided the {@link CustomItem} provided
	 * 
	 * @param p The {@link Player} that the item will be given to
	 * @param c The {@link CustomItem} class that the item will be gotten from
	 */
	public void giveCustomItem(Player p, Class<? extends CustomItem> c) {
		p.getInventory().addItem(getItemStackFromClass(c));
	}
	/**
	 * Unregisters a {@link Tickable} instance so it is no longer called every 1000ms
	 * 
	 * @param tickable The {@link Tickable} instance
	 */
	public void unregisterTickable(Tickable tickable) {
		tickables.remove(tickable);
	}
	/**
	 * Uses the provided {@link Class<? extends CustomItem>} to get a corresponding {@link CustomItem}
	 * 
	 * @param clazz The {@link Class<? extends CustomItem>} of the {@link CustomItem}
	 * @return The corresponding {@link CustomItem}
	 */
	public CustomItem getCustomItemFromClass(Class<? extends CustomItem> clazz) {
		for (CustomItem i : getCustomItems()) 
			if (i.getClass().equals(clazz)) 
				return i;
		return null;
	}
	/**
	 * Gets and returns the {@link CustomItem} class
	 * 
	 * @param customItem The {@link CustomItem} to get the class from
	 * @return The {@link CustomItem}'s class
	 */
	public Class<? extends CustomItem> getClassFromCustomItem(CustomItem customItem) {
		return customItem.getClass();
	}
	/**
	 * Gets and returns the {@link CustomItem} correlated with the {@link ItemStack} provided
	 * 
	 * @param itemStack The {@link ItemStack} to get the {@link CustomItem} of
	 * @return The {@link ItemStack} as a {@link CustomItem} instance
	 */
	public CustomItem getCustomItemFromItemStack(ItemStack itemStack) {
		for (Entry<CustomItem, ItemStack> entry : this.customItemsToItemStackMap.entrySet()) 
			if (entry.getValue().isSimilar(itemStack)) 
				return entry.getKey();
		return null;
	}
	/**
	 * Gets a {@link CustomItem}'s {@link ItemStack}
	 * 
	 * @param customItem The {@link CustomItem} to get the {@link ItemStack} of
	 * @return The {@link ItemStack} of the {@link CustomItem}
	 */
	public ItemStack getItemStackFromCustomItem(CustomItem customItem) {
		return (customItem.isUnstackable()) ? new ItemBuilder(customItem.buildItem().clone()).addNBTTag("UNSTACKABLE", UUID.randomUUID().toString(), NBTTagType.STRING).build() : this.customItemsToItemStackMap.get(customItem).clone();
	}
	/**
	 * Inputs {@link CustomFeatureHandler#getCustomItemFromItemStack(ItemStack)} into {@link CustomFeatureHandler#getClassFromCustomItem(CustomItem)} and returns the result
	 * 
	 * @param itemStack The {@link ItemStack} to get the class of
	 * @return The result of {@link CustomFeatureHandler#getCustomItemFromItemStack(ItemStack)} inputed into {@link CustomFeatureHandler#getClassFromCustomItem(CustomItem)}
	 */
	public Class<? extends CustomItem> getClassFromItemStack(ItemStack itemStack) {
		return getClassFromCustomItem(getCustomItemFromItemStack(itemStack));
	}
	/**
	 * Inputs {@link CustomFeatureHandler#getCustomItemFromClass(Class)} into {@link CustomFeatureHandler#getItemStackFromCustomItem(CustomItem)} and returns the result
	 * 
	 * @param clazz The {@link Class<? extends CustomItem>} to get the corresponding {@link ItemStack} of
	 * @return The result of {@link CustomFeatureHandler#getCustomItemFromClass(Class)} being inputed into {@link CustomFeatureHandler#getItemStackFromCustomItem(CustomItem)}
	 */
	public ItemStack getItemStackFromClass(Class<? extends CustomItem> clazz) {
		return getItemStackFromCustomItem(getCustomItemFromClass(clazz));
	}
	/**
	 * Gets and returns a {@link CustomItem} with the same UUID as the one inputed
	 * 
	 * @param id The {@link CustomItem} UUID
	 * @return The {@link CustomItem} with the same UUID
	 */
	public CustomItem getCustomItemFromUUID(String id) {
		for (CustomItem i : this.customItems) 
			if (i.getUUID().equals(id)) 
				return i;
		return null;
	}
	/**
	 * Uses the provided UUID to get the corresponding {@link ItemStack}
	 * 
	 * @param id The {@link CustomItem}'s UUID to get the item from
	 * @return The corresponding {@link ItemStack}
	 */
	public ItemStack getItemStackFromUUID(String id) {
		for (CustomItem i : this.customItems) 
			if (i.getUUID().equals(id)) 
				return getItemStackFromCustomItem(i);
		return null;
	}
	/**
	 * Gets and returns the class of the custom item id provided
	 * 
	 * @param id The {@link CustomItem} id
	 * @return The {@link CustomItem}'s class
	 */
	public Class<? extends CustomItem> getClassFromUUID(String id) {
		for (CustomItem i : this.customItems) 
			if (i.getUUID().equals(id)) 
				return i.getClass();
		return null;
	}
	/**
	 * Saves custom blocks to the save file
	 */
	@Deprecated
	public void saveCustomBlocksToFile() {
		JSONFile f = new JSONFile(getInstance().getDataFolder().getAbsolutePath()+"/customblocks/", "customblocks"); // getInstance().getDataFolder().getAbsolutePath()+"/customblocks/"
		f.deleteFile();
		f = new JSONFile(getInstance().getDataFolder().getAbsolutePath()+"/customblocks/", "customblocks"); // getInstance().getDataFolder().getAbsolutePath()+"/customblocks/"
		for (Entry<Location, String> c : this.locationToUUIDMap.entrySet()) {
			Location l = c.getKey();
			f.put(l.getWorld().getUID().toString()+":"+l.getX()+":"+l.getY()+":"+l.getZ(), c.getValue());
		}
		f.saveJsonToFile();
	}
	
	// TEST SAVING SYSTEM
	
	public void saveCustomBlocksToFileOptimised() {
		System.out.println("Saving CustomBlocks to file!");
		try {
			FileBase fb = new FileBase(getInstance().getDataFolder().getAbsolutePath()+"/customblocks/", "customBlocks", ".txt");
			fb.clearFile();
			fb.writeToFile(InventoryUtils.serialize(locationToUUIDMap));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void saveMachinesToFileOptimised() {
		System.out.println("Saving CustomMachines data to file!");
		try {
			FileBase fb = new FileBase(getInstance().getDataFolder().getAbsolutePath()+"/customblocks/", "customMachines", ".txt");
			fb.clearFile();
			Map<Location, String> map = new HashMap<Location, String>();
			for (Entry<Location, String> entry : locationToUUIDMap.entrySet()) {
				try {
					if (getCustomBlockFromLocation(entry.getKey()) instanceof CustomMachine) {
						Map<String, String> map2 = ((CustomMachine) getCustomBlockFromLocation(entry.getKey())).getSaveData(entry.getKey(), getInventoryFromLocation(entry.getKey()), getTimerFromLocation(entry.getKey()));
						if (map2==null) {
							map2 = new HashMap<String, String>();
						}
						map2.put("MID", entry.getValue());
						Inventory inv = getInventoryFromLocation(entry.getKey());
						if (inv!=null) {
							map2.put("MINV", InventoryUtils.makeInventoryToString(inv));
						}
						map.put(entry.getKey(), InventoryUtils.serialize(map2));
					}
				} catch (Exception ignore) {
					System.out.println("Failed to save custom block "+entry.getValue());
				}
			}
			fb.writeToFile(InventoryUtils.serialize(map));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void saveSkillsToFile() {
		try {
			FileBase fb = new FileBase(getInstance().getDataFolder().getAbsolutePath()+"/", "skills", ".txt");
			fb.clearFile();
			fb.writeToFile(InventoryUtils.serialize(SkillHandler.getSkillHandler()));
		} catch (Exception ignore) {
			System.out.println("Failed to save skills");
		}
	}
	
	public void loadSkillsFromFile() {
		try {
			SkillHandler.setSkills((SkillHandler)InventoryUtils.deserialize(String.join("", new FileBase(getInstance().getDataFolder().getAbsolutePath()+"/", "skills", ".txt").loadFileIntoArray())));
		} catch (Exception ignore) {
			System.out.println("Failed to load skills");
		}
	}
	
	@SuppressWarnings("unchecked")
	public void loadMachinesFromFileOptimised() {
		try {
			FileBase fb = new FileBase(getInstance().getDataFolder().getAbsolutePath()+"/customblocks/", "customMachines", ".txt");
			Map<Location, String> map = (Map<Location, String>) InventoryUtils.deserialize(String.join("", fb.loadFileIntoArray()));
			for (Entry<Location, String> entry : map.entrySet()) {
				try {
					Map<String, String> map2 = (Map<String, String>) InventoryUtils.deserialize(entry.getValue());
					CustomItem ci = getCustomItemFromUUID(map2.get("MID"));
					if (entry.getValue()==null) System.out.println("Machine data is null");
					if (ci instanceof CustomMachine) {
						Inventory inv = ((CustomMachine) ci).getInventory();
						((CustomMachine) ci).loadFromSaveData(inv, entry.getKey(), map2);
						if (inv!=null) {
							String data2 = map2.get("MINV");
							if (data2!=null) {
								InventoryUtils.setInventoryContentsFromString(data2, inv);
							}
							locationToInventoryMap.put(entry.getKey(), inv);
						}
					}
				} catch (Exception ingore) {
					System.out.println("Failed to load custom block"+entry.getValue());
				}
			}
		} catch (Exception e) {
			System.out.println("Error loading machines");
		}
	}
	
	@SuppressWarnings("unchecked")
	public void loadCustomBlocksFromFileOptimised() {
		FileBase fb = new FileBase(getInstance().getDataFolder().getAbsolutePath()+"/customblocks/", "customBlocks", ".txt");
		try {
			this.locationToUUIDMap.putAll((Map<Location, String>) InventoryUtils.deserialize(String.join("", fb.loadFileIntoArray())));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	// TODO: notes
	public void registerTimer(Location location, Timer timer) {
		this.locationToTimerMap.put(location, timer);
	}
	// TODO: notes
	public void unregisterTimer(Location location) {
		this.locationToTimerMap.remove(location);
	}
	/**
	 * Gets and returns the {@link CustomBlock} at the {@link Location} provided
	 * 
	 * @param location The {@link Location} of the block
	 * @return The {@link CustomBlock} at the location 
	 */
	public CustomBlock getCustomBlockFromLocation(Location location) {
		String id = this.locationToUUIDMap.get(location);
		for (CustomBlock c : getCustomBlocks())
			if (c.getUUID().equals(id)) 
				return c;
		return null;
	}
	/**
	 * Sets the {@link CustomBlock} at the {@link Location} provided
	 * 
	 * @param location The {@link Location} of the block
	 * @param id The {@link CustomBlock}'s UUID
	 */
	public void setCustomBlockToLocation(Location location, String id) {
		if (id==null) { System.out.println("ID cannot be null"); return; }
		if (location==null || location.getBlockY()>256) return;
		if (getCustomItemFromUUID(id) instanceof CustomEnergyCrafterMachine) {
			CustomMachine m = (CustomMachine) getCustomItemFromUUID(id);
			Inventory i = m.getInventory();
			if (i!=null) 
				this.locationToInventoryMap.put(location, i);
			this.locationToPowerMap.put(location, 0D);
		} else if (getCustomItemFromUUID(id) instanceof CustomEnergyBlock) {
			CustomMachine m = (CustomMachine) getCustomItemFromUUID(id);
			Inventory i = m.getInventory();
			if (i!=null) 
				this.locationToInventoryMap.put(location, i);
			this.locationToPowerMap.put(location, 0D);
		} else if (getCustomItemFromUUID(id) instanceof CustomGeneratorMachine) {
			CustomMachine m = (CustomMachine) getCustomItemFromUUID(id);
			Inventory i = m.getInventory();
			if (i!=null) 
				this.locationToInventoryMap.put(location, i);
			this.locationToPowerMap.put(location, 0D);
		} else if (getCustomItemFromUUID(id) instanceof CustomMachine) {
			CustomMachine m = (CustomMachine) getCustomItemFromUUID(id);
			Inventory i = m.getInventory();
			if (i!=null) 
				this.locationToInventoryMap.put(location, i);
		} else {
			location.getBlock().setType(getCustomItemFromUUID(id).getMaterial());
			location.getBlock().getState().update();
		}
		this.locationToUUIDMap.put(location, id);
		// location.getBlock().setType(getCustomItemFromUUID(id).getMaterial());
	}
	/**
	 * Sets the {@link CustomBlock} at the {@link Location} provided
	 * 
	 * @param location The {@link Location} of the block
	 * @param id The {@link CustomBlock}'s UUID
	 */
	public void setCustomBlockToLocation(Location location, String id, boolean place) {
		if (place) {
			setCustomBlockToLocation(location, id);
			return;
		}
		if (location.getBlockY()>256) return;
		if (getCustomItemFromUUID(id) instanceof CustomEnergyCrafterMachine) {
			CustomMachine m = (CustomMachine) getCustomItemFromUUID(id);
			Inventory i = m.getInventory();
			if (i!=null) 
				this.locationToInventoryMap.put(location, i);
			this.locationToPowerMap.put(location, 0D);
		} else if (getCustomItemFromUUID(id) instanceof CustomEnergyBlock) {
			CustomMachine m = (CustomMachine) getCustomItemFromUUID(id);
			Inventory i = m.getInventory();
			if (i!=null) 
				this.locationToInventoryMap.put(location, i);
			this.locationToPowerMap.put(location, 0D);
		} else if (getCustomItemFromUUID(id) instanceof CustomGeneratorMachine) {
			CustomMachine m = (CustomMachine) getCustomItemFromUUID(id);
			Inventory i = m.getInventory();
			if (i!=null) 
				this.locationToInventoryMap.put(location, i);
			this.locationToPowerMap.put(location, 0D);
		} else if (getCustomItemFromUUID(id) instanceof CustomMachine) {
			CustomMachine m = (CustomMachine) getCustomItemFromUUID(id);
			Inventory i = m.getInventory();
			if (i!=null) 
				this.locationToInventoryMap.put(location, i);
		}
		this.locationToUUIDMap.put(location, id);
	}
	// TODO: notes
	public Inventory getInventoryFromLocation(Location location) {
		return this.locationToInventoryMap.get(location);
	}
	public void setInventoryFromLocation(Location location, Inventory inventory) {
		this.locationToInventoryMap.remove(location);
		this.locationToInventoryMap.put(location, inventory);
	}

	/**
	 * Called when a custom block at the location has been broken
	 * 
	 * @param location The {@link Location} of the block that has been broken
	 */
	public void removeCustomBlockFromLocation(Location location) {
		CustomBlock cb = getCustomBlockFromLocation(location);
		if (cb==null) return;
		if (cb instanceof CustomMachine) {
			this.locationToInventoryMap.remove(location);
			this.locationToTimerMap.remove(location);
			this.locationToPowerMap.remove(location);
			this.locationToPrivateMap.remove(location);
			this.locationToTempratureMap.remove(location);
			// TODO: drop inventory contents
		}
		location.getBlock().setType(MinecraftMaterial.AIR.getMaterial());
		this.locationToUUIDMap.remove(location);
	}
	// TODO: notes
	public Set<CustomRecipe> getRecipesFromUUID(String id) {
		Set<CustomRecipe> set = new HashSet<CustomRecipe>();
		for (CustomRecipe c : getCustomRecipes()) 
			if (c.craftedInUUID().equals(id)) 
				if (CustomItemUtil.getItemStackFromUUID(id)!=null) 
					set.add(c);
		return set;
	}
	/**
	 * Gets and returns if a {@link CustomBlock} is at the {@link Location} provided
	 * 
	 * @param location The {@link Location} of the block
	 * @return If the {@link CustomBlock} at the {@link Location} provided exists
	 */
	public boolean isCustomBlockAtLocation(Location location) {
		return getCustomBlockFromLocation(location)!=null;
	}
	/**
	 * Registers the {@link CustomQuest} provided
	 * 
	 * @param customQuest The {@link CustomQuest} to be registered
	 */
	public void registerQuest(CustomQuest customQuest) {
		customQuests.add(customQuest);
	}
	// TODO: notes
	public Double getPowerAtLocation(Location location) {
		return this.locationToPowerMap.get(location);
	}
	// TODO: notes
	public void addPowerToLocation(Location location, double amount) {
		Double d = getPowerAtLocation(location);
		if (d==null) {
			this.locationToPowerMap.put(location, amount);
		} else {
			this.locationToPowerMap.remove(location);
			this.locationToPowerMap.put(location, d+amount);
		}
	}
	// TODO: notes
	public void removePowerFromLocation(Location location, double amount) {
		addPowerToLocation(location, -amount);
	}
	// TODO: notes
	public void deletePowerFromLocation(Location location) {
		this.locationToPowerMap.remove(location);
	}
	// TODO: notes
	public void registerPrivateLocation(Location location, UUID uuid) {
		this.locationToPrivateMap.put(location, uuid);
	}
	// TODO: notes
	public void unregisterPrivateLocation(Location location) {
		this.locationToPrivateMap.remove(location);
	}
	// TODO: notes
	public UUID getPrivateLocation(Location location) {
		return this.locationToPrivateMap.get(location);
	}
	// TODO: notes
	public void addTempratureToLocation(Location location, double amount) {
		Double d = getTempratureAtLocation(location);
		if (d==null) {
			this.locationToTempratureMap.put(location, amount);
		} else {
			this.locationToTempratureMap.remove(location);
			this.locationToTempratureMap.put(location, d+amount);
		}
	}
	// TODO: notes
	public Double getTempratureAtLocation(Location location) {
		return this.locationToTempratureMap.get(location);
	}
	// TODO: notes
	public void removeTempratureFromLocation(Location location, double amount) {
		addTempratureToLocation(location, -amount);
	}
	// TODO: notes
	public void deleteTempratureFromLocation(Location location) {
		this.locationToTempratureMap.remove(location);
	}
	// TODO: notes
	public Set<Location> getLocationsOfCustomBlock(String UUID) {
		Set<Location> set = new HashSet<>();
		for (Entry<Location, String> entry : locationToUUIDMap.entrySet()) {
			if (entry.getValue().equals(UUID)) {
				set.add(entry.getKey());
			}
		}
		return set;
	}
	// TODO: notes
	public Set<Location> getLocationsOfCustomBlockInChunk(String UUID, Chunk chunk) {
		Set<Location> set = new HashSet<>();
		for (Entry<Location, String> entry : locationToUUIDMap.entrySet()) {
			if (entry.getValue().equals(UUID)) {
				if (entry.getKey().getWorld().getUID().equals(chunk.getWorld().getUID()) && entry.getKey().getChunk().getX()==chunk.getX() && entry.getKey().getChunk().getZ()==chunk.getZ()) {
					set.add(entry.getKey());
				}
			}
		}
		return set;
	}
}
