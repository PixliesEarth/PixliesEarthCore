package eu.pixliesearth.pixliefun;

import eu.pixliesearth.Main;
import eu.pixliesearth.guns.guns.AK47;
import eu.pixliesearth.pixliefun.items.food.DonJulioTequilla;
import eu.pixliesearth.pixliefun.items.guns.RifleAmmo;
import eu.pixliesearth.pixliefun.items.guns.RifleBarrel;
import eu.pixliesearth.pixliefun.items.guns.RifleReceiver;
import eu.pixliesearth.pixliefun.items.guns.RifleStock;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.SkullCreator;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

public class PixlieFun {

    private static final Main instance = Main.getInstance();

    public static final RecipeType DISTILLERY = new RecipeType(new NamespacedKey(instance, "DISTILLERY"), new ItemBuilder(SkullCreator.itemFromUrl("https://textures.minecraft.net/texture/48437fee749cbd68f3443bb06e45058a687c0fca96436cadb538cad59b5216c6")).setDisplayName("§eDistillery").build());
    public static final RecipeType GUN_WORKBENCH = new RecipeType(new NamespacedKey(instance, "GUN_WORKBENCH"), new ItemBuilder(SkullCreator.itemFromUrl("https://textures.minecraft.net/texture/80a4334f6a61e40c0c63deb665fa7b581e6eb259f7a3207ced7a1ff8bdc8a9f9")).setDisplayName("§7Gun Workbench").build());

    public static Category pixlieFunCategory;
    public static Category gunsCategory;

    public PixlieFun setup() {
        ItemStack pfcIcon = new ItemBuilder(SkullCreator.itemFromUrl("https://textures.minecraft.net/texture/19c769083163e9aebd8ed5d66bebcb9df21cabae63baaaa0d3abe1420a4ab58f")).setDisplayName("§e§lPixlieFun").addLoreLine("§7§oCustom Pixlies stuff").build();
        pixlieFunCategory = new Category(new NamespacedKey(instance, "pixliefun_category"), pfcIcon);

        ItemStack gunsIcon = new ItemBuilder(AK47.buildItem()).clearLore().addLoreLine("§7§oExplore our variety of long range weapons!").build();
        gunsCategory = new Category(new NamespacedKey(instance, "guns_category"), gunsIcon);

        // REGISTER ITEMS
        new DonJulioTequilla().register(instance);

        new AK47().register(instance);

        new RifleBarrel().register(instance);
        new RifleStock().register(instance);
        new RifleReceiver().register(instance);
        new RifleAmmo().register(instance);

        // REGISTER CATEGORIES
        pixlieFunCategory.register(instance);
        gunsCategory.register(instance);

        return this;
    }

}
