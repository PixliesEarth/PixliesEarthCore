package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;

import java.util.HashMap;
import java.util.Map;

public class EnrichedUranium extends CustomRecipe {

    @Override
    public String getResultUUID() {
        return "Pixlies:Enriched_Uranium";
    }

    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Uranium_Chunk");
        map.put(1, "Pixlies:Uranium_Chunk");
        map.put(2, "Pixlies:Uranium_Chunk");
        map.put(3, "Pixlies:Uranium_Chunk");
        map.put(4, "Pixlies:Uranium_Chunk");
        map.put(5, "Pixlies:Uranium_Chunk");
        map.put(6, "Pixlies:Uranium_Chunk");
        map.put(7, "Pixlies:Uranium_Chunk");
        map.put(8, "Pixlies:Uranium_Chunk");
        return map;
    }

    @Override
    public String craftedInUUID() {
        return "Machine:Nuclear_Generator";
    }
}
