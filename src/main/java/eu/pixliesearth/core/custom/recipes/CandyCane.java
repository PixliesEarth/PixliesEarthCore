package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;

import java.util.HashMap;
import java.util.Map;

public class CandyCane extends CustomRecipe {

    @Override
    public String getResultUUID() {
        return "Christmas:Candy_Cane";
    }

    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, MinecraftMaterial.SUGAR_CANE.getUUID());
        map.put(1, MinecraftMaterial.SUGAR_CANE.getUUID());
        map.put(2, MinecraftMaterial.SUGAR_CANE.getUUID());
        map.put(3, MinecraftMaterial.SUGAR_CANE.getUUID());
        map.put(4, MinecraftMaterial.ICE.getUUID());
        map.put(5, MinecraftMaterial.SUGAR_CANE.getUUID());
        map.put(6, MinecraftMaterial.SUGAR_CANE.getUUID());
        map.put(7, MinecraftMaterial.SUGAR_CANE.getUUID());
        map.put(8, MinecraftMaterial.SUGAR_CANE.getUUID());
        return map;
    }

}
