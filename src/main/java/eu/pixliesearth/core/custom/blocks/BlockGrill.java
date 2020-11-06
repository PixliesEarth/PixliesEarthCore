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
                h.getHologramAtLocation(location).clearLines();
                inventory.setItem(0, null);
                Bukkit.getScheduler().runTask(h.getInstance(), () -> location.getWorld().dropItemNaturally(location, Grillable.getByFrom(inventory.getItem(0)).getTo()));
            } else {
                makeParticeAt(location.clone(), Particle.CAMPFIRE_COSY_SMOKE, 1);
                h.getHologramAtLocation(location).removeLine(1);
                h.getHologramAtLocation(location).insertTextLine(1, "§b" + Methods.getTimeAsString(timer.getRemaining(), false));
            }
        }
    }

    public void makeParticeAt(Location loc, Particle p, int amount) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(CustomFeatureLoader.getLoader().getInstance(), new Runnable() { @Override public void run() {loc.getWorld().spawnParticle(p, loc.getX(), loc.getY(), loc.getZ(), amount);}}, 0L);
    }

    @Override
    public void open(Player player, Location location) {
        CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
        if (h.getInventoryFromLocation(location).getItem(0) != null) {
            for (Grillable g : Grillable.values())
                if (player.getInventory().getItemInMainHand().getType().equals(g.getFrom().getType())) {
                    if (player.getInventory().getItemInMainHand().getAmount() > 1) {
                        player.getInventory().setItemInMainHand(player.getInventory().getItemInMainHand().asQuantity(player.getInventory().getItemInMainHand().getAmount() - 1));
                    } else {
                        player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                    }
                    h.getHologramAtLocation(location).insertItemLine(0, g.getFrom());
                    h.getHologramAtLocation(location).insertTextLine(1, "§b" + Methods.getTimeAsString(g.getTime(), false));
                    h.getInventoryFromLocation(location).setItem(0, g.getFrom());
                    h.registerTimer(location, new Timer(g.getTime()));
                    break;
                }
        }
    }

    @Override
    public String getUUID() {
        return "Pixlies:Grill";
    }

}
