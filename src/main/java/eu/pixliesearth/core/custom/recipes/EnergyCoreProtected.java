package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;

import java.util.HashMap;
import java.util.Map;

public class EnergyCoreProtected extends CustomRecipe {

    @Override
    public String getResultUUID() {
        return "Pixlies:Energy_Core_Protected";
    }

    @Override
    public int getResultAmount() {
        return 1;
    }

    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Titanium_Block");
        map.put(1, "Pixlies:Rubber_Glass");
        map.put(2, "Pixlies:Titanium_Block");
        map.put(3, "Pixlies:Rubber_Glass");
        map.put(4, "Pixlies:Energy_Core_Unprotected");
        map.put(5, "Pixlies:Rubber_Glass");
        map.put(6, "Pixlies:Titanium_Block");
        map.put(7, "Pixlies:Rubber_Glass");
        map.put(8, "Pixlies:Titanium_Block");
        return map;
    }

}
