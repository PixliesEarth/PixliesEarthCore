package eu.pixliesearth.core.custom.recipes;

import java.util.HashMap;
import java.util.Map;

import eu.pixliesearth.core.custom.CustomRecipe;


public class LauncherReceiver extends CustomRecipe {
    //@Override
    public LauncherReceiver() {
    }
    @Override
    public String craftedInUUID() { return "Machine:Forge"; }
    @Override
    public String getResultUUID() { return "Pixlies:Launcher_Receiver"; }
    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Heavy_Steel_Ingot");
        map.put(1, "Pixlies:Stainless_Steel_Ingot");
        map.put(2, "Pixlies:Plastic_Hard");
        map.put(3, "Pixlies:Rubber");
        return map;
    }
    @Override
    public Long getCraftTime() { return 4000L; }
}
