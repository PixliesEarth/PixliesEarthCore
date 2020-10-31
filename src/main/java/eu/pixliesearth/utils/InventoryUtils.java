package eu.pixliesearth.utils;

import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.output.ByteArrayOutputStream;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.json.simple.JSONObject;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;

public class InventoryUtils {
	
	@SuppressWarnings("unchecked")
	public static void getInventoryContentsFromJSONObject(JSONObject obj, Inventory inv) {
		obj.keySet().forEach(k -> {
			if (obj.get(k).equals("EMPTY")) {
				inv.clear(Integer.parseInt((String)k));
			} else {
				inv.setItem(Integer.parseInt((String)k), (ItemStack)deserialize((String)obj.get(k)));
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public static JSONObject makeInventoryToJSONObject(Inventory inv) {
		JSONObject obj = new JSONObject();
		int i = 0;
		for (ItemStack is : inv.getContents()) {
			if (is==null || is.getType().equals(Material.AIR)) {
				obj.put(Integer.toString(i), "EMPTY");
			} else {
				obj.put(Integer.toString(i), serialize(is));
			}
			i++;
		}
		return obj;
	}
	
	public static final String serialize(Object o) {
	    try {
	        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
	        BukkitObjectOutputStream out = new BukkitObjectOutputStream(bytesOut);
	        out.writeObject(o);
	        out.flush();
	        out.close();
	        return Base64Coder.encodeLines(bytesOut.toByteArray());
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        return null;
	    }
	}

    public static final Object deserialize(String base64) {
	    try {
	        byte[] data = Base64Coder.decodeLines(base64);
	        ByteArrayInputStream bytesIn = new ByteArrayInputStream(data);
	        BukkitObjectInputStream in = new BukkitObjectInputStream(bytesIn);
	        Object o = in.readObject();
	        in.close();
	        return o;
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        return null;
	    }
	}
}
