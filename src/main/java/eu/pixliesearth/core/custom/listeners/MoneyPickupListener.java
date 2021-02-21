package eu.pixliesearth.core.custom.listeners;

import eu.pixliesearth.core.custom.CustomListener;
import eu.pixliesearth.core.objects.Profile;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class MoneyPickupListener extends CustomListener {

    @EventHandler
    public void onPickup(PlayerAttemptPickupItemEvent event) {
        ItemStack item = event.getItem().getItemStack();
        if (item.hasDisplayName() && !item.getDisplayName().startsWith("money")) return;
        double amount = Double.parseDouble(item.getDisplayName().split(" ")[1]);
        event.getItem().remove();
        Player player = event.getPlayer();
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        Profile profile = instance.getProfile(player.getUniqueId());
        profile.depositMoney(amount, "Picked up money from " + item.getDisplayName().split(" ")[2]);
    }

    @EventHandler
    public void onPickupByInventory(InventoryPickupItemEvent event) {
        ItemStack item = event.getItem().getItemStack();
        if (event.getInventory() instanceof PlayerInventory) return;
        if (item.hasDisplayName() && item.getDisplayName().startsWith("money")) event.setCancelled(true);
    }

}
