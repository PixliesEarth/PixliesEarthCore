package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;

import java.util.HashMap;
import java.util.Map;

public class BronzeIngot extends CustomRecipe {
	
	public BronzeIngot() {
		
	}

    @Override
    public String getResultUUID() {
        return "Pixlies:Bronze_Ingot";
    }
    
    @Override
    public String craftedInUUID() {
        return "Machine:Forge";
    }

    @Override
    public int getResultAmount() {
        return 1;
    }

    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
       // map.put(0, MinecraftMaterial.OAK_PLANKS.getUUID());
        map.put(0, "Pixlies:Tin_Dust");
        map.put(0, "Pixlies:Copper_Dust");
        return map;
    }
    @Override
    public Long getCraftTime() {
        return 4000L;
    }

}