package eu.pixliesearth.core.custom.recipes;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;

import java.util.HashMap;
import java.util.Map;

public class WireCopper extends CustomRecipe {

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
        return "Pixlies:Copper_Wire";
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
        map.put(0, "minecraft:air");
        map.put(1, "Pixlies:Copper_Dust");
        map.put(2, "minecraft:air");
        map.put(3, "Pixlies:Copper_Dust");
        map.put(4, "Pixlies:Copper_Ingot");
        map.put(5, "Pixlies:Copper_Dust");
        map.put(6, "minecraft:air");
        map.put(7, "Pixlies:Copper_Dust");
        map.put(8, "minecraft:air");
        return map;
    }

}
