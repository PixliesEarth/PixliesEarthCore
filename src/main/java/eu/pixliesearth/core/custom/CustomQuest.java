package eu.pixliesearth.core.custom;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.NBTTagType;
/**
 * 
 * @author MickMMars
 * 
 * <h3>A class to create and manage custom quests</h3>
 *
 * <h4>Notes done by BradBot_1 with love <3</h4>
 *
 */
public class CustomQuest implements Listener {
	/**
	 * Initialises the class
	 */
    public CustomQuest() {

    }
    /**
     * @return The {@link CustomQuest}'s icon's name
     */
    public String getName() {
        return null;
    }
    /**
     * @return The {@link CustomQuest}'s icon's {@link Material}
     */
    public Material getMaterial() {
        return null;
    }
    /**
     * @return The {@link CustomQuest}'s icon's custom model data
     */
    public Integer getCustomModelData() {
        return null;
    }
    /**
     * @return The {@link CustomQuest}'s icon's Lore
     */
    public List<String> getLore() {
        return null;
    }
    /**
     * @return The Nation experience to give when the quest is completed
     */
    public Double getReward() {
        return null;
    }
    /**
     * @return The recipe UUID, this is used to differentiate between recipes
     */
    public String getUUID() { return ""; }
    /**
     * Builds and returns the quest icon
     * 
     * @return The {@link CustomQuest}'s icon
     */
    public ItemStack buildItem() {
        return new ItemBuilder(getMaterial()) {{
            if (getName() == null)
                setNoName();
            else
                setDisplayName(getName());
            if (getCustomModelData()!=null)
                setCustomModelData(getCustomModelData());
            addLoreLine("§f§lReward: §3" + getReward() + "§9N-XP");
            if (getLore() != null)
                addLoreAll(getLore());
            addNBTTag("UUID", getUUID(), NBTTagType.STRING);
        }}.build();
    }

}
