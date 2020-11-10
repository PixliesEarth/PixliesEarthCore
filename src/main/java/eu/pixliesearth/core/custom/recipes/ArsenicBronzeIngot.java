package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;

import java.util.HashMap;
import java.util.Map;

public class ArsenicBronzeIngot extends CustomRecipe {
	
	public ArsenicBronzeIngot() {
		
	}
	
    @Override
    public String getResultUUID() {
        return "Pixlies:Arsenic_Bronze_Ingot";
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
        map.put(0, "Pixlies:Arsenic_Dust");
        map.put(1, "Pixlies:Copper_Dust");
        return map;
    }

}