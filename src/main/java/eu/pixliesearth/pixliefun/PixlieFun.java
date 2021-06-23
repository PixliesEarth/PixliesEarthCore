package eu.pixliesearth.pixliefun;

import eu.pixliesearth.Main;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.SkullCreator;
import me.mrCookieSlime.Slimefun.Objects.Category;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

public class PixlieFun {

    private static final Main instance = Main.getInstance();

    public void start() {
        ItemStack icon = new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/19c769083163e9aebd8ed5d66bebcb9df21cabae63baaaa0d3abe1420a4ab58f")).setDisplayName("§e§lPixlieFun").addLoreLine("§7§oCustom Pixlies stuff").build();
        Category category = new Category(new NamespacedKey(Main.getInstance(), "pixliefun_category"), icon);

        category.register(instance);
    }

}
