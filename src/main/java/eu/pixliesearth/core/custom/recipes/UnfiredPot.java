package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;

import java.util.HashMap;
import java.util.Map;

public class UnfiredPot extends CustomRecipe {

    @Override
    public String getResultUUID() {
        return "Pixlies:Unfired_Pot";
    }
    
    @Override
    public String craftedInUUID() {
        return "Machine:Forge";
    }

    @Override
    public int getResultAmount() {
        return 1;
    }

    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, MinecraftMaterial.CLAY.getUUID());
        map.put(1, MinecraftMaterial.CLAY.getUUID());
        map.put(2, MinecraftMaterial.CLAY.getUUID());
        return map;
    }

}