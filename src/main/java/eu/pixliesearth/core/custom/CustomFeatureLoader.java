package eu.pixliesearth.core.custom;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import eu.pixliesearth.core.files.FileDirectory;
import eu.pixliesearth.core.vendors.Vendor;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
/**
 * 
 * @author BradBot_1
 * 
 * <h3>A class to load custom features</h3>
 *
 * @apiNote TODO: notes
 * 
 */
public class CustomFeatureLoader {
	/**
	 * Used for NMS
	 */
	private static @Getter String serverVersion = "net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    private static @Getter String craftServerVersion = "org.bukkit.craftbukkit." + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
	
    private static @Getter CustomFeatureLoader loader;
    
	private @Getter JavaPlugin instance;
	private @Getter @Setter CustomFeatureHandler handler;
	
	/**
	 * Loads the custom features
	 * 
	 * @param plugin The JavaPlugin to load under
	 * @param path the path to reflect based off
	 */
	public CustomFeatureLoader(JavaPlugin plugin, String path) {
		loader = this;
		new FileDirectory(plugin.getDataFolder().getAbsolutePath(), "/customblocks/machines/"); // Load file directories
		//new FileDirectory(getInstance().getDataFolder().getAbsolutePath()+"/customblocks/"+"/machines/"); // Load file directory
		this.instance = plugin;
		load(path);
	}
	/**
	 * Loads everything
	 */
	public void load(String path) {
		setHandler(new CustomFeatureHandler(this));
		loadCommands(path);
		loadListeners(path);
		new CustomRecipes(getInstance());
		loadCustomRecipes(path);
		loadCustomItems(path);
		loadPermissions(path);
		loadCustomBlocks(path);
		//loadCustomMachineRecipes(path);
		loadQuests(path);
		loadMachines(path);
		getHandler().loadCustomBlocksFromFile();
		getHandler().loadMachinesFromFile();
		loadVendors(path);
	}
	/**
	 * Saves everything
	 */
	public void save() {
		getHandler().saveCustomBlocksToFile();
		getHandler().saveMachinesToFile();
		for (Listener customListener : getHandler().getCustomListeners()) 
			if (customListener instanceof CustomListener) 
				((CustomListener)customListener).onServerShutdown(this, getHandler());
	}
	// TODO: notes
	@SneakyThrows
	public void loadMachines(String path) {
		for (Class<? extends CustomMachine> clazz : reflectBasedOnExtentionOf(path+".machines", CustomMachine.class)) 
			loadMachine(clazz.newInstance());
		for (Class<? extends CustomCrafterMachine> clazz : reflectBasedOnExtentionOf(path+".machines", CustomCrafterMachine.class)) 
			loadMachine(clazz.newInstance());
		for (Class<? extends CustomGeneratorMachine> clazz : reflectBasedOnExtentionOf(path+".machines", CustomGeneratorMachine.class)) 
			loadMachine(clazz.newInstance());
		for (Class<? extends CustomEnergyCrafterMachine> clazz : reflectBasedOnExtentionOf(path+".machines", CustomEnergyCrafterMachine.class)) 
			loadMachine(clazz.newInstance());
	}
	// TODO: notes
	public void loadMachine(CustomMachine customMachine) {
		getHandler().registerMachine(customMachine);
	}
	/**
	 * Uses reflection to load custom recipes
	 * 
	 * @param path The path to reflect off
	 */
	@SneakyThrows
	public void loadCustomRecipes(String path) {
		for (Class<? extends CustomRecipe> clazz : reflectBasedOnExtentionOf(path+".recipes", CustomRecipe.class)) 
			loadCustomRecipe(clazz.newInstance());
	}
	/**
	 * Registers the inputed {@link CustomRecipe}
	 * 
	 * @param customRecipe The {@link CustomRecipe} to register
	 */
	public void loadCustomRecipe(CustomRecipe customRecipe) {
		getHandler().registerRecipe(customRecipe);
	}
	/**
	 * Uses reflection to load custom blocks
	 * 
	 * @param path The path to reflect off
	 */
	@SneakyThrows
	public void loadCustomBlocks(String path) {
		for (Class<? extends CustomBlock> clazz : reflectBasedOnExtentionOf(path+".blocks", CustomBlock.class)) 
			loadCustomBlock(clazz.newInstance());
		for (Class<? extends CustomEnergyBlock> clazz : reflectBasedOnExtentionOf(path+".blocks", CustomEnergyBlock.class)) 
			loadMachine(clazz.newInstance());
		for (Class<? extends CustomEnergyCable> clazz : reflectBasedOnExtentionOf(path+".blocks", CustomEnergyCable.class)) 
			loadMachine(clazz.newInstance());
		for (Class<? extends CustomSavableBlock> clazz : reflectBasedOnExtentionOf(path+".blocks", CustomSavableBlock.class)) 
			loadMachine(clazz.newInstance());
	}
	/**
	 * Loads the {@link CustomBlock} provided
	 * 
	 * @param customBlock The {@link CustomBlock} to load
	 */
	public void loadCustomBlock(CustomBlock customBlock) {
		getHandler().registerBlock(customBlock);
	}

	@SneakyThrows
	public void loadVendors(String path) {
		for (Class<? extends Vendor> clazz : reflectBasedOnExtentionOf(path + ".vendors", Vendor.class))
			loadVendor(clazz.getConstructor().newInstance());
	}

	public void loadVendor(Vendor vendor) {
		handler.getVendorMap().put(vendor.getNpcName(), vendor);
	}

	/**
	 * Uses reflection to load custom items
	 * 
	 * @param path Path to reflect off
	 */
	@SneakyThrows
	public void loadCustomItems(String path) {
		for (Class<? extends CustomItem> clazz : reflectBasedOnExtentionOf(path+".items", CustomItem.class))
			loadCustomItem(clazz.getConstructor().newInstance());
		for (Class<? extends CustomArmour> clazz : reflectBasedOnExtentionOf(path+".items", CustomArmour.class))
			loadCustomItem(clazz.newInstance());
		for (Class<? extends CustomWeapon> clazz : reflectBasedOnExtentionOf(path+".items", CustomWeapon.class))
			loadCustomItem(clazz.newInstance());
		for (Class<? extends CustomFuel> clazz : reflectBasedOnExtentionOf(path+".items", CustomFuel.class))
			loadCustomItem(clazz.newInstance());
	}

	/**
	 * Loads a custom item based on the input
	 * 
	 * @param c The custom item to load
	 */
	public void loadCustomItem(CustomItem c) {
		getHandler().registerItem(c);
		if (c.getDropFromBlock() == null) return;
		List<BlockDrop> bdrop = new ArrayList<>();
		if (handler.getDropMap().containsKey(c.getDropFromBlock().getBlock())) bdrop = handler.getDropMap().get(c.getDropFromBlock().getBlock());
		bdrop.add(c.getDropFromBlock());
		handler.getDropMap().put(c.getDropFromBlock().getBlock(), bdrop);
	}

	/**
	@SneakyThrows
	public void loadCustomMachineRecipes(String path) {
		for (Class<? extends CustomMachineRecipe> clazz : reflectBasedOnExtentionOf(path+".recipes.machine", CustomMachineRecipe.class)) 	
			loadCustomMachineRecipe(clazz.newInstance());
	}
	
	public void loadCustomMachineRecipe(CustomMachineRecipe customMachine) {
		getHandler().registerCustomMachineRecipe(customMachine);
	}
	*/
	/**
	 * Uses reflection to register permissions
	 * 
	 * @param path Path to reflect off
	 */
	@SneakyThrows
	public void loadPermissions(String path) {
		for (Class<? extends CustomPermission> clazz : reflectBasedOnExtentionOf(path+".permissions", CustomPermission.class)) 
			loadPermission(clazz.newInstance());
	}

	/**
	 * Registers a permission to Bukkit
	 * 
	 * @param customPermission The {@link CustomPermission} to be registered
	 */
	public void loadPermission(CustomPermission customPermission) {
		getInstance().getServer().getPluginManager().addPermission(customPermission.getAsRegisterablePermission());
		getHandler().registerPermission(customPermission);
	}
	/**
	 * Uses reflection to register listeners
	 * 
	 * @param path Path to reflect off
	 */
	@SneakyThrows
	public void loadListeners(String path) {
		for (Class<?> clazz : reflectBasedOnExtentionOf(path+".listeners", CustomListener.class)) 
			loadListener((Listener) clazz.newInstance());
	}
	/**
	 * Registers the provided listener
	 * 
	 * @param l The listener to register
	 */
	public void loadListener(Listener l) {
		Bukkit.getPluginManager().registerEvents(l, getInstance());
		getHandler().registerListener(l);
	}
	/**
	 * Uses reflection to register commands
	 * 
	 * @param path Path to reflect off
	 */
	@SneakyThrows
	public void loadCommands(String path) {
		for (Class<? extends CustomCommand> clazz : reflectBasedOnExtentionOf(path+".commands", CustomCommand.class)) {
			System.out.println("found " + clazz.getName());
			loadCommand(clazz.getConstructor().newInstance());
		}
	}
	/**
	 * Loads the {@link CustomCommand} that is provided
	 * 
	 * @param c The {@link CustomCommand} to be loaded
	 */
	public void loadCommand(CustomCommand c) {
		System.out.println("Registering command " + c.getName());
		try {
			Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
            commandMap.register(c.getName(), c.getAsRegisterableCommand());
            for (String alias : c.getAliases()) {
            	commandMap.register(alias, c.getAsRegisterableCommand());
            }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getHandler().registerCommand(c);
		}
	}

	/**
	 * Loads all {@link CustomQuest} instances
	 *
	 * @param path Path to search
	 */
	@SneakyThrows
	public void loadQuests(String path) {
		for (Class<? extends CustomQuest> clazz : reflectBasedOnExtentionOf(path + ".quests", CustomQuest.class)) {
			loadListener(clazz.getConstructor().newInstance());
			loadQuest(clazz.getConstructor().newInstance());
		}
	}

	/**
	 * registers the {@link CustomQuest} instances
	 *
	 * @param quest Quest to load
	 */
	public void loadQuest(CustomQuest quest) {
		handler.registerQuest(quest);
	}

	/**
	 * Gets all the classes in a package that extends <E>
	 * 
	 * @param <E> The class type that will be returned
	 * @param pathtoclasses The package path to the classes to reflect on
	 * @param type The class to reflect on
	 * @return The set of classes that extend {type}
	 */
	public static <E> Set<Class<? extends E>> reflectBasedOnExtentionOf(String pathtoclasses, Class<E> type) {
		Reflections reflections = new Reflections(pathtoclasses);
		return reflections.getSubTypesOf(type);
	}
	/**
	 * Gets all the classes in a package that implement <E>
	 * 
	 * @param <E> The class type that will be returned
	 * @param pathtoclasses The package path to the classes to reflect on
	 * @param type The class to reflect on
	 * @return The set of classes that implement {type}
	 * 
	 * @deprecated Don't work
	 */
	@Deprecated
	public static <E> Set<Class<?>> reflectBasedOnImplementationOf(String pathtoclasses, Class<E> type) {
		Reflections reflections = new Reflections(pathtoclasses);
        Set<Class<? extends Object>> scannersSet = reflections.getSubTypesOf(Object.class); // will return all classes in package
        Set<Class<?>> set = new HashSet<Class<?>>();
        for (Class<? extends Object> clazz : scannersSet) {
        	if (!clazz.isAssignableFrom(type)) continue;
        	set.add(clazz);
        }
		return set;
	}
}
