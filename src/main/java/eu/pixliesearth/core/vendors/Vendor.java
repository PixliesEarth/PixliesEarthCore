package eu.pixliesearth.core.vendors;

import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import eu.pixliesearth.Main;
import eu.pixliesearth.core.machines.Machine;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.NBTUtil;
import lombok.Data;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@Data
public class Vendor {

    protected static final Main instance = Main.getInstance();

    private String npcId;
    private String title;
    private Map<ItemStack, Double> buyItems;
    private Map<ItemStack, Double> sellItems;

    public void open(Player player) {
        Profile profile = instance.getProfile(player.getUniqueId());
        ItemStack soldLast = new ItemBuilder(Material.BARRIER).setDisplayName("§c§lNO LAST SOLD ITEM!").build();
        if (profile.getExtras().containsKey("soldLast")) soldLast = (ItemStack) Machine.deserialize((String) profile.getExtras().get("soldLast"));
        Gui gui = new Gui(instance, 6, title);

        StaticPane pane = new StaticPane(0, 0, 9, 6);

        ItemStack placeholder = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setNoName().build();

        for (int i = 0; i < 9; i++) pane.addItem(new GuiItem(placeholder, event -> event.setCancelled(true)), i, 0);
        pane.addItem(new GuiItem(placeholder, event -> event.setCancelled(true)), 0, 1);
        pane.addItem(new GuiItem(placeholder, event -> event.setCancelled(true)), 8, 1);
        pane.addItem(new GuiItem(placeholder, event -> event.setCancelled(true)), 0, 2);
        pane.addItem(new GuiItem(placeholder, event -> event.setCancelled(true)), 8, 2);
        pane.addItem(new GuiItem(placeholder, event -> event.setCancelled(true)), 0, 3);
        pane.addItem(new GuiItem(placeholder, event -> event.setCancelled(true)), 8, 3);
        pane.addItem(new GuiItem(placeholder, event -> event.setCancelled(true)), 0, 4);
        pane.addItem(new GuiItem(placeholder, event -> event.setCancelled(true)), 8, 4);
        for (int i = 0; i < 9; i++) if (i != 4) pane.addItem(new GuiItem(placeholder, event -> event.setCancelled(true)), i, 5);

        if (soldLast.getType() == Material.BARRIER) pane.addItem(new GuiItem(soldLast, event -> event.setCancelled(true)), 4, 5); else {
            ItemStack finalSoldLast = soldLast;
            pane.addItem(new GuiItem(soldLast, event -> {
                event.setCancelled(true);
                boolean buy = buy(finalSoldLast, player);
                if (!buy) player.playSound(player.getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 1, 1);
            }), 4, 5);
        }


        gui.show(player);
    }

    protected boolean buy(ItemStack item, Player player) {
        //TODO
        return true;
    }

    protected boolean sell(ItemStack item, Player player) {
        //TODO
        return true;
    }

    protected Double getSellPriceFromItem(ItemStack item) {
        if (sellItems.containsKey(item)) return sellItems.get(item);
        NBTUtil.NBTTags tags = NBTUtil.getTagsFromItem(item);
        if (tags.getString("sellPrice") != null) return Double.parseDouble(tags.getString("sellPrice"));
        return 0.0;
    }

    protected Double getBuyPriceFromItem(ItemStack item) {
        if (buyItems.containsKey(item)) return buyItems.get(item);
        NBTUtil.NBTTags tags = NBTUtil.getTagsFromItem(item);
        if (tags.getString("buyPrice") != null) return Double.parseDouble(tags.getString("buyPrice"));
        return 0.0;
    }

}
