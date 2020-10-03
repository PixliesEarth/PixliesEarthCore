package eu.pixliesearth.core.customitems.listeners;

import eu.pixliesearth.Main;
import eu.pixliesearth.events.SlingShotEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class SlingshotListener implements Listener {

    @EventHandler
    public void onShoot(SlingShotEvent e) {
        Player p = e.getPlayer();
        if(e.isCancelled()) return;
        AtomicReference<Snowball> sb = new AtomicReference<>();
        p.getWorld().spawn(p.getEyeLocation(), Snowball.class, snowball -> {
            snowball.setShooter(p);
            snowball.setVelocity(p.getEyeLocation().getDirection().multiply(1.0));
            snowball.setSilent(true);
            snowball.setCustomName("Slingshot");
            snowball.setBounce(false);
            snowball.setGravity(true);
            ItemMeta meta = snowball.getItem().getItemMeta();
            meta.setCustomModelData(2);
            snowball.getItem().setItemMeta(meta);
            Main.getInstance().getUtilLists().ammos.put(snowball, 3.0);
            sb.set(snowball);
        });
        if(e.getPlayer().getInventory().contains(Material.COBBLESTONE))
            removeOne(Material.COBBLESTONE, p);
        else if(e.getPlayer().getInventory().contains(Material.GRAVEL))
            removeOne(Material.GRAVEL, p);
        else if(e.getPlayer().getInventory().contains(Material.STONE))
           removeOne(Material.STONE, p);

        UUID uuid = p.getUniqueId();

        if(!(Main.getInstance().getUtilLists().reloading.contains(uuid)))
            Main.getInstance().getUtilLists().reloading.add(uuid);

        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> Main.getInstance().getUtilLists().reloading.remove(uuid),30);
    }

    public void removeOne(Material m, Player p){
        HashMap<Integer, ? extends ItemStack> map = p.getInventory().all(m);
        if(map.isEmpty()){
            return;
        }
        for(Map.Entry<Integer, ? extends ItemStack> entry : map.entrySet())
            if(entry.getValue().getAmount() != 0) {
                if (entry.getValue().getAmount() == 1) p.getPlayer().getInventory().setItem(entry.getKey(), null);
                else {
                    entry.getValue().setAmount(entry.getValue().getAmount() - 1);
                    p.getPlayer().getInventory().setItem(entry.getKey(), entry.getValue());
                }
                break;
            }
    }
}
