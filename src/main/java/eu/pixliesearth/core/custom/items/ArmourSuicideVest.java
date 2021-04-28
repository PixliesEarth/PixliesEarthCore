package eu.pixliesearth.core.custom.items;

import eu.pixliesearth.core.custom.CustomArmour;
import eu.pixliesearth.core.custom.CustomItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import java.util.*;

public class ArmourSuicideVest extends CustomArmour {

    @Override
    public Material getMaterial() {
        return Material.LEATHER_CHESTPLATE;
    }

    @Override
    public List<String> getDefaultLore() {
        return Arrays.asList(
                "§7§oBlock up your enemies in a distance of 12 blocks",
                " ",
                "§f§lRIGHT-CLICK §7to detonate"
        );
    }

    @Override
    public String getDefaultDisplayName() {
        return "§c§lSuicide Vest";
    }

    @Override
    public Map<String, Object> getDefaultNBT() {
        return new HashMap<String, Object>();
    }

    @Override
    public Map<Enchantment, Integer> getDefaultEnchants() {
        return new HashMap<Enchantment, Integer>(){{
            put(Enchantment.VANISHING_CURSE, 1);
        }};
    }

    @Override
    public CustomItem.CreativeTabs getCreativeTab() {
        return CustomItem.CreativeTabs.COMBAT;
    }

    @Override
    public String getUUID() {
        return "Pixlies:Suicide_Vest"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }

}
