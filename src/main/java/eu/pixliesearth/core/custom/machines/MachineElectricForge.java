package eu.pixliesearth.core.custom.machines;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Location;

import eu.pixliesearth.core.custom.CustomCrafterMachine;
import eu.pixliesearth.core.custom.CustomEnergyCrafterMachine;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.CustomRecipe;

public class MachineElectricForge extends CustomEnergyCrafterMachine { //TODO: make use fuel
	
	public MachineElectricForge() {
		
	}
	
	@Override
	public String getPlayerHeadUUID() {
		return "368ab8cf594da55f90f593bdc0b41e05925d75daa3ee7f1b49c2421d2bdead0";
	}
	
	@Override
    public String getDefaultDisplayName() {
        return "§6Electric Forge";
    }

    @Override
    public String getUUID() {
        return "Machine:Forge_Electric"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }

	@Override
	public double getCapacity() {
		return 150000D;
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
		for (CustomRecipe cr : CustomFeatureLoader.getLoader().getHandler().getRecipesFromUUID("Machine:Forge")) {
			list.add(cr.getResultUUID());
		}
		Collections.sort(list);
		for (String s : list) {
			list2.add(getRecipesOfUUIDInOrderedList(s));
		}
		return list2;
	}
}
