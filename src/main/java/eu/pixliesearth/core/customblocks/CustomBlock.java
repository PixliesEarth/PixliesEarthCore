package eu.pixliesearth.core.customblocks;

import java.io.IOException;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import eu.pixliesearth.core.files.FileDirectory;
import eu.pixliesearth.core.files.JSONFile;
import lombok.Getter;
import lombok.Setter;

public class CustomBlock {
	
	protected static final @Getter String MachineSavePath = CustomBlocks.getMachineSavePath();
	
	private static final String locationToSaveableString(Location l) {
		return l.getWorld().getUID().toString().concat(":").concat(Double.toString(l.getX())).concat(":").concat(Double.toString(l.getY())).concat(":").concat(Double.toString(l.getZ())).concat(":").concat(Float.toString(l.getYaw())).concat(":").concat(Float.toString(l.getPitch()));
	}
	
	protected @Getter @Setter Location location;
	protected @Getter @Setter UUID id;
	
	public CustomBlock(UUID id, Location location) {
		this.location = location;
		this.id = id;
	}
	
	public ItemStack getItem() {
		return null;
	}
	
	public String getTitle() {
		return "CustomBlock";
	}
	
	public void save() {
		JSONFile f;
		try {
        	f = new JSONFile(getMachineSavePath(), id.toString()); // Create or load file
        	f.clearFile();
    	} catch (Exception e) {
    		new FileDirectory(getMachineSavePath()); // Create directory as exception was caused by directory not existing
			f = new JSONFile(getMachineSavePath(), id.toString());
			try {f.clearFile();} catch (IOException e2) {e2.printStackTrace();} // clear old data
    	}
		f.put("location", locationToSaveableString(location));
		f.put("type", getTitle());
		f.put("id", getId());
		f.saveJsonToFile();
	}
}
