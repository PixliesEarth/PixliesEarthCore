package eu.pixliesearth.core.vendors;

import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import eu.pixliesearth.Main;
import eu.pixliesearth.core.machines.Machine;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.Methods;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Data
@AllArgsConstructor
public class Vendor {

    protected static final Main instance = Main.getInstance();

    private int npcId;
    private String title;
    private List<ItemStack> items;

    public void open(Player player) {
        Profile profile = instance.getProfile(player.getUniqueId());
        ItemStack soldLast = new ItemBuilder(Material.BARRIER).setDisplayName("§c§lNO LAST SOLD ITEM!").build();
        if (profile.getExtras().containsKey("soldLast")) soldLast = new ItemBuilder((ItemStack) Machine.deserialize((String) profile.getExtras().get("soldLast"))).addLoreLine(getBuyPriceFromItem((ItemStack) Machine.deserialize((String) profile.getExtras().get("soldLast"))) != null ? "§fBuy: §2§l$§a" + getBuyPriceFromItem((ItemStack) Machine.deserialize((String) profile.getExtras().get("soldLast"))) : "§c§oUnpurchasable").addLoreLine(" ").addLoreLine("§7§oL-Click to buy").build();
        if (soldLast == null) return;
        Gui gui = new Gui(instance, 6, title);

        StaticPane outline = new StaticPane(0, 0, 9, 6);

        ItemStack placeholder = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setNoName().build();

        for (int i = 0; i < 9; i++) outline.addItem(new GuiItem(placeholder, event -> event.setCancelled(true)), i, 0);
        outline.addItem(new GuiItem(placeholder, event -> event.setCancelled(true)), 0, 1);
        outline.addItem(new GuiItem(placeholder, event -> event.setCancelled(true)), 8, 1);
        outline.addItem(new GuiItem(placeholder, event -> event.setCancelled(true)), 0, 2);
        outline.addItem(new GuiItem(placeholder, event -> event.setCancelled(true)), 8, 2);
        outline.addItem(new GuiItem(placeholder, event -> event.setCancelled(true)), 0, 3);
        outline.addItem(new GuiItem(placeholder, event -> event.setCancelled(true)), 8, 3);
        outline.addItem(new GuiItem(placeholder, event -> event.setCancelled(true)), 0, 4);
        outline.addItem(new GuiItem(placeholder, event -> event.setCancelled(true)), 8, 4);
        for (int i = 0; i < 9; i++) if (i != 4) outline.addItem(new GuiItem(placeholder, event -> event.setCancelled(true)), i, 5);

        if (soldLast.getType() == Material.BARRIER) outline.addItem(new GuiItem(soldLast, event -> event.setCancelled(true)), 4, 5); else {
            ItemStack finalSoldLast = soldLast;
            outline.addItem(new GuiItem(soldLast, event -> {
                event.setCancelled(true);
                boolean buy = buy(getFromVendorReady(finalSoldLast), finalSoldLast, profile);
                if (buy) {
                    profile.getExtras().remove("soldLast");
                    profile.save();
                    open(player);
                }
                if (!buy) player.playSound(player.getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 1, 1); else player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1, 1);
            }), 4, 5);
        }

        StaticPane itemsPane = new StaticPane(1, 1, 7, 4);

        int x = 0;
        int y = 0;
        for (ItemStack item : items) {
            ItemBuilder builder = new ItemBuilder(item);
            builder.addLoreLine(getBuyPriceFromItem(item) != null ? "§fBuy: §2§l$§a" + getBuyPriceFromItem(item) : "§c§oUnpurchasable");
            builder.addLoreLine(getSellPriceFromItem(item) != null ? "§fSell: §2§l$§a" + getSellPriceFromItem(item) : "§c§oUnsellable");
            builder.addLoreLine(" ");
            builder.addLoreLine("§7§oL-Click to buy");
            builder.addLoreLine("§7§oR-Click to sell");
            item = builder.build();
            if (x + 1 > 7) {
                x = 0;
                y++;
            }
            ItemStack finalItem = item;
            itemsPane.addItem(new GuiItem(item, event -> {
                event.setCancelled(true);
                if (event.isLeftClick() && getBuyPriceFromItem(finalItem) != null) {
                    boolean buy = buy(getFromVendorReady(finalItem), finalItem, profile);
                    if (!buy) player.playSound(player.getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 1, 1); else { player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1, 1); open(player);}
                } else if (event.isRightClick() && getSellPriceFromItem(finalItem) != null) {
                    boolean sell = sell(getFromVendorReady(finalItem), finalItem, profile);
                    if (!sell) player.playSound(player.getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 1, 1); else { player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1, 1); open(player);}
                }
            }), x, y);
            x++;
        }

        gui.addPane(outline);
        gui.addPane(itemsPane);

        gui.show(player);
    }

    protected boolean buy(ItemStack item, ItemStack placeholderItem, Profile profile) {
        Player player = profile.getAsPlayer();
        double price = getBuyPriceFromItem(placeholderItem);
        boolean purchase = profile.withdrawMoney(price, title + " purchase of " + item.getI18NDisplayName());
        if (!purchase) return false;
        if (player.getInventory().firstEmpty() == -1) player.getWorld().dropItemNaturally(player.getLocation(), item); else player.getInventory().addItem(item);
        int itemsPurchased = 0;
        if (profile.getExtras().containsKey("itemsPurchased")) itemsPurchased = Integer.parseInt((String) profile.getExtras().get("itemsPurchased"));
        itemsPurchased++;
        profile.getExtras().put("itemsPurchased", Integer.toString(itemsPurchased));
        profile.save();
        return true;
    }

    protected boolean sell(ItemStack item, ItemStack placeholderItem, Profile profile) {
        Player player = profile.getAsPlayer();
        double price = getSellPriceFromItem(placeholderItem);
        if (!player.getInventory().containsAtLeast(item, 1)) return false;
        Methods.removeRequiredAmount(item, player.getInventory());
        profile.depositMoney(price, title + " sale of " + item.getI18NDisplayName());
        int itemsPurchased = 0;
        if (profile.getExtras().containsKey("itemSold")) itemsPurchased = Integer.parseInt((String) profile.getExtras().get("itemSold"));
        itemsPurchased++;
        profile.getExtras().put("itemSold", Integer.toString(itemsPurchased));
        profile.getExtras().put("soldLast", Machine.serialize(item));
        profile.save();
        return true;
    }

    protected Double getSellPriceFromItem(ItemStack item) {
        VendorItem vendorItem = instance.getVendorItems().get(CustomItemUtil.getUUIDFromItemStack(item));
        if (vendorItem != null) return vendorItem.getSellPrice();
        return null;
    }

    protected Double getBuyPriceFromItem(ItemStack item) {
        VendorItem vendorItem = instance.getVendorItems().get(CustomItemUtil.getUUIDFromItemStack(item));
        if (vendorItem != null) return vendorItem.getBuyPrice();
        return null;
    }

    protected ItemStack getFromVendorReady(ItemStack item) {
        VendorItem vendorItem = instance.getVendorItems().get(CustomItemUtil.getUUIDFromItemStack(item));
        if (vendorItem != null) return vendorItem.getRawItemStack();
        return null;
    }

}
