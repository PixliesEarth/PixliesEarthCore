package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;

import java.util.HashMap;
import java.util.Map;

public class FiredBrickBlock extends CustomRecipe {

    @Override
    public String getResultUUID() {
        return "Pixlies:Fired_Brick_Block";
    }

    @Override
    public int getResultAmount() {
        return 1;
    }

    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Fired_Brick");
        map.put(1, "Pixlies:Fired_Brick");
        map.put(2, "Pixlies:Fired_Brick");
        map.put(3, "Pixlies:Fired_Brick");
        map.put(4, "Pixlies:Fired_Brick");
        map.put(5, "Pixlies:Fired_Brick");
        map.put(6, "Pixlies:Fired_Brick");
        map.put(7, "Pixlies:Fired_Brick");
        map.put(8, "Pixlies:Fired_Brick");
        return map;
    }

}
