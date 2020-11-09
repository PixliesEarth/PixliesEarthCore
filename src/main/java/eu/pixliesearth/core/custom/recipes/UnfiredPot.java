package eu.pixliesearth.core.custom.recipes;

import java.util.HashMap;
import java.util.Map;

import eu.pixliesearth.core.custom.CustomRecipe;

public class UnfiredPot extends CustomRecipe {

    @Override
    public String getResultUUID() {
        return "Pixlies:Unfired_Pot";
    }
    
    @Override
    public String craftedInUUID() {
        return "Machine:Pottery";
    }

    @Override
    public int getResultAmount() {
        return 1;
    }

    @Override
    public Map<Integer, String> getContentsList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Pixlies:Mud_Brick");
        map.put(1, "Pixlies:Mud_Brick");
        map.put(2, "Pixlies:Mud_Brick");
        map.put(3, "Pixlies:Mud_Brick");
        return map;
    }

}