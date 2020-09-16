package eu.pixliesearth.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * @author BradBot_1
 * <p>
 * A static based class to mess with item NBT
 */
public class NBTUtil {

    public static class NBTTags {

        private Object tag;

        public NBTTags(Object tag) {
            this.tag = tag;
            if (this.tag == null) try {
                this.tag = Class.forName(NBTUtil.serverVersion + ".NBTTagCompound").newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void addTag(String key, Object value) {
            addTag(key, value, NBTTagType.getTagByClass(value.getClass()));
        }

        public void addTag(String key, Object value, NBTTagType tagtype) {
            switch (tagtype) {
                case STRING:
                    try {
                        Method m = Class.forName(NBTUtil.serverVersion + ".NBTTagCompound").getMethod("setString", String.class, NBTTagType.getClassByID(tagtype.getTagID()));
                        m.setAccessible(true);
                        m.invoke(this.tag, key, value);
                        m.setAccessible(false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case INT:
                    try {
                        Method m = Class.forName(NBTUtil.serverVersion + ".NBTTagCompound").getMethod("setInt", String.class, NBTTagType.getClassByID(tagtype.getTagID()));
                        m.setAccessible(true);
                        m.invoke(this.tag, key, value);
                        m.setAccessible(false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case SHORT:
                    try {
                        Method m = Class.forName(NBTUtil.serverVersion + ".NBTTagCompound").getMethod("setShort", String.class, NBTTagType.getClassByID(tagtype.getTagID()));
                        m.setAccessible(true);
                        m.invoke(this.tag, key, value);
                        m.setAccessible(false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case BOOLEAN:
                    try {
                        Method m = Class.forName(NBTUtil.serverVersion + ".NBTTagCompound").getMethod("set", String.class, NBTTagType.getClassByID(1));
                        m.setAccessible(true);
                        if ((boolean) value) m.invoke(this.tag, key, (byte) 1);
                        else m.invoke(this.tag, key, (byte) 0); // Done with byte to make compatibility easier
                        //m.invoke(this.tag, key, value);
                        m.setAccessible(false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case DOUBLE:
                    try {
                        Method m = Class.forName(NBTUtil.serverVersion + ".NBTTagCompound").getMethod("setDouble", String.class, NBTTagType.getClassByID(tagtype.getTagID()));
                        m.setAccessible(true);
                        m.invoke(this.tag, key, value);
                        m.setAccessible(false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case LONG:
                    try {
                        Method m = Class.forName(NBTUtil.serverVersion + ".NBTTagCompound").getMethod("setLong", String.class, NBTTagType.getClassByID(tagtype.getTagID()));
                        m.setAccessible(true);
                        m.invoke(this.tag, key, value);
                        m.setAccessible(false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    try {
                        Method m = Class.forName(NBTUtil.serverVersion + ".NBTTagCompound").getMethod("set", String.class, NBTTagType.getClassByID(tagtype.getTagID()));
                        m.setAccessible(true);
                        m.invoke(this.tag, key, value);
                        m.setAccessible(false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }

        public Long getLong(String key) {
            try {
                Method m = Class.forName(NBTUtil.serverVersion + ".NBTTagCompound").getMethod("getLong", String.class);
                m.setAccessible(true);
                Object o = m.invoke(this.tag, key);
                m.setAccessible(false);
                if (o instanceof Long) return (Long) o;
                else return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        public String getString(String key) {
            try {
                Method m = Class.forName(NBTUtil.serverVersion + ".NBTTagCompound").getMethod("getString", String.class);
                m.setAccessible(true);
                Object o = m.invoke(this.tag, key);
                m.setAccessible(false);
                if (o instanceof String) return (String) o;
                else return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        public boolean getBoolean(String key) {
            if (getByte(key) == (byte) 1) return true;
            else return false;
        }

        public Byte getByte(String key) {
            Object o = get(key);
            if (o instanceof Byte) return (byte) o;
            else return null;
        }

        public Object get(String key) {
            try {
                Method m = Class.forName(NBTUtil.serverVersion + ".NBTTagCompound").getMethod("get", String.class);
                m.setAccessible(true);
                Object o = m.invoke(this.tag, key);
                m.setAccessible(false);
                return o;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        public Object getCompound() {
            return this.tag;
        }

        public String toString() {
            return "NBT{" + this.tag + "}";
        }
    }

    private static String serverVersion = "net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    private static String craftServerVersion = "org.bukkit.craftbukkit." + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

    @Deprecated // Not done TODO: This
    public static HashMap<String, Object> getNBTFromItem(ItemStack i) {
        if (i == null || i.getType().equals(Material.AIR))
            throw new NullPointerException("ItemStack can't be null or air!");
        HashMap<String, Object> a = new HashMap<String, Object>();
        // TODO: Make a map of all nbt on an item
        return a;
    }

    public static ItemStack addTagsToItem(ItemStack i, NBTUtil.NBTTags tag) {
        if (i == null || i.getType().equals(Material.AIR))
            throw new NullPointerException("ItemStack can't be null or air!");
        try {
            Method m = Class.forName(craftServerVersion + ".inventory.CraftItemStack").getMethod("asNMSCopy", ItemStack.class);
            Method m2 = Class.forName(serverVersion + ".ItemStack").getMethod("setTag", Class.forName(serverVersion + ".NBTTagCompound"));
            Method m3 = Class.forName(craftServerVersion + ".inventory.CraftItemStack").getMethod("asBukkitCopy", Class.forName(serverVersion + ".ItemStack"));

            m.setAccessible(true);
            m2.setAccessible(true);
            m3.setAccessible(true);

            Object o = m.invoke(null, i);
            m2.invoke(o, tag.getCompound());
            Object o2 = m3.invoke(null, o);

            m.setAccessible(false);
            m2.setAccessible(false);
            m3.setAccessible(false);

            if (o2 instanceof ItemStack) return (ItemStack) o2;
            else return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static NBTUtil.NBTTags getTagsFromItem(ItemStack i) {
        if (i == null || i.getType().equals(Material.AIR))
            throw new NullPointerException("ItemStack can't be null or air!");
        try {
            Method m = Class.forName(craftServerVersion + ".inventory.CraftItemStack").getMethod("asNMSCopy", ItemStack.class);
            Method m2 = Class.forName(serverVersion + ".ItemStack").getMethod("getTag");

            m.setAccessible(true);
            m2.setAccessible(true);

            Object o = m.invoke(null, i);
            Object o2 = m2.invoke(o);

            m.setAccessible(false);
            m2.setAccessible(false);

            return new NBTUtil.NBTTags(o2);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
