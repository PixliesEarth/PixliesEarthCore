package eu.pixliesearth.core.custom.machines;

import eu.pixliesearth.core.custom.CustomCrafterMachine;
import eu.pixliesearth.core.custom.CustomEnergyCrafterMachine;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.CustomRecipe;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MachineRefrigerator extends CustomEnergyCrafterMachine {

    @Override
    public Material getMaterial() {
        return Material.IRON_BLOCK;
    }

    @Override
    public String getDefaultDisplayName() {
        return "§bRefrigerator";
    }

    @Override
    public String getUUID() {
        return "Machine:Refrigerator"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }

    @Override
    public double getCapacity() {
        return 150_000D;
    }
    /**
     * Called to check if the item can be crafted
     *
     * @param location The {@link CustomCrafterMachine} {@link Location}
     * @param customRecipe The current {@link CustomRecipe}
     * @return If the crafting can go ahead
     */
    @Override
    public boolean hasCost(Location location, CustomRecipe customRecipe) {
        return getContainedPower(location)>=150D;
    }
    /**
     * Called to take the cost of the crafting
     *
     * @param location The {@link CustomCrafterMachine} {@link Location}
     * @param customRecipe The current {@link CustomRecipe}
     */
    @Override
    public void takeCost(Location location, CustomRecipe customRecipe) {
        CustomFeatureLoader.getLoader().getHandler().removePowerFromLocation(location, 150D);
    }

    // HACKY STUFF TO GET IT LOAD FORGES RECIPES

    @Override
    public List<List<CustomRecipe>> getRecipes() {
        List<String> list = new ArrayList<>();
        List<List<CustomRecipe>> list2 = new ArrayList<>();
        for (CustomRecipe cr : CustomFeatureLoader.getLoader().getHandler().getRecipesFromUUID("Machine:Refrigerator")) {
            list.add(cr.getResultUUID());
        }
        Collections.sort(list);
        for (String s : list) {
            list2.add(getRecipesOfUUIDInOrderedList(s));
        }
        return list2;
    }

}
