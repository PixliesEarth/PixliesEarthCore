package eu.pixliesearth.core.custom.recipes;

import java.util.HashMap;
import java.util.Map;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;



public class BlockInspector extends CustomRecipe {
    //@Override
    public BlockInspector() {
        
    }
    @Override
    public String getResultUUID() {
        return "Pixlies:Block_Inspector";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Steel_Ingot");
        map.put(1, "Pixlies:Platinum_Ingot");
        map.put(2, "Pixlies:Steel_Ingot");
        map.put(3, "Pixlies:Steel_Ingot");
        map.put(4, MinecraftMaterial.NETHERITE_SCRAP.getUUID());
        map.put(5, "Pixlies:Steel_Ingot");
        map.put(6, "Pixlies:Steel_Ingot");
        map.put(7, "Pixlies:Platinum_Ingot");
        map.put(8, "Pixlies:Steel_Ingot");
        return map;
    }
    @Override
	public Long getCraftTime() {
		return 4000L;
	}
}
