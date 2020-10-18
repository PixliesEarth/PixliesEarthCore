package eu.pixliesearth.core.custom.recipes;

import java.util.HashMap;
import java.util.Map;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;

public class MudBrickBlock extends CustomRecipe {

    @Override
    public String getResultUUID() {
        return "Pixlies:Mud_Brick_Block";
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
        map.put(2, MinecraftMaterial.AIR.getUUID());
        map.put(3, "Pixlies:Mud_Brick");
        map.put(4, "Pixlies:Mud_Brick");
        map.put(5, MinecraftMaterial.AIR.getUUID());
        map.put(6, MinecraftMaterial.AIR.getUUID());
        map.put(7, MinecraftMaterial.AIR.getUUID());
        map.put(8, MinecraftMaterial.AIR.getUUID());
        return map;
    }

}
