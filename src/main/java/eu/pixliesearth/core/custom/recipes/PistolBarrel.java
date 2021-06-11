package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;

import java.util.HashMap;
import java.util.Map;


public class PistolBarrel extends CustomRecipe {
    //@Override
    public PistolBarrel() {
    }
    @Override
    public String craftedInUUID() { return "Machine:Forge"; }
    @Override
    public String getResultUUID() { return "Pixlies:Pistol_Barrel"; }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Stainless_Steel_Ingot");
        map.put(1, "Pixlies:Stainless_Steel_Ingot");
        map.put(2, "Pixlies:Plastic_Hard");
        map.put(3, "Pixlies:Rubber");
        return map;
    }
    @Override
    public Long getCraftTime() { return 4000L; }
}
