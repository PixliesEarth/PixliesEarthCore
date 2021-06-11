package eu.pixliesearth.core.custom.recipes;

import java.util.HashMap;
import java.util.Map;

import eu.pixliesearth.core.custom.CustomRecipe;



public class NetherPlateBlock extends CustomRecipe {
    //@Override
    public NetherPlateBlock() {
        
    }
    @Override
    public String getResultUUID() {
        return "Pixlies:Nether_Plate_Block";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Nether_Plate");
        map.put(1, "Pixlies:Nether_Plate");
        map.put(2, "Pixlies:Nether_Plate");
        map.put(3, "Pixlies:Nether_Plate");
        map.put(4, "Pixlies:Nether_Plate");
        map.put(5, "Pixlies:Nether_Plate");
        map.put(6, "Pixlies:Nether_Plate");
        map.put(7, "Pixlies:Nether_Plate");
        map.put(8, "Pixlies:Nether_Plate");
        return map;
    }
    @Override
    public int getResultAmount() {
        return 1;
    }
    @Override
	public Long getCraftTime() {
		return 4000L;
	}
}
