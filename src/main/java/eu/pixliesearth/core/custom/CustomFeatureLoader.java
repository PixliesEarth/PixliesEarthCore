package eu.pixliesearth.core.custom;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import eu.pixliesearth.core.custom.CustomCommand.RegisterableCommand;
import eu.pixliesearth.core.files.FileDirectory;
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
		loadPermissions(path);
		getHandler().loadSkillsFromFile();
		getHandler().registerSkills();
	}
	/**
	 * Saves everything
	 */
	public void save() {
		getHandler().saveSkillsToFile();
		for (Listener customListener : getHandler().getCustomListeners()) 
			if (customListener instanceof CustomListener) 
				((CustomListener)customListener).onServerShutdown(this, getHandler());
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
		int i = 0;
		for (Class<?> clazz : reflectBasedOnExtentionOf(path+".listeners", CustomListener.class)) {
			loadListener((Listener) clazz.newInstance());
			i++;
		}
		System.out.println("§7Loaded §b" + i + "§7 custom listeners.");
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
		int i = 0;
		for (Class<? extends CustomCommand> clazz : reflectBasedOnExtentionOf(path+".commands", CustomCommand.class)) {
			loadCommand(clazz.getConstructor().newInstance());
			i++;
		}
		System.out.println("§7Loaded §b" + i + "§7 custom commands.");
	}
	/**
	 * Loads the {@link CustomCommand} that is provided
	 * 
	 * @param c The {@link CustomCommand} to be loaded
	 */
	public void loadCommand(CustomCommand c) {
		try {
			Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
            commandMap.register(this.getInstance().getName(), new RegisterableCommand(c));
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
