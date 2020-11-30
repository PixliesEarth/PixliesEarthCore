package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;
import eu.pixliesearth.nations.entities.nation.Era;

import java.util.HashMap;
import java.util.Map;

public class CastIronBoots extends CustomRecipe {
	/**
	 * The UUID of what machine the recipe should be crafted in. These are:
	 * 
	 * <p>CustomCraftingTable = Pixlies:Crafting_Table</p>
	 * <p>Forge = Machine:Forge</p>
	 * <p>TinkerTable = Machine:TinkerTable</p>
	 * <p>FarmingWorkbench = Machine:FarmingWorkBench</p>
	 * <p>Pottery = Machine:Pottery</p>
	 */
	@Override
	public String craftedInUUID() {
		return "Pixlies:Crafting_Table";
	}
	/**
	 * The UUID of the item to give when crafted
	 * 
	 * <p>For minecraft items reference the {@link MinecraftMaterial} Enum like this:
	 * <code>MinecraftMaterial.DIRT.getUUID();</code></p>
	 */
	@Override
	public String getResultUUID() {
		return "Pixlies:Cast_Iron_Boots";
	}
	/**
	 * How many of the result to give
	 */
	@Override
	public int getResultAmount() {
		return 1;
	}
	/**
	 * The era required to craft the item
	 */
	@Override
	public Era getEraNeeded() {
		return Era.TRIBAL;
	}
	/**
	 * The contents of the recipe, If it is a Pixlies:Crafting_Table recipe then the Integer matters as it dictates the slot that the item is needed in.
	 * Else-wise it can be any number
	 * In machines to use the same item more then once you repeat the line with a different number for example:
	 * <code>
	 * map.put(0, "Pixlies:Rubber");
	 * map.put(1, "Pixlies:Rubber");
	 * map.put(2, "Pixlies:Rubber");
	 * </code>
	 * Would make the recipe require three rubber
	 */
	@Override
	public Map<Integer, String> getContentsList() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(0, "Minecraft:air");
		map.put(1, "Minecraft:air");
		map.put(2, "Minecraft:air");
		map.put(3, "Pixlies:Cast_Iron_Ingot");
		map.put(4, "Minecraft:air");
		map.put(5, "Pixlies:Cast_Iron_Ingot");
		map.put(6, "Pixlies:Cast_Iron_Ingot");
		map.put(7, "Minecraft:air");
		map.put(8, "Pixlies:Cast_Iron_Ingot");
		return map;
	}
	/**
	 * How long in ms it should to take to craft, 1000L = one second
	 */
	@Override
	public Long getCraftTime() {
		return 2000L;
	}
	/**
	 * How much energy this crafting process should take up, set to null if none
	 */
	@Override
	public Double getEnergyCost() {
		return null;
	}
	
}
