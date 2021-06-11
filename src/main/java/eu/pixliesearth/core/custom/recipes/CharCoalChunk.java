package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;

import java.util.HashMap;
import java.util.Map;

public class CharCoalChunk extends CustomRecipe {

    @Override
    public String getResultUUID() {
        return "Pixlies:CharcoalChunk";
    }

    @Override
    public String craftedInUUID() {
        return "Machine:TinkerTable";
    }

    @Override
    public int getResultAmount() {
        return 1;
    }

    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(9, MinecraftMaterial.CHARCOAL.getUUID());
        return map;
    }

}
