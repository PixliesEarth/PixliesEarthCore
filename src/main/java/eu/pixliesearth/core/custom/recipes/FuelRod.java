package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;

import java.util.HashMap;
import java.util.Map;



public class FuelRod extends CustomRecipe {
    //@Override
    public FuelRod() {
        
    }
    @Override
    public String getResultUUID() {
        return "Pixlies:Fuel_Rod";
    }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Titanium_Ingot");
        map.put(1, "Pixlies:Canister_Oxygen");
        map.put(2, "Pixlies:Titanium_Ingot");
        map.put(3, "Pixlies:Canister_Hydrogen");
        map.put(4, "Pixlies:Canister_Oil");
        map.put(5, "Pixlies:Canister_Hydrogen");
        map.put(6, "Pixlies:Titanium_Ingot");
        map.put(7, "Pixlies:Canister_Oxygen");
        map.put(8, "Pixlies:Titanium_Ingot");
        return map;
    }
    @Override
	public Long getCraftTime() {
		return 4000L;
	}
}
