package eu.pixliesearth.pixliefun;

import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.CustomRecipe;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author MickMMars
 * this is the class for the Mick version of the PixlieFun GUI
 */
public class PixlieFunGUI {

    private static final Map<String, List<CustomRecipe>> recipes;

    static {
        recipes = new HashMap<>();
        for (CustomRecipe r : CustomFeatureLoader.getLoader().getHandler().getCustomRecipes()) {
            recipes.putIfAbsent(r.getResultUUID(), new ArrayList<>());
            recipes.get(r.getResultUUID()).add(r);
        }

    }

    private Player player;

}
