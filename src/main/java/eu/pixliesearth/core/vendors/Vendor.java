package eu.pixliesearth.core.vendors;

import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import eu.pixliesearth.Main;
import eu.pixliesearth.core.machines.Machine;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.Methods;
import eu.pixliesearth.utils.NBTTagType;
import eu.pixliesearth.utils.NBTUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@Data
@AllArgsConstructor
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
                boolean buy = buy(finalSoldLast, profile);
                if (buy) {
                    profile.getExtras().remove("soldLast");
                    profile.save();
                }
                if (!buy) player.playSound(player.getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 1, 1); else player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1, 1);
            }), 4, 5);
        }

        StaticPane items = new StaticPane(1, 1, 7, 4);

        int x = 0;
        int y = 0;
        for (ItemStack item : buyItems.keySet()) {
            ItemBuilder builder = new ItemBuilder(item);
            builder.addLoreLine("§fBuy: §2§l$§a" + buyItems.get(item));
            builder.addLoreLine("§fSell: §2§l$§a" + sellItems.get(item));
            builder.addLoreLine(" ");
            builder.addLoreLine("§7§oL-Click to buy");
            builder.addLoreLine("§7§oR-Click to sell");
            item = builder.build();
            NBTUtil.NBTTags tags = NBTUtil.getTagsFromItem(item);
            tags.addTag("buyPrice", buyItems.get(item), NBTTagType.DOUBLE);
            tags.addTag("sellPrice", sellItems.get(item), NBTTagType.DOUBLE);
            NBTUtil.addTagsToItem(item, tags);
            if (x + 1 > 7) {
                x = 0;
                y++;
            }
            ItemStack finalItem = item;
            items.addItem(new GuiItem(item, event -> {
                event.setCancelled(true);
                if (event.isLeftClick()) {
                    boolean buy = buy(finalItem, profile);
                    if (!buy) player.playSound(player.getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 1, 1); else player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1, 1);
                } else if (event.isRightClick()) {
                    boolean sell = sell(finalItem, profile);
                    if (!sell) player.playSound(player.getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 1, 1); else player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1, 1);
                }
            }), x, y);
            x++;
        }

        gui.addPane(outline);
        gui.addPane(items);

        gui.show(player);
    }

    protected boolean buy(ItemStack item, Profile profile) {
        Player player = profile.getAsPlayer();
        double price = getBuyPriceFromItem(item);
        boolean purchase = profile.withdrawMoney(price, title + " purchase of " + item.getI18NDisplayName());
        if (!purchase) return false;
        if (player.getInventory().firstEmpty() == -1) player.getWorld().dropItemNaturally(player.getLocation(), item); else player.getInventory().addItem(item);
        int itemsPurchased = 0;
        if (profile.getExtras().containsKey("itemsPurchased")) itemsPurchased = (int) profile.getExtras().get("itemsPurchased");
        itemsPurchased++;
        profile.getExtras().put("itemsPurchased", itemsPurchased);
        profile.save();
        return true;
    }

    protected boolean sell(ItemStack item, Profile profile) {
        Player player = profile.getAsPlayer();
        double price = getSellPriceFromItem(item);
        if (!player.getInventory().containsAtLeast(item, 1)) return false;
        Methods.removeRequiredAmount(item, player.getInventory());
        profile.depositMoney(price, title + " sale of " + item.getI18NDisplayName());
        int itemsPurchased = 0;
        if (profile.getExtras().containsKey("itemSold")) itemsPurchased = (int) profile.getExtras().get("itemSold");
        itemsPurchased++;
        profile.getExtras().put("itemSold", itemsPurchased);
        profile.save();
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
