package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;

import java.util.HashMap;
import java.util.Map;

public class ExplosivePickaxe extends CustomRecipe {

    @Override
    public String getResultUUID() {
        return "Pixlies:Explosive_Pickaxe";
    }

    @Override
    public int getResultAmount() {
        return 1;
    }

    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, MinecraftMaterial.TNT.getUUID());
        map.put(1, MinecraftMaterial.DIAMOND_PICKAXE.getUUID());
        map.put(2, MinecraftMaterial.TNT.getUUID());
        map.put(3, MinecraftMaterial.AIR.getUUID());
        map.put(4, MinecraftMaterial.BLAZE_ROD.getUUID());
        map.put(5, MinecraftMaterial.AIR.getUUID());
        map.put(6, MinecraftMaterial.AIR.getUUID());
        map.put(7, MinecraftMaterial.BLAZE_ROD.getUUID());
        map.put(8, MinecraftMaterial.AIR.getUUID());
        return map;
    }

}
