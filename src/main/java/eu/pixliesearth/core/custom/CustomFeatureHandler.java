package eu.pixliesearth.core.custom;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.filoghost.holographicdisplays.api.Hologram;

import eu.pixliesearth.core.files.DataFile;
import eu.pixliesearth.core.files.FileBase;
import eu.pixliesearth.core.files.FileDirectory;
import eu.pixliesearth.core.files.JSONFile;
import eu.pixliesearth.core.vendors.Vendor;
import eu.pixliesearth.utils.Timer;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

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
	private @Getter final Map<Material, List<BlockDrop>> dropMap;
	private @Getter final Map<String, Vendor> vendorMap;
	/**
	 *  Allows to get an item from a CustomItem instance
	 */
	private final HashMap<CustomItem, ItemStack> customItemsToItemStackMap;
	private final HashMap<Location, String> locationToUUIDMap;
	private final HashMap<Location, Integer> locationToEventMap;
	private final HashMap<Location, Inventory> locationToInventoryMap;
	private final HashMap<Location, Timer> locationToTimerMap;
	private final HashMap<Location, Hologram> locationToHologramMap;
	private final HashMap<Location, Double> locationToPowerMap;
	/**
	 * The instance of {@link CustomFeatureLoader} that initiated this class
	 */
	private @Getter @Setter CustomFeatureLoader loader;
	/**
	 * Initiates a new {@link CustomFeatureHandler} based of the {@link CustomFeatureLoader} provided
	 * 
	 * @param cfl The {@link CustomFeatureLoader} that is trying to initiate this class
	 */
	@SuppressWarnings("deprecation")
	public CustomFeatureHandler(CustomFeatureLoader cfl) {
		setLoader(cfl);
		this.customItems = new HashSet<CustomItem>();
		this.customListeners = new HashSet<Listener>();
		this.tickables = new HashSet<Tickable>();
		this.customCommands = new HashSet<CustomCommand>();
		this.customPermissions = new HashSet<CustomPermission>();
		this.customBlocks = new HashSet<CustomBlock>();
		this.customItemsToItemStackMap = new HashMap<CustomItem, ItemStack>();
		this.locationToUUIDMap = new HashMap<Location, String>();
		this.customQuests = new HashSet<CustomQuest>();
		this.locationToEventMap = new HashMap<Location, Integer>();
		this.customMachines = new HashSet<CustomMachine>();
		this.locationToInventoryMap = new HashMap<Location, Inventory>();
		this.locationToTimerMap = new HashMap<Location, Timer>();
		this.locationToHologramMap = new HashMap<Location, Hologram>();
		this.locationToPowerMap = new HashMap<Location, Double>();

		this.dropMap = new HashMap<>();

		this.customRecipes = new HashSet<CustomRecipe>();

		this.vendorMap = new HashMap<>();
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(getInstance(), new Runnable() {
			@SneakyThrows
			@Override
			public void run() {
				Bukkit.getScheduler().scheduleAsyncRepeatingTask(getInstance(), new Runnable() {
					@SneakyThrows
					@Override
					public void run() {
						for (Tickable t : getTickables()) 
							t.onTick();
					}
				}, 1L, 1L);
			}
		}, 	400L);
		
		loadCustomBlockTickable();
		loadMachineTickable();
	}
	
	public void loadCustomBlockTickable() {
		registerTickable(new Tickable() {
			@Override
			public void onTick() {
				for (Entry<Location, String> entry : locationToUUIDMap.entrySet())
					getCustomBlockFromLocation(entry.getKey()).onTick(entry.getKey());
			}
		});
	}
	
	public void loadMachineTickable() {
		registerTickable(new Tickable() {
			@Override
			public void onTick() {
				for (Entry<Location, String> entry : locationToUUIDMap.entrySet())
					if (getCustomBlockFromLocation(entry.getKey()) instanceof CustomMachine) 
						((CustomMachine)getCustomBlockFromLocation(entry.getKey())).onTick(entry.getKey(), getInventoryFromLocation(entry.getKey()), getTimerFromLocation(entry.getKey()));
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
		// System.out.println("Registered the custom block "+customBlock.getUUID());
	}
	// TODO: notes
	public void registerMachine(CustomMachine customMachine) {
		this.customMachines.add(customMachine);
		this.customBlocks.add(customMachine);
		this.customItems.add(customMachine);
		this.customItemsToItemStackMap.put(customMachine, customMachine.buildItem());
		System.out.println("Registered the custom machine "+customMachine.getUUID());
	}
	/**
	 * Registers the command
	 * 
	 * @param customCommand The {@link CustomCommand} instance to register 
	 */
	public void registerCommand(CustomCommand customCommand) {
		this.customCommands.add(customCommand);
		// System.out.println("Registered the command "+customCommand.getName());
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
		return this.customItemsToItemStackMap.get(customItem).clone();
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
	// TODO: notes
	public void saveMachinesToFiles() {
		int i = 0;
		for (Entry<Location, String> entry : this.locationToUUIDMap.entrySet()) {
			if (getCustomBlockFromLocation(entry.getKey()) instanceof CustomMachine) {
				try {
					DataFile f = new DataFile(getInstance().getDataFolder().getAbsolutePath()+"/customblocks/machines/", Integer.toString(i));
					if (((CustomMachine)getCustomBlockFromLocation(entry.getKey())).getSaveData(entry.getKey(), getInventoryFromLocation(entry.getKey()), getTimerFromLocation(entry.getKey()))==null) {
						Map<String, String> map = new HashMap<String, String>();
						map.put("MUUID", getCustomBlockFromLocation(entry.getKey()).getUUID());
						map.put("MLOCATION", entry.getKey().getWorld().getUID().toString()+":"+entry.getKey().getX()+":"+entry.getKey().getY()+":"+entry.getKey().getZ());
						f.saveDataMap(map);
					} else {
						Map<String, String> map = ((CustomMachine)getCustomBlockFromLocation(entry.getKey())).getSaveData(entry.getKey(), getInventoryFromLocation(entry.getKey()), getTimerFromLocation(entry.getKey()));
						map.put("MUUID", getCustomBlockFromLocation(entry.getKey()).getUUID());
						map.put("MLOCATION", entry.getKey().getWorld().getUID().toString()+":"+entry.getKey().getX()+":"+entry.getKey().getY()+":"+entry.getKey().getZ());
						f.saveDataMap(map);
					}
					f.saveDataMap(((CustomMachine)getCustomBlockFromLocation(entry.getKey())).getSaveData(entry.getKey(), getInventoryFromLocation(entry.getKey()), getTimerFromLocation(entry.getKey())));
				} catch (IOException e) {
					System.err.println("Unable to save the machine "+getCustomBlockFromLocation(entry.getKey()).getUUID()+" due to a IOException");
				} finally {
					i++;
				}
			}
		}
	}
	// TODO: notes
	public void loadMachinesFromFiles() {
		FileDirectory d = new FileDirectory(getInstance().getDataFolder().getAbsolutePath()+"/customblocks/"+"/machines/");
		for (FileBase f : d.getFilesInDirectory()) {
			DataFile df = new DataFile(f.getFilePath(), f.getFileName(), f.getFileExtension());
			try {
				Map<String, String> map = df.loadDataMap();
				String[] l2 = map.get("MLOCATION").split(":");
				Location location = new Location(Bukkit.getWorld(UUID.fromString(l2[0])), Double.parseDouble(l2[1]), Double.parseDouble(l2[2]), Double.parseDouble(l2[3]));
				for (CustomMachine m : getCustomMachines())
					if (m.getUUID().equalsIgnoreCase(map.get("MUUID"))) {
						Inventory i = m.getInventory();
						this.locationToHologramMap.put(location, CustomMachine.createHologram(m.getDefaultDisplayName(), location));
						this.locationToInventoryMap.put(location, i);
						m.loadFromSaveData(i, location, map);
					}
			} catch (IOException e) {
				System.err.println("Unable to load a machine due to a IOException");
			} finally {
				df.deleteFile();
			}
		}
	}
	/**
	 * Loads custom blocks from the save file
	 */
	public void loadCustomBlocksFromFile() {
		JSONFile f = new JSONFile(getInstance().getDataFolder().getAbsolutePath()+"/customblocks/", "customblocks");
		for (String l : f.keySet()) {
			String[] l2 = l.split(":");
			this.locationToUUIDMap.put(new Location(Bukkit.getWorld(UUID.fromString(l2[0])), Double.parseDouble(l2[1]), Double.parseDouble(l2[2]), Double.parseDouble(l2[3])), f.get(l));
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
		if (getCustomItemFromUUID(id) instanceof CustomGeneratorMachine) {
			CustomMachine m = (CustomMachine) getCustomItemFromUUID(id);
			this.locationToHologramMap.put(location, CustomMachine.createHologram(m.getDefaultDisplayName(), location));
			this.locationToInventoryMap.put(location, m.getInventory());
			this.locationToPowerMap.put(location, 0D);
		} else if (getCustomItemFromUUID(id) instanceof CustomMachine) {
			CustomMachine m = (CustomMachine) getCustomItemFromUUID(id);
			this.locationToHologramMap.put(location, CustomMachine.createHologram(m.getDefaultDisplayName(), location));
			this.locationToInventoryMap.put(location, m.getInventory());
		} else {
			location.getWorld().getBlockAt(location).setType(getCustomItemFromUUID(id).getMaterial());
			location.getWorld().getBlockAt(location).getState().update();
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
	// TODO: notes
	public Hologram getHologramAtLocation(Location location) {
		return this.locationToHologramMap.get(location);
	}
	/**
	 * Called when a custom block at the location has been broken
	 * 
	 * @param location The {@link Location} of the block that has been broken
	 */
	public void removeCustomBlockFromLocation(Location location) {
		if (getCustomBlockFromLocation(location) instanceof CustomMachine) {
			Hologram h = getHologramAtLocation(location);
			Hologram h2 = getHologramAtLocation(location);
			if (h!=null)
				h.delete();
			if (h2!=null)
				h2.delete();
			this.locationToInventoryMap.remove(location);
			this.locationToTimerMap.remove(location);
			this.locationToHologramMap.remove(location);
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
}
