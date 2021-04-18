package eu.pixliesearth.core.vendors;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.InventoryUtils;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.Methods;
import lombok.Data;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static org.bukkit.Material.HEART_OF_THE_SEA;

@Data
public class Vendor {

    protected static final Main instance = Main.getInstance();

    private String npcName;
    private String title;
    private ItemStack[] items;

    public Vendor(String npcName, String title, ItemStack... items) {
        this.npcName = npcName;
        this.title = title;
        this.items = items;
    }

    public void open(Player player) {
        Profile profile = instance.getProfile(player.getUniqueId());
        ItemStack soldLast = new ItemBuilder(Material.BARRIER).setDisplayName("§c§lNO LAST SOLD ITEM!").build();
        if (profile.getExtras().containsKey("soldLast")) soldLast = new ItemBuilder((ItemStack) InventoryUtils.deserialize((String) profile.getExtras().get("soldLast"))).addLoreLine(getBuyPriceFromItem((ItemStack) InventoryUtils.deserialize((String) profile.getExtras().get("soldLast"))) != null ? "§7Buy: §2§l$§a" + getBuyPriceFromItem((ItemStack) InventoryUtils.deserialize((String) profile.getExtras().get("soldLast"))) : "§c§oUnpurchasable").addLoreLine(" ").addLoreLine("§f§lLEFT §7Click to buy").build();
        if (soldLast == null) return;
        ChestGui gui = new ChestGui(6, title);

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
                if (event.isLeftClick()) {
                    boolean buy = buy(getFromVendorReady(finalSoldLast), finalSoldLast, profile, finalSoldLast.getAmount());
                    if (buy) {
                        profile.getExtras().remove("soldLast");
                        profile.save();
                        open(player);
                    }
                    if (!buy) player.playSound(player.getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 1, 1);
                    else player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1, 1);
                }
            }), 4, 5);
        }

        PaginatedPane itemsPane = new PaginatedPane(1, 1, 7, 4);

        List<GuiItem> guiItems = new ArrayList<>();

        for (ItemStack item : items) {
            if (item == null) continue;
            ItemBuilder builder = new ItemBuilder(item);
            builder.addLoreLine(getBuyPriceFromItem(item) != null ? "§7Buy: §2§l$§a" + getBuyPriceFromItem(item) : "§c§oUnpurchasable");
            builder.addLoreLine(getSellPriceFromItem(item) != null ? "§7Sell: §2§l$§a" + getSellPriceFromItem(item) : "§c§oUnsellable");
            builder.addLoreLine(" ");
            builder.addLoreLine("§f§lLEFT §7Click to buy");
            builder.addLoreLine("§f§lRIGHT §7Click to sell");
            builder.addLoreLine("§f§lSHIFT §7Click to sell/buy §b64 §7items");
            item = builder.build();
            ItemStack finalItem = item;
            guiItems.add(new GuiItem(item, event -> {
                event.setCancelled(true);
                if (event.isLeftClick() && event.isShiftClick() && getBuyPriceFromItem(finalItem) != null) {
                    boolean buy = buy(getFromVendorReady(finalItem), finalItem, profile, 64);
                    if (!buy) player.playSound(player.getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 1, 1);else { player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1, 1);open(player); }
                } else if (event.isRightClick() && event.isShiftClick() && getSellPriceFromItem(finalItem) != null) {
                    boolean sell = sell(getFromVendorReady(finalItem), finalItem, profile, 64);
                    if (!sell) player.playSound(player.getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 1, 1); else { player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1, 1); open(player);}
                } else if (event.isLeftClick() && !event.isShiftClick() && getBuyPriceFromItem(finalItem) != null) {
                    boolean buy = buy(getFromVendorReady(finalItem), finalItem, profile, 1);
                    if (!buy) player.playSound(player.getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 1, 1); else { player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1, 1); open(player);}
                } else if (event.isRightClick() && !event.isShiftClick() && getSellPriceFromItem(finalItem) != null) {
                    boolean sell = sell(getFromVendorReady(finalItem), finalItem, profile, 1);
                    if (!sell) player.playSound(player.getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 1, 1); else { player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1, 1); open(player);}
                } else {
                    player.playSound(player.getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 1, 1);
                }
            }));
        }

        itemsPane.populateWithGuiItems(guiItems);

        StaticPane hotBar = new StaticPane(0, 5, 9, 1);
        hotBar.addItem(new GuiItem(new ItemBuilder(HEART_OF_THE_SEA).setDisplayName("§b§lNext Page").build(), event -> {
            event.setCancelled(true);
            try {
                itemsPane.setPage(itemsPane.getPage() + 1);
                gui.addPane(itemsPane);
                gui.show(player);
            } catch (Exception ignore) { }
        }), 8, 0);
        hotBar.addItem(new GuiItem(new ItemBuilder(HEART_OF_THE_SEA).setDisplayName("§b§lLast Page").build(), event -> {
            event.setCancelled(true);
            try {
                itemsPane.setPage(itemsPane.getPage() - 1);
                gui.addPane(itemsPane);
                gui.show(player);
            } catch (Exception ignore) { }
        }), 0, 0);
        gui.addPane(outline);
        gui.addPane(hotBar);
        gui.addPane(itemsPane);

        gui.show(player);
    }

    protected boolean buy(ItemStack item, ItemStack placeholderItem, Profile profile, int amount) {
        Player player = profile.getAsPlayer();
        double price = getBuyPriceFromItem(placeholderItem) * amount;
        boolean purchase = profile.withdrawMoney(price, title + " purchase of x" + amount + " " + item.getI18NDisplayName());
        if (!purchase) return false;
        item.setAmount(amount);
        if (player.getInventory().firstEmpty() == -1) player.getWorld().dropItemNaturally(player.getLocation(), item); else player.getInventory().addItem(item);
        int itemsPurchased = 0;
        if (profile.getExtras().containsKey("itemsPurchased")) itemsPurchased = Integer.parseInt((String) profile.getExtras().get("itemsPurchased"));
        itemsPurchased += amount;
        profile.getExtras().put("itemsPurchased", Integer.toString(itemsPurchased));
        profile.save();
        return true;
    }

    protected boolean sell(ItemStack item, ItemStack placeholderItem, Profile profile, int amount) {
        Player player = profile.getAsPlayer();
        double price = getSellPriceFromItem(placeholderItem) * amount;
        if (!player.getInventory().containsAtLeast(item, amount)) return false;
        item.setAmount(amount);
        Methods.removeRequiredAmount(item, player.getInventory());
        profile.depositMoney(price, title + " sale of x" + amount + " " + item.getI18NDisplayName());
        int itemsSold = 0;
        if (profile.getExtras().containsKey("itemSold")) itemsSold = Integer.parseInt((String) profile.getExtras().get("itemSold"));
        itemsSold += amount;
        profile.getExtras().put("itemSold", Integer.toString(itemsSold));
        profile.getExtras().put("soldLast", InventoryUtils.serialize(item));
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

    protected static ItemStack g(String uuid) {
        return CustomItemUtil.getItemStackFromUUID(uuid);
    }

}
