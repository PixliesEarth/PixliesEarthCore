package eu.pixliesearth.core.objects;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.utils.FileManager;
import eu.pixliesearth.localization.Lang;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Data
@AllArgsConstructor
public class ShopItem {

    private ItemStack item;
    private double price;
    private ShopCategory category;

    //<editor-fold desc="Advanced methods">
    private static final FileManager cfg = Main.getInstance().getShopCfg();

    public void saveToConfig() {
        cfg.getConfiguration().set(item.getItemMeta().getDisplayName() + ".item", item);
        cfg.getConfiguration().set(item.getItemMeta().getDisplayName() + ".price", price);
        cfg.getConfiguration().set(item.getItemMeta().getDisplayName() + ".category", category.name());
        cfg.save();
        cfg.reload();
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
    //</editor-fold>

    public enum ShopCategory {

    }

}
