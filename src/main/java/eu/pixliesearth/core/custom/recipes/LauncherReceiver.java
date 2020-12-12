package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;

import java.util.HashMap;
import java.util.Map;


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
        map.put(2, "Pixlies:Hard_Plastic");
        map.put(3, "Pixlies:Rubber");
        return map;
    }
    @Override
    public Long getCraftTime() { return 4000L; }
}
