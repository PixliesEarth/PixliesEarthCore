package eu.pixliesearth.pixliefun;

import eu.pixliesearth.Main;
import eu.pixliesearth.pixliefun.items.DonJulioTequilla;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.SkullCreator;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

public class PixlieFun {

    private static final Main instance = Main.getInstance();

    public static final RecipeType DISTILLERY = new RecipeType(new NamespacedKey(instance, "distillery"), new ItemBuilder(SkullCreator.itemFromUrl("https://textures.minecraft.net/texture/48437fee749cbd68f3443bb06e45058a687c0fca96436cadb538cad59b5216c6")).setDisplayName("§eDistillery").build());
    public static Category pixlieFunCategory;

    public void setup() {
        ItemStack icon = new ItemBuilder(SkullCreator.itemFromUrl("https://textures.minecraft.net/texture/19c769083163e9aebd8ed5d66bebcb9df21cabae63baaaa0d3abe1420a4ab58f")).setDisplayName("§e§lPixlieFun").addLoreLine("§7§oCustom Pixlies stuff").build();
        pixlieFunCategory = new Category(new NamespacedKey(Main.getInstance(), "pixliefun_category"), icon);

        // REGISTER ITEMS
        new DonJulioTequilla().register(instance);

        pixlieFunCategory.register(instance);
    }

}
