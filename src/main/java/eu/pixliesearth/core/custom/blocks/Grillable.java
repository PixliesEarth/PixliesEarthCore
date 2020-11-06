package eu.pixliesearth.core.custom.blocks;

import eu.pixliesearth.utils.CustomItemUtil;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum Grillable {

    BEEF(new ItemStack(Material.BEEF), new ItemStack(Material.COOKED_BEEF), 2000),
    PORK(new ItemStack(Material.PORKCHOP), new ItemStack(Material.COOKED_PORKCHOP), 2000),
    MUTTON(new ItemStack(Material.MUTTON), new ItemStack(Material.COOKED_MUTTON), 2000),
    RABBIT(new ItemStack(Material.RABBIT), new ItemStack(Material.COOKED_RABBIT), 2000),
    CHICKEN(new ItemStack(Material.CHICKEN), new ItemStack(Material.COOKED_CHICKEN), 2000);

    private @Getter
    final ItemStack from;
    private @Getter final ItemStack to;
    private @Getter final long time;

    Grillable(ItemStack from, ItemStack to, long time) {
        this.from = from;
        this.to = to;
        this.time = time;
    }

    public static Grillable getByFrom(ItemStack item) {
        for (Grillable g : values())
            if (CustomItemUtil.getUUIDFromItemStack(g.from).equals(CustomItemUtil.getUUIDFromItemStack(item)))
                return g;
        return null;
    }

}
