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
import io.sentry.Sentry;
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

	private @Getter final Set<Listener> customListeners;
	private @Getter final Set<CustomCommand> customCommands;
	private @Getter final Set<CustomPermission> customPermissions;
	private @Getter final Map<String, Vendor> vendorMap;

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
		this.customListeners = new HashSet<Listener>();
		this.customCommands = new HashSet<CustomCommand>();
		this.customPermissions = new HashSet<CustomPermission>();

		this.vendorMap = new HashMap<>();
	}
	
	public void registerSkills() {
		SkillHandler skillHandler = SkillHandler.getSkillHandler();
		skillHandler.registerSkill(new SimpleSkill("Lumbering", "Chop down trees!"));//
		skillHandler.registerSkill(new SimpleSkill("Mining", "Mine minerals and stone underground"));//
		skillHandler.registerSkill(new SimpleSkill("Building", "Build big structures!"));//
		skillHandler.registerSkill(new SimpleSkill("Traveling", "Walking is good for your body"));//
		skillHandler.registerSkill(new SimpleSkill("Farming", "Live a farmers life"));//
		skillHandler.registerSkill(new SimpleSkill("Hunting", "Hunt animals"));//
		skillHandler.registerSkill(new SimpleSkill("Enchanting", "Enchant your gear"));//
		skillHandler.registerSkill(new SimpleSkill("Fishing", "Just fish!"));//
		skillHandler.registerSkill(new SimpleSkill("Taming", "Tame animals"));//
		skillHandler.registerSkill(new SimpleSkill("Questing", ""));
		System.out.println("Registered skills");
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
	 * Registers the command
	 * 
	 * @param customCommand The {@link CustomCommand} instance to register 
	 */
	public void registerCommand(CustomCommand customCommand) {
		this.customCommands.add(customCommand);
		// System.out.println("Registered the command "+customCommand.getCommandName());
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

}
