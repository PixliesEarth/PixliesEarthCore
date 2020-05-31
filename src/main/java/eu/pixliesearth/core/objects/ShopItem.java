package eu.pixliesearth.core.objects;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.utils.FileManager;
import lombok.AllArgsConstructor;
import lombok.Data;
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
    //</editor-fold>

    public enum ShopCategory {

    }

}
