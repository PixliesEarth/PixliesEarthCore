package eu.pixliesearth.pixliefun;

import eu.pixliesearth.Main;
import eu.pixliesearth.guns.guns.AK47;
import eu.pixliesearth.guns.guns.M1911;
import eu.pixliesearth.pixliefun.items.PixlieFunItems;
import eu.pixliesearth.pixliefun.items.food.Beer;
import eu.pixliesearth.pixliefun.items.food.DonJulioTequilla;
import eu.pixliesearth.pixliefun.items.food.Rum;
import eu.pixliesearth.pixliefun.items.food.Wine;
import eu.pixliesearth.pixliefun.items.guns.*;
import eu.pixliesearth.pixliefun.machine.Distillery;
import eu.pixliesearth.pixliefun.machine.GunWorkbench;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.SkullCreator;
import io.github.thebusybiscuit.slimefun4.api.items.groups.NestedItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.groups.SubItemGroup;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

public class PixlieFun {

    private static final Main instance = Main.getInstance();

    public static final RecipeType DISTILLERY = new RecipeType(new NamespacedKey(instance, "distillery"), PixlieFunItems.DISTILLERY);
    public static final RecipeType GUN_WORKBENCH = new RecipeType(new NamespacedKey(instance, "gun_workbench"), PixlieFunItems.GUN_WORKBENCH);

    public static NestedItemGroup pixlieFunCategory;
    public static SubItemGroup foodAndDrinks;
    public static SubItemGroup gunsCategory;
    public static SubItemGroup machinesCategory;
    public static SubItemGroup trafficCategory;

    public PixlieFun setup() {
        ItemStack pfcIcon = new ItemBuilder(SkullCreator.itemFromUrl("https://textures.minecraft.net/texture/19c769083163e9aebd8ed5d66bebcb9df21cabae63baaaa0d3abe1420a4ab58f")).setDisplayName("§e§lPixlieFun").addLoreLine("§7§oCustom Pixlies stuff").build();
        pixlieFunCategory = new NestedItemGroup(new NamespacedKey(instance, "pixliefun_category"), pfcIcon);

        ItemStack foodAndDrinksIcon = new ItemBuilder(PixlieFunItems.DONJULIO_TEQUILA).clearLore().setDisplayName("§dFood and Drinks").build();
        foodAndDrinks = new SubItemGroup(new NamespacedKey(instance, "pixliefun_food_and_drinks"), pixlieFunCategory, foodAndDrinksIcon);

        ItemStack gunsIcon = new ItemBuilder(AK47.buildItem()).setDisplayName("§c§lGuns").clearLore().addLoreLine("§7§oExplore our variety of long range weapons!").build();
        gunsCategory = new SubItemGroup(new NamespacedKey(instance, "guns_category"), pixlieFunCategory, gunsIcon);

        ItemStack machinesIcon = new ItemBuilder(PixlieFunItems.GUN_WORKBENCH).clearLore().setDisplayName("§cMachines").build();
        machinesCategory = new SubItemGroup(new NamespacedKey(instance, "pixliefun_machines"), pixlieFunCategory, machinesIcon);

        // REGISTER CATEGORIES
        pixlieFunCategory.register(instance);
        foodAndDrinks.register(instance);
        gunsCategory.register(instance);
        machinesCategory.register(instance);

        // REGISTER ITEMS
        new DonJulioTequilla().register(instance);
        new Beer().register(instance);
        new Wine().register(instance);
        new Rum().register(instance);

        new AK47().register(instance);
        new M1911().register(instance);

        new RifleBarrel().register(instance);
        new RifleStock().register(instance);
        new RifleReceiver().register(instance);
        new RifleAmmo().register(instance);
        new PistolBarrel().register(instance);
        new PistolReceiver().register(instance);

        new Distillery().register(instance);
        new GunWorkbench().register(instance);

        return this;
    }

}
