package eu.pixliesearth.utils;

import java.io.ByteArrayInputStream;

import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.output.ByteArrayOutputStream;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.json.simple.JSONObject;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import eu.pixliesearth.core.custom.listeners.CustomInventoryListener;

public class InventoryUtils {
	
	public static String makeInventoryToString(Inventory inv) {
		return itemStackArrayToBase64(inv.getContents());
	}
	
	public static void setInventoryContentsFromString(String data, Inventory inv) {
		try {
            ItemStack[] isl = itemStackArrayFromBase64(data);
            for (int i = 0; i < isl.length; i++) {
            	inv.setItem(i, isl[i]);
            }
        } catch (Exception ignore) {}
	}
	
	
	private static String itemStackArrayToBase64(ItemStack[] items) {
    	try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
            
            // Write the size of the inventory
            dataOutput.writeInt(items.length);
            
            // Save every element in the list
            for (int i = 0; i < items.length; i++) {
                dataOutput.writeObject(items[i]);
            }
            
            // Serialize that array
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            return "";
        }
    }
	
	private static ItemStack[] itemStackArrayFromBase64(String data) {
    	try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            ItemStack[] items = new ItemStack[dataInput.readInt()];
    
            // Read the serialized inventory
            int i = 0;
            while(dataInput.available() > 0) {
            	items[i] = (ItemStack) dataInput.readObject();
            	i++;
            }
            /*for (int i = 0; i < items.length; i++) {
            	items[i] = (ItemStack) dataInput.readObject();
            }*/
            
            dataInput.close();
            return items;
        } catch (Exception ignore) {
            return new ItemStack[0];
        }
    }
	
	@SuppressWarnings("unchecked")
	public static void getInventoryContentsFromJSONObject(JSONObject obj, Inventory inv) {
		obj.keySet().forEach(k -> {
			if (obj.get(k).equals("A") || obj.get(k).equals("EMPTY")) {
				inv.clear(Integer.parseInt((String)k));
			} else if (obj.get(k).equals("B")) {
				// Dont do anything!
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
				obj.put(Integer.toString(i), "A");
			} else if (CustomItemUtil.getUUIDFromItemStack(is).equalsIgnoreCase(CustomInventoryListener.getUnclickableItemUUID())) {
				obj.put(Integer.toString(i), serialize(is)); // Used to be 'B' however, this has since been changed to fix a bug with inventory loading
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
