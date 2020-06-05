package eu.pixliesearth.customitems.listeners;

import eu.pixliesearth.Main;
import eu.pixliesearth.events.ShootEvent;
import eu.pixliesearth.events.SlingShotEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.concurrent.atomic.AtomicReference;

public class SlingshotListener implements Listener {

    @EventHandler
    public void onShoot(SlingShotEvent e){
        Player p = e.getPlayer();
        if(e.isCancelled()) return;
        AtomicReference<Snowball> sb = new AtomicReference<>();
        Bukkit.getPluginManager().callEvent(new ShootEvent(player, "§c7.62"));
        p.getWorld().spawn(p.getEyeLocation(), Snowball.class, snowball -> {
            snowball.setShooter(player);
            snowball.setVelocity(player.getEyeLocation().getDirection().multiply(2.0));
            snowball.setSilent(true);
            snowball.setCustomName("§c7.62mm");
            snowball.setBounce(false);
            snowball.setGravity(false);
            Main.getInstance().getPlayerLists().ammos.add(snowball);
            sb.set(snowball);
        });


    }
}
