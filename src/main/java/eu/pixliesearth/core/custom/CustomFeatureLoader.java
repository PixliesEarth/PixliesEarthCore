package eu.pixliesearth.core.custom;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import eu.pixliesearth.core.files.FileDirectory;
import eu.pixliesearth.utils.ThreadUtils;
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
		new FileDirectory(plugin.getDataFolder().getAbsolutePath(), "/customblocks/"); // Load file directory
		this.instance = plugin;
		load(path);
	}
	/**
	 * Loads everything
	 */
	public void load(String path) {
		setHandler(new CustomFeatureHandler(this));
		loadCommands(path);
		new ThreadUtils(new Runnable() {
			@Override
			public void run() {
				loadListeners(path);
				loadRecipes(path);
				loadCustomItems(path);
				loadPermissions(path);
				loadCustomBlocks(path);
				loadCustomMachineRecipes(path);
				getHandler().loadCustomBlocksFromFile();
			}
		});
	}
	/**
	 * Saves everything
	 */
	public void save() {
		new ThreadUtils(new Runnable() {
			@Override
			public void run() {
				getHandler().saveCustomBlocksToFile();
			}
		});
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
	}
	/**
	 * Loads the {@link CustomBlock} provided
	 * 
	 * @param customBlock The {@link CustomBlock} to load
	 */
	public void loadCustomBlock(CustomBlock customBlock) {
		getHandler().registerBlock(customBlock);
	}
	/**
	 * Uses reflection to load custom items
	 * 
	 * @param path Path to reflect off
	 */
	@SneakyThrows
	public void loadCustomItems(String path) {
		for (Class<? extends CustomItem> clazz : reflectBasedOnExtentionOf(path+".items", CustomItem.class))
			loadCustomItem(clazz.newInstance());
		for (Class<? extends CustomArmour> clazz : reflectBasedOnExtentionOf(path+".items", CustomArmour.class))
			loadCustomItem(clazz.newInstance());
		for (Class<? extends CustomWeapon> clazz : reflectBasedOnExtentionOf(path+".items", CustomWeapon.class))
			loadCustomItem(clazz.newInstance());
	}
	/**
	 * Loads a custom item based on the input
	 * 
	 * @param c The custom item to load
	 */
	public void loadCustomItem(CustomItem c) {
		getHandler().registerItem(c);
	}
	//TODO: notes
	@SneakyThrows
	public void loadCustomMachineRecipes(String path) {
		for (Class<? extends CustomMachineRecipe> clazz : reflectBasedOnExtentionOf(path+".recipes.machine", CustomMachineRecipe.class)) 	
			loadCustomMachineRecipe(clazz.newInstance());
	}
	//TODO: notes
	public void loadCustomMachineRecipe(CustomMachineRecipe customMachine) {
		getHandler().registerCustomMachineRecipe(customMachine);
	}
	/**
	 * Uses reflection to load recipes into Minecraft
	 * 
	 * @param path Path to reflect off
	 */
	@SneakyThrows
	public void loadRecipes(String path) {
		for (Class<? extends CustomRecipe> clazz : reflectBasedOnExtentionOf(path+".recipes.craftingtable", CustomRecipe.class)) 
			loadRecipe(clazz.newInstance());
	}
	/**
	 * Loads a recipe into Minecraft base on a custom recipe
	 * 
	 * @param cr The custom recipe to load
	 */
	public void loadRecipe(CustomRecipe cr) {
		Recipe r = cr.getRecipeAsMinecraftRecipe(getInstance()); // Get the custom recipe name
		if (r==null) throw new Exceptions.CustomRecipeCreationException(cr); // The recipe failed to be created
		Bukkit.addRecipe(r); // Register the recipe
		getHandler().registerRecipe(cr);
	}
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
		System.out.println("Registering commands");
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
