package eu.pixliesearth.core.custom.recipes;

import java.util.HashMap;
import java.util.Map;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;



public class NuclearBomb extends CustomRecipe {
    //@Override
    public NuclearBomb() {
        
    }
    @Override
    public String getResultUUID() {
        return "Pixlies:Nuclear_Bomb";
    }
    @Override 
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, MinecraftMaterial.NETHER_STAR.getUUID());
        map.put(1, "Pixlies:Uranium_Chunk");
        map.put(2, MinecraftMaterial.NETHER_STAR.getUUID());
        map.put(3, "Pixlies:Uranium_Chunk");
        map.put(4, MinecraftMaterial.TNT.getUUID());
        map.put(5, "Pixlies:Uranium_Chunk");
        map.put(6, MinecraftMaterial.NETHER_STAR.getUUID());
        map.put(7, "Pixlies:Uranium_Chunk");
        map.put(8, MinecraftMaterial.NETHER_STAR.getUUID());
        return map;
    }
}
