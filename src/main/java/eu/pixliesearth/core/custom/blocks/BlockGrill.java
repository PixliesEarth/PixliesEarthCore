package eu.pixliesearth.core.custom.blocks;

import eu.pixliesearth.core.custom.*;
import eu.pixliesearth.utils.Methods;
import eu.pixliesearth.utils.Timer;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BlockGrill extends CustomSavableBlock {

    public BlockGrill() {

    }

    @Override
    public Material getMaterial() {
        return Material.LOOM;
    }

    @Override
    public String getDefaultDisplayName() {
        return "§6Grill";
    }

    @Override
    public void onTick(Location location, Inventory inventory, Timer timer) {
        CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
        if (timer != null) {
            if (timer.hasExpired()) {
                h.unregisterTimer(location);
                Bukkit.getScheduler().runTask(h.getInstance(), () -> {
                    h.getHologramAtLocation(location).clearLines();
                    h.getHologramAtLocation(location).insertItemLine(0, Grillable.getByFrom(inventory.getItem(0)).getTo());
                    h.getHologramAtLocation(location).insertTextLine(1, "§aCooked!");
                });
                // Bukkit.getScheduler().scheduleSyncDelayedTask(h.getInstance(), () -> location.getWorld().dropItemNaturally(location, Grillable.getByFrom(inventory.getItem(0)).getTo()), 0L);
            } else {
                // makeParticeAt(location.clone(), Particle.CAMPFIRE_COSY_SMOKE, 1);
                Bukkit.getScheduler().runTask(h.getInstance(), () -> {
                    h.getHologramAtLocation(location).removeLine(1);
                    h.getHologramAtLocation(location).insertTextLine(1, "§b" + Methods.getTimeAsString(timer.getRemaining(), false));
                });
            }
        }
    }

/*    public void makeParticeAt(Location loc, Particle p, int amount) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(CustomFeatureLoader.getLoader().getInstance(), () -> {loc.getWorld().spawnParticle(p, loc.getX(), loc.getY(), loc.getZ(), amount);}, 0L);
    }*/

    @Override
    public Inventory getInventory() {
        return Bukkit.createInventory(null, 9, getInventoryTitle());
    }

    @Override
    public void open(Player player, Location location) {
        player.closeInventory();
        CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
        ItemStack item = h.getInventoryFromLocation(location).getItem(0);
        if (item == null || item.getType().equals(Material.AIR)) {
            for (Grillable g : Grillable.values())
                if (player.getInventory().getItemInMainHand().getType().equals(g.getFrom().getType())) {
                    if (player.getInventory().getItemInMainHand().getAmount() > 1) {
                        player.getInventory().setItemInMainHand(player.getInventory().getItemInMainHand().asQuantity(player.getInventory().getItemInMainHand().getAmount() - 1));
                    } else {
                        player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                    }
                    h.registerHologramAtLocation(location, createHologram(g.getFrom(), location));
                    h.getHologramAtLocation(location).insertTextLine(1, "§b" + Methods.getTimeAsString(g.getTime(), false));
                    h.getInventoryFromLocation(location).setItem(0, g.getFrom());
                    h.registerTimer(location, new Timer(g.getTime()));
                    break;
                }
        } else {
            player.getInventory().addItem(Grillable.getByFrom(item).getTo());
            h.getHologramAtLocation(location).clearLines();
        }
    }

    @Override
    public String getUUID() {
        return "Pixlies:Grill";
    }

}
