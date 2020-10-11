package eu.pixliesearth.core.custom;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import eu.pixliesearth.core.files.JSONFile;
import eu.pixliesearth.utils.ThreadUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import javax.print.attribute.HashAttributeSet;

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
	
	private @Getter Set<CustomItem> customItems;
	private @Getter Set<CustomRecipe> customRecipes;
	private @Getter Set<Listener> customListeners;
	private @Getter Set<CustomCommand> customCommands;
	private @Getter Set<CustomPermission> customPermissions;
	private @Getter Set<CustomBlock> customBlocks;
	private @Getter Set<Tickable> tickables;
	private @Getter Set<CustomMachineRecipe> customMachineRecipes;
	private @Getter Set<CustomQuest> customQuests;
	/**
	 *  Allows to get an item from a CustomItem instance
	 */
	private HashMap<CustomItem, ItemStack> customItemsToItemStackMap;
	private HashMap<Location, String> locationToUUIDMap;
	/**
	 * The instance of {@link CustomFeatureLoader} that initiated this class
	 */
	private @Getter @Setter CustomFeatureLoader loader;
	/**
	 * Initiates a new {@link CustomFeatureHandler} based of the {@link CustomFeatureLoader} provided
	 * 
	 * @param cfl The {@link CustomFeatureLoader} that is trying to initiate this class
	 */
	public CustomFeatureHandler(CustomFeatureLoader cfl) {
		setLoader(cfl);
		this.customItems = new HashSet<CustomItem>();
		this.customRecipes = new HashSet<CustomRecipe>();
		this.customListeners = new HashSet<Listener>();
		this.tickables = new HashSet<Tickable>();
		this.customCommands = new HashSet<CustomCommand>();
		this.customPermissions = new HashSet<CustomPermission>();
		this.customBlocks = new HashSet<CustomBlock>();
		this.customItemsToItemStackMap = new HashMap<CustomItem, ItemStack>();
		this.locationToUUIDMap = new HashMap<Location, String>();
		this.customMachineRecipes = new HashSet<CustomMachineRecipe>();
		this.customQuests = new HashSet<CustomQuest>();
		new ThreadUtils(new Runnable() {
			@SneakyThrows
			@Override
			public void run() {
				while (true) {
					for (Tickable t : getTickables()) 
						t.onTick();
					Thread.sleep(50);
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
		System.out.println("Registered the listener "+listener.getClass().getName());
	}
	/**
	 * Adds a {@link CustomItem} to {@link CustomFeatureHandler#customItems}
	 * 
	 * @param customitem The {@link CustomItem} to be registered
	 */
	public void registerItem(CustomItem customitem) {
		this.customItems.add(customitem);
		this.customItemsToItemStackMap.put(customitem, customitem.buildItem());
		System.out.println("Registered the item "+customitem.getUUID());
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
		System.out.println("Registered the custom block "+customBlock.getUUID());
	}
	/**
	 * Adds a {@link CustomRecipe} to {@link CustomFeatureHandler#customRecipes}
	 * 
	 * @param customRecipe The {@link CustomRecipe} to be registered
	 */
	public void registerRecipe(CustomRecipe customRecipe) {
		this.customRecipes.add(customRecipe);
		System.out.println("Registered the recipe "+customRecipe.getRecipeName());
	}
	/**
	 * Registers the command
	 * 
	 * @param customCommand The {@link CustomCommand} instance to register 
	 */
	public void registerCommand(CustomCommand customCommand) {
		this.customCommands.add(customCommand);
		System.out.println("Registered the command "+customCommand.getName());
	}
	/**
	 * Adds a {@link Tickable} to be called every 1000ms
	 * 
	 * @param tickable The {@link Tickable} instance
	 */
	public void registerTickable(Tickable tickable) {
		this.tickables.add(tickable);
		System.out.println("Registered the tickable "+tickable.getClass().getName());
	}
	//TODO: notes
	public void registerPermission(CustomPermission customPermission) {
		this.customPermissions.add(customPermission);
		System.out.println("Registered the permission "+customPermission.getName());
	}
	//TODO: notes
	public void registerCustomMachineRecipe(CustomMachineRecipe customMachineRecipe) {
		
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
	 * Disables a {@link CustomRecipe} based on its class
	 * 
	 * @param clazz The {@link CustomRecipe}'s class
	 */
	public void disableRecipe(Class<? extends CustomRecipe> clazz) {
		for (CustomRecipe r : getCustomRecipes()) 
			if (r.getClass().equals(clazz)) {
				Bukkit.removeRecipe(r.getNamespacedKey(getInstance()));
				this.customRecipes.remove(r);
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
	//TODO: notes
	public CustomItem getCustomItemFromClass(Class<? extends CustomItem> c) {
		for (CustomItem i : getCustomItems()) 
			if (i.getClass().equals(c)) 
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
	//TODO: notes
	public ItemStack getItemStackFromCustomItem(CustomItem c) {
		return this.customItemsToItemStackMap.get(c).clone();
	}
	//TODO: notes
	public Class<? extends CustomItem> getClassFromItemStack(ItemStack i) {
		return getClassFromCustomItem(getCustomItemFromItemStack(i));
	}
	//TODO: notes
	public ItemStack getItemStackFromClass(Class<? extends CustomItem> c) {
		return getItemStackFromCustomItem(getCustomItemFromClass(c));
	}
	//TODO: notes
	public CustomItem getCustomItemFromUUID(String id) {
		for (CustomItem i : this.customItems) 
			if (i.getUUID().equals(id)) 
				return i;
		return null;
	}
	//TODO: notes
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
		location.getWorld().getBlockAt(location).setType(getCustomItemFromUUID(id).getMaterial());
		this.locationToUUIDMap.put(location, id);
	}
	// TODO: notes
	public void removeCustomBlockFromLocation(Location location) {
		this.locationToUUIDMap.remove(location);
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

	public void registerQuest(CustomQuest quest) {
		customQuests.add(quest);
	}

}