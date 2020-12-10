package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;

import java.util.HashMap;
import java.util.Map;

public class EnergyCoreUnprotected extends CustomRecipe {

    @Override
    public String getResultUUID() {
        return "Pixlies:Energy_Core_Unrotected";
    }

    @Override
    public int getResultAmount() {
        return 1;
    }

    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, MinecraftMaterial.GLASS.getUUID());
        map.put(1, "Pixlies:Cobalt_Block");
        map.put(2, MinecraftMaterial.GLASS.getUUID());
        map.put(3, "Pixlies:Cobalt_Block");
        map.put(4, MinecraftMaterial.END_CRYSTAL.getUUID());
        map.put(5, "Pixlies:Cobalt_Block");
        map.put(6, MinecraftMaterial.GLASS.getUUID());
        map.put(7, "Pixlies:Cobalt_Block");
        map.put(8, MinecraftMaterial.GLASS.getUUID());
        return map;
    }

}
