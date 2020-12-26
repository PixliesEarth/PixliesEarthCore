package eu.pixliesearth.core.custom.recipes;

import java.util.HashMap;
import java.util.Map;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;

public class DonJulio extends CustomRecipe {

    @Override
    public String craftedInUUID() {
        return "Machine:Distillery";
    }
    /**
     * The UUID of the item to give when crafted
     *
     * <p>For minecraft items reference the {@link MinecraftMaterial} Enum like this:
     * <code>MinecraftMaterial.DIRT.getUUID();</code></p>
     */
    @Override
    public String getResultUUID() {
        return "Pixlies:DonJulio";
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
        map.put(0, MinecraftMaterial.SWEET_BERRIES.getUUID());
        map.put(1, MinecraftMaterial.SUGAR_CANE.getUUID());
        map.put(2, MinecraftMaterial.GOLD_NUGGET.getUUID());
        map.put(3, MinecraftMaterial.GLASS_BOTTLE.getUUID());
        return map;
    }
    /**
     * How long in ms it should to take to craft, 1000L = one second
     */
    @Override
    public Long getCraftTime() {
        return 5000L;
    }

}
