package eu.pixliesearth.core.custom;

import eu.pixliesearth.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CustomQuest implements Listener {

    public CustomQuest() {

    }

    public String getName() {
        return null;
    }

    public Material getMaterial() {
        return null;
    }

    public Integer getCustomModelData() {
        return null;
    }

    public List<String> getLore() {
        return null;
    }

    public Double getReward() {
        return null;
    }

    public String getUUID() { return ""; }

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
        }}.build();
    }

}
