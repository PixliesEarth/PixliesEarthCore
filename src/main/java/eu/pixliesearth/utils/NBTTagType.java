package eu.pixliesearth.utils;

import org.bukkit.Bukkit;

import java.util.List;

/**
 * @author BradBot_1
 * <p>
 * A enum used for controlling NBTTags with NBTUtil
 */
public enum NBTTagType {

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
    public Class<?> getTagClass() {
        return getClassByID(tagID);
    }

    /**
     * Gets a tag type from an id
     *
     * @param id The tag ID
     * @return The tag that correlates with the ID
     */
    public static NBTTagType getTagByID(int id) {
        switch (id) {
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
            default:
                return END;
        }
    }

    /**
     * Returns a class based on the NBT tag id
     *
     * @param id The NBTtag's internal minecraft id
     * @return The class the NBT is correlated to
     */
    public static Class<?> getClassByID(int id) {
        switch (id) {
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
            default:
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
            return Class.forName("net.minecraft.nbt.NBTTagCompound");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
