package eu.pixliesearth.core.custom.listeners;

import eu.pixliesearth.core.custom.CustomListener;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class ChestShopListener extends CustomListener {

    @EventHandler
    public void onSignClick(PlayerInteractEvent event) {
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if (!(event.getClickedBlock() != null && event.getClickedBlock().getState() instanceof Sign)) return;
        Block block = event.getClickedBlock();
        Block attached = Methods.getAttachedBlock(block);
        if (attached != null && attached.getType().equals(Material.CHEST)) {
            Chest chest = (Chest) attached.getState();
            Sign sign = (Sign) block.getState();
            if (sign.getLine(0).equals("§bChestshop")) {
                Material material = Material.getMaterial(sign.getLine(1));
                if (material == null) return;
                double price = Double.parseDouble(sign.getLine(2).replace("§2§l$§a", ""));
                OfflinePlayer seller = Bukkit.getOfflinePlayerIfCached(sign.getLine(3));
                if (seller == null) return;
                Profile sellerProfile = instance.getProfile(seller.getUniqueId());
                Player player = event.getPlayer();
                Profile profile = instance.getProfile(player.getUniqueId());
                if (!chest.getInventory().contains(material, 1)) {
                    player.sendMessage("§c§lUH OH §cit looks like the seller needs to refill this shop :(");
                    return;
                }
                boolean withdraw = profile.withdrawMoney(price, material.name() + " purchase from " + seller.getName());
                if (withdraw) {
                    final ItemStack finalItem = chest.getInventory().getItem(chest.getInventory().first(material)).asOne();
                    Methods.removeRequiredAmount(finalItem, chest.getInventory());
                    for (ItemStack item : player.getInventory().addItem(finalItem).values())
                        player.getWorld().dropItemNaturally(sign.getLocation(), item);
                    sellerProfile.depositMoney(price, material.name() + " sold to " + player.getName());
                }
            }
        }
    }

    @EventHandler
    public void onSignPlace(SignChangeEvent event) {
        if (event.getLine(0) == null) return;
        if (!event.getLine(0).equalsIgnoreCase("chestshop")) return;
        if (!(Methods.getAttachedBlock(event.getBlock()).getState() instanceof Chest chest)) return;
        event.setLine(0, "§bChestshop");
        if (chest.getInventory().getItem(0) == null) {
            event.getPlayer().sendMessage(Lang.EARTH + "§7You have to put an item into the chest.");
            return;
        }
        Material selling = chest.getInventory().getItem(0).getType();
        event.setLine(1, selling.name());
        event.setLine(2, "§2§l$§a" + event.getLine(2));
        event.setLine(3, event.getPlayer().getName());
    }

}
