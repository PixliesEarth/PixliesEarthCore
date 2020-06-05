package eu.pixliesearth.core.objects;

import eu.pixliesearth.Main;
import eu.pixliesearth.utils.FileManager;
import eu.pixliesearth.localization.Lang;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class ShopItem {

    private ItemStack item;
    private double price;
    private ShopCategory category;

    //<editor-fold desc="Advanced methods">
    private static final FileManager cfg = Main.getInstance().getShopCfg();

    public void saveToConfig() {
        cfg.getConfiguration().set("items." + item.getItemMeta().getDisplayName() + ".item", item);
        cfg.getConfiguration().set("items." + item.getItemMeta().getDisplayName() + ".price", price);
        cfg.getConfiguration().set("items." + item.getItemMeta().getDisplayName() + ".category", category.name());
        cfg.save();
        cfg.reload();
    }

    public static ShopItem get(String name) {
        if (!cfg.getConfiguration().contains("items." + name)) return null;

        ItemStack item = cfg.getConfiguration().getItemStack("items." + name + ".item");
        double price = cfg.getConfiguration().getDouble("items." + name + ".price");
        ShopCategory category = ShopCategory.valueOf(cfg.getConfiguration().getString("items." + name + ".category"));

        return new ShopItem(item, price, category);
    }

    public void buy(Player player, int amount) {
        Profile profile = Main.getInstance().getProfile(player.getUniqueId());
        boolean withdraw = profile.withdrawMoney(price * amount, amount + "x " + item.getItemMeta().getDisplayName());
        if (!withdraw) {
            player.sendMessage(Lang.NOT_ENOUGH_MONEY.get(player));
            return;
        }
        for (int i = 1; i < amount; i++)
            player.getInventory().addItem(item);
        player.sendMessage(Lang.PURCHASED_ITEMS.get(player).replace("%AMOUNT%", amount+"").replace("%ITEM%", item.getItemMeta().getDisplayName()));
    }

    public static Set<ShopItem> getAll() {
        Set<ShopItem> returner = new HashSet<>();
        for (String s : cfg.getConfiguration().getConfigurationSection("items").getKeys(false)) {
            returner.add(get(s));
        }
        return returner;
    }

    public static Set<ShopItem> getAll(ShopCategory category) {
        Set<ShopItem> returner = new HashSet<>();
        for (String s : cfg.getConfiguration().getConfigurationSection("items").getKeys(false)) {
            ShopItem item = get(s);
            if (item.getCategory() == category)
                returner.add(item);
        }
        return returner;
    }
    //</editor-fold>

    public enum ShopCategory {
        BLOCKS,
        RESOURCES,
        REDSTONE,
        COLOR,
        FOOD
    }

}
