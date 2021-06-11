package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;

import java.util.HashMap;
import java.util.Map;

public class CustomCraftingTable extends CustomRecipe {

    @Override
    public String getResultUUID() {
        return "Pixlies:Crafting_Table";
    }

    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, MinecraftMaterial.REDSTONE.getUUID());
        map.put(1, MinecraftMaterial.STICK.getUUID());
        map.put(2, MinecraftMaterial.REDSTONE.getUUID());
        map.put(3, MinecraftMaterial.STICK.getUUID());
        map.put(4, MinecraftMaterial.CRAFTING_TABLE.getUUID());
        map.put(5, MinecraftMaterial.STICK.getUUID());
        map.put(6, MinecraftMaterial.REDSTONE.getUUID());
        map.put(7, MinecraftMaterial.STICK.getUUID());
        map.put(8, MinecraftMaterial.REDSTONE.getUUID());
        return map;
    }

    @Override
    public String craftedInUUID() {
        return MinecraftMaterial.CRAFTING_TABLE.getUUID();
    }
}
