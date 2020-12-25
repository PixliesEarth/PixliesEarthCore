package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;

import java.util.HashMap;
import java.util.Map;


public class OilFabricator extends CustomRecipe {
    //@Override
    public OilFabricator() {
        
    }
    @Override
    public String getResultUUID() {
        return "Machine:Oil_Fabricator";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Carbon_Steel_Ingot");
        map.put(1, "Pixlies:Heavy_Steel_Ingot");
        map.put(2, "Pixlies:Carbon_Steel_Ingot");
        map.put(3, MinecraftMaterial.IRON_BARS.getUUID());
        map.put(4, "Pixlies:Machine_Base");
        map.put(5, MinecraftMaterial.IRON_BARS.getUUID());
        map.put(6, "Pixlies:Carbon_Steel_Ingot");
        map.put(7, "Pixlies:Heavy_Steel_Ingot");
        map.put(8, "Pixlies:Carbon_Steel_Ingot");
        return map;
    }

}
