package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;

import java.util.HashMap;
import java.util.Map;

public class Distillery extends CustomRecipe {

    @Override
    public String getResultUUID() {
        return "Machine:Distillery";
    }

    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Carbon");
        map.put(1, MinecraftMaterial.STICK.getUUID());
        map.put(2, "Pixlies:Carbon");
        map.put(3, MinecraftMaterial.STICK.getUUID());
        map.put(4, MinecraftMaterial.BARREL.getUUID());
        map.put(5, MinecraftMaterial.STICK.getUUID());
        map.put(6, "Pixlies:Carbon");
        map.put(7, MinecraftMaterial.STICK.getUUID());
        map.put(8, "Pixlies:Carbon");
        return map;
    }

}
