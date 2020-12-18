package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;

import java.util.HashMap;
import java.util.Map;


public class RifleBarrel extends CustomRecipe {
    //@Override
    public RifleBarrel() {
    }
    @Override
    public String craftedInUUID() { return "Machine:Forge"; }
    @Override
    public String getResultUUID() { return "Pixlies:Rifle_Barrel"; }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Carbon_Steel_Ingot");
        map.put(1, "Pixlies:Carbon_Steel_Ingot");
        map.put(2, "Pixlies:Titanium_Ingot");
        return map;
    }
    @Override
    public Long getCraftTime() { return 4000L; }
}