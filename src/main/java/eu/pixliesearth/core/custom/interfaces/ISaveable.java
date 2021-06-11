package eu.pixliesearth.core.custom.interfaces;

import org.bukkit.Location;
import org.json.simple.JSONObject;
/**
 * 
 * @author BradBot_1
 * 
 * TODO: implement this
 *
 */
public interface ISaveable {
	
	public JSONObject save(Location location);
	
	public void load(JSONObject object);
	
}
