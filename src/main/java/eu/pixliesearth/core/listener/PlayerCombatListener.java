package eu.pixliesearth.core.listener;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.nations.entities.chunk.NationChunk;
import eu.pixliesearth.nations.entities.nation.Nation;
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

        Profile dProfile = instance.getProfile(damager.getUniqueId());
        Profile tProfile = instance.getProfile(target.getUniqueId());

        if (dProfile.isInNation() && tProfile.isInNation() && (dProfile.getNationId().equals(tProfile.getNationId()) || Nation.getRelation(dProfile.getNationId(), tProfile.getNationId()) == Nation.NationRelation.ALLY)) return;

        Timer timer = new Timer(60 * 1000);
        dProfile.getTimers().put("§c§lCombat", timer.toMap());
        tProfile.getTimers().put("§c§lCombat", timer.toMap());
        dProfile.save();
        tProfile.save();

    }

}
