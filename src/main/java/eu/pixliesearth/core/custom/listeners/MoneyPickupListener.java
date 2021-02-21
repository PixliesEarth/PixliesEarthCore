package eu.pixliesearth.core.custom.listeners;

import eu.pixliesearth.core.custom.CustomListener;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.utils.NBTUtil;
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
        if (NBTUtil.getTagsFromItem(item) != null && NBTUtil.getTagsFromItem(item).getString("money") == null) return;
        double amount = Double.parseDouble(item.getDisplayName().split(" ")[1]);
        event.getItem().remove();
        Player player = event.getPlayer();
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        Profile profile = instance.getProfile(player.getUniqueId());
        profile.depositMoney(amount, "Picked up money from " + item.getDisplayName().split(" ")[2]);
        event.setCancelled(true);
    }

}
