package eu.pixliesearth.core.auctionhouse;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import eu.pixliesearth.Main;
import eu.pixliesearth.core.custom.interfaces.Constants;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.utils.InventoryUtils;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.Timer;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
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
            itemsFS.add(new GuiItem(new ItemBuilder(item).clearLore().addLoreLine("§7Seller: §6" + seller.getName()).addLoreLine("§7Price: §2§l$§a" + document.getDouble("price")).addLoreLine("§7§oID: " + document.getObjectId("_id").toString()).build(), event -> {
                event.setCancelled(true);
                buy(item, document.getDouble("price"), seller, document);
            }));
        }
        itemsPane.populateWithGuiItems(itemsFS);
        gui.addPane(itemsPane);

        StaticPane hotBar = new StaticPane(0, 5, 9, 1);
        hotBar.addItem(new GuiItem(Constants.backItem, event -> {
            event.setCancelled(true);
            try {
                itemsPane.setPage(itemsPane.getPage() - 1);
                gui.update();
            } catch (Exception ignored) {
            }
        }), 0, 0);
        hotBar.addItem(new GuiItem(Constants.nextItem, event -> {
            event.setCancelled(true);
            try {
                itemsPane.setPage(itemsPane.getPage() + 1);
                gui.update();
            } catch (Exception ignored) {
            }
        }), 8, 0);
        hotBar.addItem(new GuiItem(new ItemBuilder(Material.COMPASS).setDisplayName("§cSearch").build(), event -> {
            event.setCancelled(true);
            //TODO: Search function
        }), 4, 0);
        gui.addPane(hotBar);
        gui.show(player);
        return this;
    }

    public void buy(ItemStack item, double price, OfflinePlayer seller, Document document) {
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
        for (ItemStack toDrop : player.getInventory().addItem(item).values())
            player.getWorld().dropItemNaturally(player.getLocation(), toDrop);
        auctionHouseCollection.deleteOne(document);
    }

}
