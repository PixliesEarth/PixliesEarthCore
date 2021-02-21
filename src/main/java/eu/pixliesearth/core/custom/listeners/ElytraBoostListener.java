package eu.pixliesearth.core.custom.listeners;

import com.destroystokyo.paper.event.player.PlayerElytraBoostEvent;
import eu.pixliesearth.core.custom.CustomListener;
import org.bukkit.event.EventHandler;

public class ElytraBoostListener extends CustomListener {

    @EventHandler
    public void onBoost(PlayerElytraBoostEvent event) {
        if (event.getPlayer().getWorld().getName().contains("the_end")) return;
        event.setCancelled(true);
    }

}
