package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;

import java.util.HashMap;
import java.util.Map;

public class TESTRECIPE extends CustomRecipe {

    @Override
    public String getResultUUID() {
        return MinecraftMaterial.BEDROCK.getUUID();
    }
    
    @Override
    public String craftedInUUID() {
		return "Machine:Test";
	}

    @Override
    public int getResultAmount() {
        return 1;
    }

    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Arsenic_Bronze_Ingot");
        map.put(1, "Pixlies:Tin_Ingot");
        map.put(2, "Pixlies:Rubber");
        return map;
    }

}
