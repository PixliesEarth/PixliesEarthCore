package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;

import java.util.HashMap;
import java.util.Map;


public class RifleReceiver extends CustomRecipe {
    //@Override
    public RifleReceiver() {
    }
    @Override
    public String craftedInUUID() { return "Machine:Forge"; }
    @Override
    public String getResultUUID() { return "Pixlies:Rifle_Receiver"; }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Stainless_Steel_Ingot");
        map.put(1, "Pixlies:Heavy_Steel_Ingot");
        map.put(2, "Pixlies:Plastic_hard");
        map.put(3, "Pixlies:Rubber");
        return map;
    }
    @Override
    public Long getCraftTime() { return 4000L; }
}
