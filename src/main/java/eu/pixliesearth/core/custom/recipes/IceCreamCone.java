package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;

import java.util.HashMap;
import java.util.Map;

public class IceCreamCone extends CustomRecipe {

    @Override
    public String getResultUUID() {
        return "Food:IceCreamCone";
    }

    @Override
    public String craftedInUUID() {
        return "Machine:Refrigerator";
    }

    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, MinecraftMaterial.MILK_BUCKET.getUUID());
        map.put(1, MinecraftMaterial.ICE.getUUID());
        map.put(2, "Food:Waffle");
        return map;
    }

}
