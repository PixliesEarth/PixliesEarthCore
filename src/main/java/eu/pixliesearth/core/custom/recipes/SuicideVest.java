package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;

import java.util.HashMap;
import java.util.Map;

public class SuicideVest extends CustomRecipe {

    @Override
    public String craftedInUUID() {
        return "Machine:GunWorkbench";
    }

    @Override
    public String getResultUUID() {
        return "Pixlies:Suicide_Vest";
    }

    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, MinecraftMaterial.TNT.getUUID());
        map.put(1, MinecraftMaterial.LEVER.getUUID());
        map.put(2, MinecraftMaterial.TNT.getUUID());
        map.put(3, MinecraftMaterial.REDSTONE.getUUID());
        map.put(4, MinecraftMaterial.DIAMOND_CHESTPLATE.getUUID());
        map.put(5, MinecraftMaterial.REDSTONE.getUUID());
        map.put(6, MinecraftMaterial.TNT.getUUID());
        map.put(7, MinecraftMaterial.REDSTONE.getUUID());
        map.put(8, MinecraftMaterial.TNT.getUUID());
        return map;
    }

}
