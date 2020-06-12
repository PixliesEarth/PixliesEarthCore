package eu.pixliesearth.core.listener;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.utils.Timer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerCombatListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) return;
        if (!(event.getEntity() instanceof Player)) return;

        Main instance = Main.getInstance();

        Player damager = (Player) event.getDamager();
        Player target = (Player) event.getEntity();

        Timer timer = new Timer(60 * 1000);
        Profile dProfile = instance.getProfile(damager.getUniqueId());
        Profile tProfile = instance.getProfile(target.getUniqueId());

        dProfile.getTimers().put("§c§lCombat", timer);
        tProfile.getTimers().put("§c§lCombat", timer);
        dProfile.save();
        tProfile.save();

    }

}
