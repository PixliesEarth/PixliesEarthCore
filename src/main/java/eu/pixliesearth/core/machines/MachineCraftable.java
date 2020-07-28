package eu.pixliesearth.core.machines;

import eu.pixliesearth.utils.ItemBuilder;
import lombok.Data;
import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

public enum MachineCraftable {

    CUT_WOOD(Type.CARPENTRY, new ItemBuilder(Material.OAK_LOG).setAmount(64).setDisplayName("Cut Wood").addLoreLine("§a32 §7oak-logs > §a4x64 §7oak-planks").build(), Arrays.asList(new ItemBuilder(Material.OAK_LOG).setAmount(32).build()), Arrays.asList(new ItemStack(Material.OAK_LOG, 64), new ItemStack(Material.OAK_LOG, 64), new ItemStack(Material.OAK_LOG, 64), new ItemStack(Material.OAK_LOG, 64)));

    protected Type type;
    protected ItemStack menuItem;
    protected List<ItemStack> result;
    protected List<ItemStack> ingredients;

    MachineCraftable(Type type, ItemStack menuItem, List<ItemStack> result, List<ItemStack> ingredients) {
        this.type = type;
        this.menuItem = menuItem;
        this.result = result;
        this.ingredients = ingredients;
    }

    public enum Type {

        CARPENTRY

    }

}
