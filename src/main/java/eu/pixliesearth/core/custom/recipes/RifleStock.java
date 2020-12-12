package eu.pixliesearth.core.custom.recipes;

import java.util.HashMap;
import java.util.Map;

import eu.pixliesearth.core.custom.CustomRecipe;


public class RifleStock extends CustomRecipe {
    //@Override
    public RifleStock() {
    }
    @Override
    public String craftedInUUID() { return "Machine:Forge"; }
    @Override
    public String getResultUUID() { return "Pixlies:Rifle_Stock"; }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Aluminium_Ingot");
        map.put(1, "Pixlies:Carbon_Steel_Ingot");
        map.put(2, "Pixlies:Carbon_Steel_Ingot");
        return map;
    }
    @Override
    public Long getCraftTime() { return 4000L; }
}
