package eu.pixliesearth.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemBuilder {

    private ItemStack item;
    private List<String> lore;
    private ItemMeta meta;

    public ItemBuilder(ItemStack item) {
        this.item = item.clone();
        this.meta = this.item.getItemMeta();
        this.lore = this.meta.getLore() == null ? new ArrayList<>() : meta.getLore();
    }

    public ItemBuilder(Material mat, int amount) {
        item = new ItemStack(mat, amount);
        meta = item.getItemMeta();
        this.lore = meta.getLore() == null ? new ArrayList<>() : meta.getLore();
    }

    public ItemBuilder(Material mat) {
        item = new ItemStack(mat);
        meta = item.getItemMeta();
        this.lore = meta.getLore() == null ? new ArrayList<>() : meta.getLore();
    }
    /**
     * Use setDamage instead!
     * 
     * @param durability the durability to set the item to
     */
    @Deprecated
    public ItemBuilder setDurability(short durability) {
        item.setDurability(durability);
        return this;
    }
    
    public ItemBuilder setDamage(int damage) {
        Damageable d = (Damageable) meta;
        d.setDamage(damage);
        return this;
    }

    public ItemBuilder setAmount(int value) {
        item.setAmount(value);
        return this;
    }

    public ItemBuilder setNoName() {
        meta.setDisplayName(" ");
        return this;
    }

    public ItemBuilder setGlow() {
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    public ItemBuilder setGlow(boolean value) {
        if (value) {
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        return this;
    }

    public ItemBuilder addLoreLine(String line) {
        lore.add(line);
        return this;
    }

    public ItemBuilder addLoreArray(String[] lines) {
        lore.addAll(Arrays.asList(lines));
        return this;
    }

    public ItemBuilder addLoreAll(List<String> lines) {
        lore.addAll(lines);
        return this;
    }

    public ItemBuilder setDisplayName(String name) {
        meta.setDisplayName(name);
        return this;
    }

    public ItemBuilder setSkullOwner(String owner) {
        ((SkullMeta) meta).setOwningPlayer(getOfflinePlayer(owner));
        return this;
    }

    public ItemBuilder setSkullOwner(UUID uuid) {
        ((SkullMeta) meta).setOwningPlayer(Bukkit.getOfflinePlayer(uuid));
        return this;
    }

    public ItemBuilder setBook(Enchantment enchantment, int level) {
        ((EnchantmentStorageMeta) meta).addStoredEnchant(enchantment, level, true);
        return this;
    }

    public ItemBuilder setUnbreakable(boolean value) {
        meta.setUnbreakable(value);
        return this;
    }

    public ItemBuilder setCustomModelData(int data) {
        meta.setCustomModelData(data);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment ench, int lvl) {
        meta.addEnchant(ench, lvl, true);
        return this;
    }

    public ItemBuilder addItemFlag(ItemFlag flag) {
        meta.addItemFlags(flag);
        return this;
    }

    public ItemBuilder hideFlags() {
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        return this;
    }

    public ItemBuilder addNBTTag(String key, Object value, NBTTagType type) {
		NBTUtil.NBTTags tags = NBTUtil.getTagsFromItem(item);
		tags.addTag(key, value, type);
		item = NBTUtil.addTagsToItem(item, tags);
        return this;
    }

    public ItemStack build() {
        if (!lore.isEmpty()) {
            meta.setLore(lore);
        }
        item.setItemMeta(meta);
        return item;
    }
    
    private OfflinePlayer getOfflinePlayer(String name) {
    	for (OfflinePlayer op : Bukkit.getOfflinePlayers()) if (op.getName().equalsIgnoreCase(name)) return op;
    	return null;
    }
    /**
     * 
     * @author BradBot_1
     *
     * A static based class to mess with item NBT
     *
     */
    public static class NBTUtil {
    	public static class NBTTags {
    		private Object tag;
    		public NBTTags(Object tag) {
    			this.tag = tag;
    			if (this.tag==null) try {this.tag = Class.forName(NBTUtil.serverVersion+".NBTTagCompound").newInstance();} catch (Exception e) {e.printStackTrace();}
    		}
    		public void addTag(String key, Object value) {
    			addTag(key, value, NBTTagType.getTagByClass(value.getClass()));
    		}
    		public void addTag(String key, Object value, NBTTagType tagtype) {
    			switch (tagtype) {
    			case STRING:
    				try{
                        Method m = Class.forName(NBTUtil.serverVersion+".NBTTagCompound").getMethod("setString", String.class, NBTTagType.getClassByID(tagtype.getTagID()));
                        m.setAccessible(true);
                        m.invoke(this.tag, key, value);
                        m.setAccessible(false);
                    } catch(Exception e){
                        e.printStackTrace();
                    }
    				break;
    			case INT:
    				try{
    					Method m = Class.forName(NBTUtil.serverVersion+".NBTTagCompound").getMethod("setInt", String.class, NBTTagType.getClassByID(tagtype.getTagID()));
    	                m.setAccessible(true);
    	                m.invoke(this.tag, key, value);
    	                m.setAccessible(false);
    	            } catch(Exception e){
    	            	e.printStackTrace();
    	            }
    				break;
    			case SHORT:
    				try{
    					Method m = Class.forName(NBTUtil.serverVersion+".NBTTagCompound").getMethod("setShort", String.class, NBTTagType.getClassByID(tagtype.getTagID()));
    	                m.setAccessible(true);
    	                m.invoke(this.tag, key, value);
    	                m.setAccessible(false);
    	            } catch(Exception e){
    	            	e.printStackTrace();
    	            }
    				break;
    			case BOOLEAN:
    				try{
    					Method m = Class.forName(NBTUtil.serverVersion+".NBTTagCompound").getMethod("set", String.class, NBTTagType.getClassByID(1));
    	                m.setAccessible(true);
    	                if ((boolean)value) m.invoke(this.tag, key, (byte)1); else m.invoke(this.tag, key, (byte)0); // Done with byte to make compatibility easier
    	                //m.invoke(this.tag, key, value);
    	                m.setAccessible(false);
    	            } catch(Exception e){
    	            	e.printStackTrace();
    	            }
    				break;
    			case DOUBLE:
    				try{
    					Method m = Class.forName(NBTUtil.serverVersion+".NBTTagCompound").getMethod("setDouble", String.class, NBTTagType.getClassByID(tagtype.getTagID()));
    	                m.setAccessible(true);
    	                m.invoke(this.tag, key, value);
    	                m.setAccessible(false);
    	            } catch(Exception e){
    	            	e.printStackTrace();
    	            }
    				break;
    			case LONG:
    				try{
    					Method m = Class.forName(NBTUtil.serverVersion+".NBTTagCompound").getMethod("setLong", String.class, NBTTagType.getClassByID(tagtype.getTagID()));
    	                m.setAccessible(true);
    	                m.invoke(this.tag, key, value);
    	                m.setAccessible(false);
    	            } catch(Exception e){
    	            	e.printStackTrace();
    	            }
    				break;
    			default :
    				try{
    					Method m = Class.forName(NBTUtil.serverVersion+".NBTTagCompound").getMethod("set", String.class, NBTTagType.getClassByID(tagtype.getTagID()));
    	                m.setAccessible(true);
    	                m.invoke(this.tag, key, value);
    	                m.setAccessible(false);
    	            } catch(Exception e){
    	            	e.printStackTrace();
    	            }
    				break;
    			}
    		}
    		public Long getLong(String key) {
    			try{
    	            Method m = Class.forName(NBTUtil.serverVersion+".NBTTagCompound").getMethod("getLong", String.class);
    	            m.setAccessible(true);
    	            Object o = m.invoke(this.tag, key);
    	            m.setAccessible(false);
    	            if (o instanceof Long) return (Long) o; else return null;
    	        }
    	        catch(Exception e){
    	            e.printStackTrace();
    	            return null;
    	        }
    		}
    		public String getString(String key) {
    			try{
    	            Method m = Class.forName(NBTUtil.serverVersion+".NBTTagCompound").getMethod("getString", String.class);
    	            m.setAccessible(true);
    	            Object o = m.invoke(this.tag, key);
    	            m.setAccessible(false);
    	            if (o instanceof String) return (String) o; else return null;
    	        }
    	        catch(Exception e){
    	            e.printStackTrace();
    	            return null;
    	        }
    		}
    		public boolean getBoolean(String key) {
    			if (getByte(key)==(byte)1) return true; else return false;
    		}
    		public Byte getByte(String key) {
    			Object o = get(key);
    			if (o instanceof Byte) return (byte) o; else return null;
    		}
    		public Object get(String key) {
    			try{
    	            Method m = Class.forName(NBTUtil.serverVersion+".NBTTagCompound").getMethod("get", String.class);
    	            m.setAccessible(true);
    	            Object o = m.invoke(this.tag, key);
    	            m.setAccessible(false);
    	            return o;
    	        }
    	        catch(Exception e){
    	            e.printStackTrace();
    	            return null;
    	        }
    		}
    		public Object getCompound() {
    			return this.tag;
    		}
    		public String toString() {
    			return "NBT{"+this.tag+"}";
    		}
    	}
    	
    	private static String serverVersion = "net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    	private static String craftServerVersion = "org.bukkit.craftbukkit." + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    	
    	@Deprecated // Not done TODO: This
    	public static HashMap<String, Object> getNBTFromItem(ItemStack i) {
    		if (i==null||i.getType().equals(Material.AIR)) throw new NullPointerException("ItemStack can't be null or air!");
    		HashMap<String, Object> a = new HashMap<String, Object>();
    		// TODO: Make a map of all nbt on an item
    		return a;
    	}
    	
    	public static ItemStack addTagsToItem(ItemStack i, NBTTags tag) {
    		if (i==null||i.getType().equals(Material.AIR)) throw new NullPointerException("ItemStack can't be null or air!");
    		try{
                Method m = Class.forName(craftServerVersion+".inventory.CraftItemStack").getMethod("asNMSCopy", ItemStack.class);
                Method m2 = Class.forName(serverVersion+".ItemStack").getMethod("setTag", Class.forName(serverVersion+".NBTTagCompound"));
                Method m3 = Class.forName(craftServerVersion+".inventory.CraftItemStack").getMethod("asBukkitCopy", Class.forName(serverVersion+".ItemStack"));
                
                m.setAccessible(true);
                m2.setAccessible(true);
                m3.setAccessible(true);
                
                Object o = m.invoke(null, i);
                m2.invoke(o, tag.getCompound());
                Object o2 = m3.invoke(null, o);
                
                m.setAccessible(false);
                m2.setAccessible(false);
                m3.setAccessible(false);
                
                if (o2 instanceof ItemStack) return (ItemStack) o2; else return null;
            }
            catch(Exception e) {
                e.printStackTrace();
                return null;
            }
    	}
    	public static NBTTags getTagsFromItem(ItemStack i) {
    		if (i==null||i.getType().equals(Material.AIR)) throw new NullPointerException("ItemStack can't be null or air!");
    		try{
    			Method m =  Class.forName(craftServerVersion+".inventory.CraftItemStack").getMethod("asNMSCopy", ItemStack.class);
    			Method m2 = Class.forName(serverVersion+".ItemStack").getMethod("getTag");
    			
                m.setAccessible(true);
                m2.setAccessible(true);
                
                Object o = m.invoke(null, i);
                Object o2 = m2.invoke(o);
                
                m.setAccessible(false);
                m2.setAccessible(false);
    			
                return new NBTTags(o2);
            } catch(Exception e){
                e.printStackTrace();
                return null;
            }
    	}
    }
    /**
     * 
     * @author BradBot_1
     *
     * A enum used for controlling NBTTags with NBTUtil
     *
     */
    public enum NBTTagType{
    	END(0),
        BYTE(1),
    	SHORT(2),
    	INT(3),
    	LONG(4),
    	FLOAT(5),
    	DOUBLE(6),
    	BYTEARRAY(7),
    	STRING(8),
    	LIST(9),
    	COMPOUND(10),
    	INTARRAY(11),
    	// Custom nbt types
    	BOOLEAN(100);
    	// The variable the internal tag id is set to
    	private int tagID;
    	// The static variable that states where the compound class is located
    	static Class<?> compoundclass = getCompoundClass();
    	/**
    	 * Initialisation method
    	 * 
    	 * @param id
    	 */
    	NBTTagType(int id) {
    		tagID = id;
    	}
    	/**
    	 * Returns the tagID used by minecraft internally
    	 * 
    	 * @return The tags ID
    	 */
    	public int getTagID() {
    		return tagID;
    	}
    	/**
    	 * Returns the class the tag is using
    	 * 
    	 * @return The tags class
    	 */
    	public Class<?> getTagClass(){
    		return getClassByID(tagID);
    	}
    	/**
    	 * Gets a tag type from an id
    	 * 
    	 * @param id The tag ID
    	 * @return The tag that correlates with the ID
    	 */
    	public static NBTTagType getTagByID(int id) {
    		switch(id) {
    		case 0:
    			return END;
    		case 1:
    			return BYTE;
    		case 2:
    			return SHORT;
    		case 3:
    			return INT;
    		case 4:
    			return LONG;
    		case 5:
    			return FLOAT;
    		case 6:
    			return DOUBLE;
    		case 7:
    			return BYTEARRAY;
    		case 8:
    			return STRING;
    		case 9:
    			return LIST;
    		case 10:
    			return COMPOUND;
    		case 11:
    			return INTARRAY;
    		case 100:
    			return BOOLEAN;
    		default :
    			return END;
    		}
    	}
    	/**
    	 * Returns a class based on the NBT tag id
    	 * 
    	 * @param id The NBTtag's internal minecraft id
    	 * @return The class the NBT is correlated to
    	 */
    	public static Class<?> getClassByID(int id){
    		switch(id) {
    		case 0:
    			return null;
    		case 1:
    			return byte.class;
    		case 2:
    			return short.class;
    		case 3:
    			return int.class;
    		case 4:
    			return long.class;
    		case 5:
    			return float.class;
    		case 6:
    			return double.class;
    		case 7:
    			return byte[].class;
    		case 8:
    			return String.class;
    		case 9:
    			return List.class;
    		case 10:
    			return compoundclass;
    		case 11:
    			return int[].class;
    		case 100: 
    			return boolean.class;
    		default :
    			return null;
    		}
    	}
    	/**
    	 * Returns a NBTTag id based on the corresponding class
    	 * 
    	 * @param c The class that the tag uses
    	 * @return The id the class corresponds to
    	 */
    	public static int getIDByClass(Class<?> c) {
    		// Has to be done this way as switch statements don't work with class types
    		if (c.equals(byte.class)) return 1;
    		else if (c.equals(short.class)) return 2;
    		else if (c.equals(int.class)) return 3;
    		else if (c.equals(long.class)) return 4;
    		else if (c.equals(float.class)) return 5;
    		else if (c.equals(double.class)) return 6;
    		else if (c.equals(byte[].class)) return 7;
    		else if (c.equals(String.class)) return 8;
    		else if (c.equals(List.class)) return 9;
    		else if (c.equals(compoundclass)) return 10;
    		else if (c.equals(int[].class)) return 11;
    		else if (c.equals(boolean.class)) return 100;
    		else return 0;
    	}
    	/**
    	 * Gets the ID of the class and gets the Tag from the id
    	 * 
    	 * @param c The class that the tag uses
    	 * @return The tag type
    	 */
    	public static NBTTagType getTagByClass(Class<?> c) {
    		return getTagByID(getIDByClass(c));
    	}
    	/**
    	 * Returns the Compound class
    	 * 
    	 * @return compound class
    	 */
    	private static Class<?> getCompoundClass() {
    		try {
				return Class.forName("net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3]+".NBTTagCompound");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				return null;
			}
    	}
	}
}
