package eu.pixliesearth.core.machines;

import eu.pixliesearth.utils.ItemBuilder;
import lombok.Data;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

@Data
public abstract class MachineCraftable {

    protected Type type;
    protected ItemStack menuItem;
    protected List<ItemStack> result;
    protected List<ItemStack> ingredients;

    public MachineCraftable(Type type, ItemStack menuItem, List<ItemStack> result, List<ItemStack> ingredients) {
        this.type = type;
        this.menuItem = menuItem;
        this.result = result;
        this.ingredients = ingredients;
    }

    public enum Type {

        CARPENTRY

    }

    public static final List<MachineCraftable> instances() {
        return Arrays.asList(

        );
    }

}
