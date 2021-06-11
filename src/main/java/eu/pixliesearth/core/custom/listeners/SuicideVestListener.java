package eu.pixliesearth.core.custom.listeners;

import eu.pixliesearth.core.custom.CustomListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class SuicideVestListener extends CustomListener {

    @EventHandler(ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
        if (!player.isSneaking()) return;
        if (player.getEquipment() == null) return;
        if (player.getEquipment().getChestplate() == null) return;
        ItemStack chestPlate = player.getEquipment().getChestplate();
        if (!chestPlate.getDisplayName().equalsIgnoreCase("§c§lSuicide Vest")) return;
        player.getEquipment().setChestplate(null);
        player.sendTitle("§e§l3", "§7detonating in...", 5, 10, 5);
        Bukkit.getScheduler().runTaskLater(instance, () -> {
            player.sendTitle("§6§l2", "§7detonating in...", 5, 10, 5);
            Bukkit.getScheduler().runTaskLater(instance, () -> {
                player.sendTitle("§c§l1", "§7detonating in...", 5, 10, 5);
                Bukkit.getScheduler().runTaskLater(instance, () -> {
                    player.getWorld().createExplosion(player.getLocation(), 12F, true, false, player);
                    player.setHealth(0);
                }, 20);
            }, 20);
        }, 20);
    }

}
