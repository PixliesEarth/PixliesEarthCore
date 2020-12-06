package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;

import java.util.HashMap;
import java.util.Map;

public class SolderDust extends CustomRecipe {

    @Override
    public String getResultUUID() {
        return "Pixlies:Solder_Dust";
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
        map.put(0, "Pixlies:Tin_Dust");
        map.put(1, "Pixlies:Lead_Dust");
        return map;
    }
    @Override
	public Long getCraftTime() {
		return 4000L;
	}
}