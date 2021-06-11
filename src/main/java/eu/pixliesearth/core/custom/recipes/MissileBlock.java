package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;

import java.util.HashMap;
import java.util.Map;



public class MissileBlock extends CustomRecipe {
    //@Override
    public MissileBlock() {
        
    }
    @Override
    public String getResultUUID() {
        return "Pixlies:Missile_Block";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Steel_Block");
        map.put(1, "Pixlies:Lead_Ingot");
        map.put(2, "Pixlies:Steel_Block");
        map.put(3, "Pixlies:Steel_Block");
        map.put(4, "Pixlies:Circuit_Board");
        map.put(5, "Pixlies:Steel_Block");
        map.put(6, "Pixlies:Steel_Block");
        map.put(7, "Pixlies:Lead_Ingot");
        map.put(8, "Pixlies:Steel_Block");
        return map;
    }
    @Override
	public Long getCraftTime() {
		return 4000L;
	}
}
