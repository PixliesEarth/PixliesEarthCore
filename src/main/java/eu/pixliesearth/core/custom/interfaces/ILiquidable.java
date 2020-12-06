package eu.pixliesearth.core.custom.interfaces;

import eu.pixliesearth.core.custom.MinecraftMaterial;
import eu.pixliesearth.utils.CustomItemUtil;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public interface ILiquidable {
	
	public ConcurrentHashMap<String, Integer> getLiquidCapacities();
	
	public static final String waterID = MinecraftMaterial.WATER.getUUID();
	public static final String lavaID = MinecraftMaterial.LAVA.getUUID();
	
	// Custom ones
	public static final String hydrogenID = "Pixlies:Hydrogen";
	public static final String oxygenID = "Pixlies:Oxygen";
	public static final String carbonID = "Pixlies:Carbon";
	public static final String oilID = "Pixlies:Oil";
	public static final String heliumID = "Pixlies:Helium";
	
	public static boolean isBucketFormOf(ItemStack is, String UUID, boolean convert) {
		String id = (convert) ? convertID(UUID) : UUID;
		boolean isBucket = CustomItemUtil.getUUIDFromItemStack(is).equalsIgnoreCase(id+"_bucket");
		String[] s = id.split(":");
		boolean isCanist = CustomItemUtil.getUUIDFromItemStack(is).equalsIgnoreCase(s[0]+"Canister_"+s[1]);
    	return (isBucket || isCanist);
    }
	
	public static boolean areLiquidsEqual(String id, String id2) {
		return getLiquidContents(id).equals(getLiquidContents(id2));
	}
	
	public static String getLiquidBondedUUID(String id, String id2) {
        // if (id==null || id2==null || !id.contains(":") || !id2.contains(":")) return id;
        id = convertID(id);
        id2 = convertID(id2);
		String r = "";
		String[] s = id.split(":");
		String[] s2 = id2.split(":");
		String ns = s[0];
		String ns2 = s2[0];
		if (ns.equalsIgnoreCase(ns2)) {
			r += ns + ":";
		} else {
			r += "Pixlies:"; // Default to pixlies
		}
		String[] s3 = s[1].split("_");
		String[] s4 = s2[1].split("_");
		List<String> l = new ArrayList<>();
		for (String s5 : s3) {
			if (l.isEmpty()) {
				l.add(s5);
			} else {
				boolean v = true;
				for (String s6 : l) {
					if (s6.contains(s5.replaceAll("[0-9]", ""))) {
						int i = 1;
					    int i2 = 1;
					    try {
					        i = Integer.parseInt(s5.replaceAll("[^0-9]", ""));
					    } catch(Exception ingore) {}
					    try {
					        i2 = Integer.parseInt(s6.replaceAll("[^0-9]", ""));
					    } catch(Exception ingore) {}
					    l.set(l.indexOf(s6), s5.replaceAll("[0-9]", "")+Integer.toString(i+i2));
						v = false;
					} else {
						// Do nothing
					}
				}
				if (v) {
					l.add(s5);
				}
			}
		}
		for (String s7 : s4) {
			if (l.isEmpty()) {
				l.add(s7);
			} else {
				boolean v = true;
				for (String s8 : l) {
					if (s8.contains(s7.replaceAll("[0-9]", ""))) {
					    int i = 1;
					    int i2 = 1;
					    try {
					        i = Integer.parseInt(s7.replaceAll("[^0-9]", ""));
					    } catch(Exception ingore) {}
					    try {
					        i2 = Integer.parseInt(s8.replaceAll("[^0-9]", ""));
					    } catch(Exception ingore) {}
					    l.set(l.indexOf(s8), s7.replaceAll("[0-9]", "")+Integer.toString(i+i2));
						v = false;
					} else {
						// Do nothing
					}
				}
				if (v) {
					l.add(s7);
				}
			}
		}
		Collections.sort(l);
		for (String s9 : l) {
			r += s9;
			if (!(l.indexOf(s9)==(l.size()-1))) {
				r += "_";
			}
		}
		return r;
	}
	
	public static String liquidContentsToLiquid(List<String> l) {
		String r = "";
		Collections.sort(l);
		for (String s : l) {
		    s = convertID(s);
		    if (r == null || r == "") {
		        r = s;
		        continue;
		    }
		    r = getLiquidBondedUUID(r, s);
		}
		return r;
	}
	
	public static List<String> getLiquidContents(String id) {
	    id = convertID(id);
		List<String> l = new ArrayList<>();
		String n = id.split(":")[0];
		String[] s = id.split(":")[1].split("_");
		for (String s2 : s) {
			if (s2.equalsIgnoreCase(s2.replaceAll("[0-9]", ""))) {
			    l.add(n+":"+s2);
			    continue;
			} else {
			    for (int i = 0; i < Integer.parseInt(s2.replaceAll("[^0-9]", "")); i++) {
			        l.add(n+":"+s2.replaceAll("[0-9]", ""));
			    }
			}
		}
		Collections.sort(l);
		return l;
	}
	
	public static String convertID(String id) {
        return (id.equalsIgnoreCase(waterID)) ? getLiquidBondedUUID(hydrogenID, getLiquidBondedUUID(hydrogenID, oxygenID)) : ((id.equalsIgnoreCase(oilID)) ? ("Pixlies:Carbon_Hydrogen"+4) : (id.equals("") ? "null" : id));
    }
	
	public default String turnLiquidContentsIntoSavableString(Map<String, Integer> map) {
		String s = "[";
		for (Entry<String, Integer> entry : map.entrySet()) {
			s += ("<"+entry.getKey()+";"+Integer.toString(entry.getValue())+">");
		}
		return (s.equals("[") ? "[]" : s+"]");
	}
	
	public default ConcurrentHashMap<String, Integer> turnSavableStringIntoLiquidContents(String data) {
		if (data.equals("[]")) {
	        return new ConcurrentHashMap<>();
	    } else if (!data.startsWith("[<")) {
	    	return new ConcurrentHashMap<>();
	    } else {
	        data = data.substring(2, (data.length()-2));
	        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
	        String[] s = data.split("><");
	        for (String s2 : s){
	            String[] s3 = s2.split(";");
	            map.put(s3[0], Integer.parseInt(s3[1]));
	        }
	        return map;
	    }
	}
}
