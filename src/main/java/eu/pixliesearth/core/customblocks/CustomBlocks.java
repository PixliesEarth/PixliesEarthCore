package eu.pixliesearth.core.customblocks;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.files.FileBase;
import eu.pixliesearth.core.files.FileDirectory;
import eu.pixliesearth.core.files.JSONFile;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class CustomBlocks {
	
	protected static final @Getter String MachineSavePath = Main.getInstance().getDataFolder().getAbsolutePath()+"/customblocks/";
	private static final Main instance = Main.getInstance();
	
	private static final Location locationFromSaveableString(String s) {
		String[] data = s.split(":");
		return new Location(Bukkit.getWorld(UUID.fromString(data[0])), Double.parseDouble(data[1]), Double.parseDouble(data[2]), Double.parseDouble(data[3]), Float.parseFloat(data[4]), Float.parseFloat(data[5]));
	}
	
	// private static Set<Class<? extends CustomBlock>> array = new HashSet<Class<? extends CustomBlock>>();
	private static HashMap<String, Class<? extends CustomBlock>> map = new HashMap<String, Class<? extends CustomBlock>>();
	private static @Getter Set<CustomBlock> set = new HashSet<CustomBlock>();
	
	public static boolean registerCustomBlock(String blockname, Class<? extends CustomBlock> customblock) {
		if (isRegistered(customblock) || isRegistered(blockname)) 
			return false;
		else {
			map.put(blockname, customblock);
			return true;
		}
	}
	
	public static void registerCustomBlock(Class<? extends CustomBlock> customblock) {
		try {
			registerCustomBlock(customblock.newInstance().getTitle(), customblock);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public static void unregisterCustomBlock(String blockname) {
		map.remove(blockname);
	}
	
	public static boolean isRegistered(Class<? extends CustomBlock> customblock) {
		return map.containsValue(customblock);
	}
	
	public static boolean isRegistered(String blockname) {
		return map.containsKey(blockname);
	}
	
	public static Class<? extends CustomBlock> getFromBlockType(String blockname) {
		return map.get(blockname);
	}
	
	public static Class<? extends CustomBlock> getFromItemStack(ItemStack is) {
		for (String s : map.keySet()) {
			try {
				if (map.get(s).newInstance().getItem().isSimilar(is)) 
					return map.get(s);
				else 
					continue;
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static void placedCustomBlock(CustomBlock customblock) {
		set.add(customblock);
	}
	
	public static void brokeCustomBlock(Location loc) {
		set.remove(getBlockFromLocation(loc));
	}
	
	public static CustomBlock getBlockFromLocation(Location loc) {
		for(CustomBlock customblock : set)
			if (customblock.getLocation().equals(loc)) 
				return customblock;
		return null;
	}
	
	public static void load() {
		Reflections reflections = new Reflections("eu.pixliesearth.core.customblocks.blocks"); // get all classes in the package eu.pixliesearth.core.customblocks.blocks
		Set<Class<? extends CustomBlock>> classes = reflections.getSubTypesOf(CustomBlock.class); // puts all the classes in a set
		for (Class<? extends CustomBlock> c : classes) 
			registerCustomBlock(c);
		
		for (FileBase f : new FileDirectory(getMachineSavePath()).getFilesInDirectory()) {
			if (!f.getFile().getName().endsWith(".json")) {
                f.deleteFile();
                continue;
            }
			JSONFile jf = new JSONFile(f.getFilePath(), f.getFileName());
			CustomBlock customblock;
			Location loc = locationFromSaveableString(jf.get("location"));
			if (isRegistered(jf.get("type"))) {
				try {
					customblock = getFromBlockType(jf.get("type")).getConstructor(UUID.class, Location.class).newInstance(UUID.fromString(jf.get("id")), loc);
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
					customblock = null;
				}
			} else {
				instance.getLogger().warning("Unable to load the custom block at "+jf.get("location")+"! The block type is not registered? Removing file");
				jf.deleteFile();
				customblock = null;
			}
            if (customblock == null) {
            	instance.getLogger().warning("Unable to load the custom block at "+jf.get("location")+"! The block returned null");
                continue;
            } else 
            	set.add(customblock);
		}
	}
	/**
	 * Saves all custom blocks that have been placed
	 */
	public static void save() {
		for (CustomBlock customblock : getSet())
			customblock.save();
	}
	
}
