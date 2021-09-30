package eu.pixliesearth.core.auctionhouse;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import eu.pixliesearth.Main;
import eu.pixliesearth.core.custom.interfaces.Constants;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.utils.InventoryUtils;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.Timer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bson.Document;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record AuctionHouseInventory(Player player) {

    private static final Main instance = Main.getInstance();
    private static final MongoCollection<Document> auctionHouseCollection = Main.getAuctionHouseCollection();

    public AuctionHouseInventory open() {
        ChestGui gui = new ChestGui(6, "§bAuction House");

        PaginatedPane itemsPane = new PaginatedPane(0, 0, 9, 5);
        List<GuiItem> itemsFS = new ArrayList<>();
        for (Document document : auctionHouseCollection.find().sort(new BasicDBObject("dateAdded", -1))) {
            if ((document.getLong("dateAdded") + (Timer.DAY * 2)) < System.currentTimeMillis()) continue;
            ItemStack item = (ItemStack) InventoryUtils.deserialize(document.getString("item"));
            if (item == null) continue;
            OfflinePlayer seller = Bukkit.getOfflinePlayer(UUID.fromString(document.getString("seller")));
            itemsFS.add(new GuiItem(new ItemBuilder(item).clearLore().addLoreLine("§7Seller: §6" + seller.getName()).addLoreLine("§7Price: §2§l$§a" + document.getDouble("price")).addLoreLine("§7§oID: " + document.getObjectId("_id").toString()).addLoreLine(" ").addLoreLine("§f§lLEFT-CLICK §7to purchase").build(), event -> {
                event.setCancelled(true);
                buy(item, document.getDouble("price"), seller, document);
            }));
        }
        itemsPane.populateWithGuiItems(itemsFS);
        gui.addPane(itemsPane);

        StaticPane hotBar = new StaticPane(0, 5, 9, 1);
        hotBar.fillWith(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setNoName().build(), e -> e.setCancelled(true));
        hotBar.addItem(new GuiItem(Constants.backItem, event -> {
            event.setCancelled(true);
            try {
                itemsPane.setPage(itemsPane.getPage() - 1);
                gui.update();
            } catch (Exception ignored) { }
        }), 0, 0);
        hotBar.addItem(new GuiItem(Constants.nextItem, event -> {
            event.setCancelled(true);
            try {
                itemsPane.setPage(itemsPane.getPage() + 1);
                gui.update();
            } catch (Exception ignored) { }
        }), 8, 0);
        hotBar.addItem(new GuiItem(new ItemBuilder(Material.COMPASS).setDisplayName("§cSearch").build(), event -> {
            event.setCancelled(true);
            sendSearchMessage();
        }), 3, 0);
        hotBar.addItem(new GuiItem(new ItemBuilder(Material.COMPASS).setDisplayName("§bMy Items").build(), event -> {
            event.setCancelled(true);
            openManagementMenu();
        }), 5, 0);
        gui.addPane(hotBar);
        gui.show(player);
        return this;
    }

    public AuctionHouseInventory openSearch(String query) {
        ChestGui gui = new ChestGui(6, "§bAuction House §7- §3" + query);

        PaginatedPane itemsPane = new PaginatedPane(0, 0, 9, 5);
        List<GuiItem> itemsFS = new ArrayList<>();
        for (Document document : auctionHouseCollection.find(Filters.text(query)).sort(new BasicDBObject("dateAdded", -1))) {
            if ((document.getLong("dateAdded") + (Timer.DAY * 2)) < System.currentTimeMillis()) continue;
            ItemStack item = (ItemStack) InventoryUtils.deserialize(document.getString("item"));
            if (item == null) continue;
            OfflinePlayer seller = Bukkit.getOfflinePlayer(UUID.fromString(document.getString("seller")));
            itemsFS.add(new GuiItem(new ItemBuilder(item).clearLore().addLoreLine("§7Seller: §6" + seller.getName()).addLoreLine("§7Price: §2§l$§a" + document.getDouble("price")).addLoreLine("§7§oID: " + document.getObjectId("_id").toString()).addLoreLine(" ").addLoreLine("§f§lLEFT-CLICK §7to purchase").build(), event -> {
                event.setCancelled(true);
                buy(item, document.getDouble("price"), seller, document);
            }));
        }
        itemsPane.populateWithGuiItems(itemsFS);
        gui.addPane(itemsPane);

        StaticPane hotBar = new StaticPane(0, 5, 9, 1);
        hotBar.fillWith(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setNoName().build(), e -> e.setCancelled(true));
        hotBar.addItem(new GuiItem(Constants.backItem, event -> {
            event.setCancelled(true);
            try {
                itemsPane.setPage(itemsPane.getPage() - 1);
                gui.update();
            } catch (Exception ignored) { }
        }), 0, 0);
        hotBar.addItem(new GuiItem(Constants.nextItem, event -> {
            event.setCancelled(true);
            try {
                itemsPane.setPage(itemsPane.getPage() + 1);
                gui.update();
            } catch (Exception ignored) { }
        }), 8, 0);
        hotBar.addItem(new GuiItem(new ItemBuilder(Material.COMPASS).setDisplayName("§cSearch").build(), event -> {
            event.setCancelled(true);
            sendSearchMessage();
        }), 3, 0);
        hotBar.addItem(new GuiItem(new ItemBuilder(Material.COMPASS).setDisplayName("§bMy Items").build(), event -> {
            event.setCancelled(true);
            openManagementMenu();
        }), 5, 0);
        gui.addPane(hotBar);
        gui.show(player);
        return this;
    }

    public AuctionHouseInventory openManagementMenu() {
        ChestGui gui = new ChestGui(6, "§bAuction House §7- §9Management");

        PaginatedPane itemsPane = new PaginatedPane(0, 0, 9, 5);
        List<GuiItem> itemsFS = new ArrayList<>();
        for (Document document : auctionHouseCollection.find(new BasicDBObject("seller", player.getUniqueId().toString())).sort(new BasicDBObject("dateAdded", -1))) {
            ItemStack item = (ItemStack) InventoryUtils.deserialize(document.getString("item"));
            if (item == null) continue;
            OfflinePlayer seller = Bukkit.getOfflinePlayer(UUID.fromString(document.getString("seller")));
            ItemBuilder builder = new ItemBuilder(item).clearLore();
            if ((document.getLong("dateAdded") + (Timer.DAY * 2)) < System.currentTimeMillis()) builder.addLoreLine("§cEXPIRED");
            builder.addLoreLine("§7Price: §2§l$§a" + document.getDouble("price")).addLoreLine("§7§oID: " + document.getObjectId("_id").toString()).addLoreLine(" ").addLoreLine("§f§lLEFT-CLICK §7to §cremove");
            itemsFS.add(new GuiItem(builder.build(), event -> {
                event.setCancelled(true);
                auctionHouseCollection.deleteOne(document);
                for (ItemStack toDrop : player.getInventory().addItem(item).values())
                    player.getWorld().dropItemNaturally(player.getLocation(), toDrop);
                player.closeInventory();
            }));
        }
        itemsPane.populateWithGuiItems(itemsFS);
        gui.addPane(itemsPane);

        StaticPane hotBar = new StaticPane(0, 5, 9, 1);
        hotBar.fillWith(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setNoName().build(), e -> e.setCancelled(true));
        hotBar.addItem(new GuiItem(Constants.backItem, event -> {
            event.setCancelled(true);
            try {
                itemsPane.setPage(itemsPane.getPage() - 1);
                gui.update();
            } catch (Exception ignored) { }
        }), 0, 0);
        hotBar.addItem(new GuiItem(Constants.nextItem, event -> {
            event.setCancelled(true);
            try {
                itemsPane.setPage(itemsPane.getPage() + 1);
                gui.update();
            } catch (Exception ignored) { }
        }), 8, 0);
        gui.addPane(hotBar);
        gui.show(player);
        return this;
    }

    public void sendSearchMessage() {
        player.closeInventory();
        player.sendMessage(Component.text("Click me to search...").color(NamedTextColor.GREEN).hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT, Component.text("click me...").color(NamedTextColor.GRAY))).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/market search ")));
    }

    public void buy(ItemStack item, double price, OfflinePlayer seller, Document document) {
        player.closeInventory();
        if (player.getUniqueId().equals(seller.getUniqueId())) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
            return;
        }
        Profile profile = instance.getProfile(player.getUniqueId());
        Profile sellerProfile = instance.getProfile(seller.getUniqueId());
        boolean transfer = profile.transferMoney(price, "Purchase of " + item.getDisplayName() + " from " + seller.getName(), "Sold " + item.getDisplayName() + " to " + player.getName(), sellerProfile);
        if (!transfer) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
            return;
        }
        if (sellerProfile.isOnline()) {
            Player sellerPlayer = seller.getPlayer();
            sellerPlayer.sendMessage(Lang.EARTH + "§6" + profile.getAsPlayer() + " §7just bought §bx" + item.getAmount() + " " + document.getString("displayName") + " §7from you.");
            sellerPlayer.playSound(sellerPlayer.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
        }
        for (ItemStack toDrop : player.getInventory().addItem(item).values())
            player.getWorld().dropItemNaturally(player.getLocation(), toDrop);
        auctionHouseCollection.deleteOne(document);
    }

    public static void addItemToDatabase(ItemStack item, double price, Player seller) {
        Document document = new Document();
        document.put("displayName", item.hasDisplayName() ? item.getDisplayName() : item.getI18NDisplayName());
        document.put("material", item.getType().name());
        document.put("price", price);
        document.put("item", InventoryUtils.serialize(item));
        document.put("seller", seller.getUniqueId().toString());
        document.put("dateAdded", System.currentTimeMillis());
        auctionHouseCollection.insertOne(document);
    }

}
