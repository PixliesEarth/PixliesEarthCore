package eu.pixliesearth.core.listener;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.entities.nation.NationFlag;
import eu.pixliesearth.utils.Timer;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerCombatListener implements Listener {

    private static final Main instance = Main.getInstance();

    @EventHandler(priority=EventPriority.LOW)
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) return;
        if (!(event.getEntity() instanceof Player)) return;
        if (event.getDamager() instanceof Projectile && ((Projectile) event.getDamager()).getShooter() instanceof Player) {
            Player damager = (Player) ((Projectile) event.getDamager()).getShooter();
            Player target = (Player) event.getEntity();

            Profile dProfile = instance.getProfile(damager.getUniqueId());
            Profile tProfile = instance.getProfile(target.getUniqueId());

            if (dProfile.getNationId().equals(tProfile.getNationId()) && !dProfile.getCurrentNation().getFlags().contains(NationFlag.FRIENDLY_FIRE.name())) return;

            startCombatTimer(tProfile, dProfile);
        } else if (event.getDamager() instanceof Player) {
            Player damager = (Player) event.getDamager();
            Player target = (Player) event.getEntity();

            Profile dProfile = instance.getProfile(damager.getUniqueId());
            Profile tProfile = instance.getProfile(target.getUniqueId());

            if (dProfile.getNationId().equals(tProfile.getNationId()) && !dProfile.getCurrentNation().getFlags().contains(NationFlag.FRIENDLY_FIRE.name())) return;

            startCombatTimer(tProfile, dProfile);
        }
    }

    public void startCombatTimer(Profile tProfile, Profile dProfile) {
        if (dProfile.isInNation() && tProfile.isInNation() && (dProfile.getNationId().equals(tProfile.getNationId()) || Nation.getRelation(dProfile.getNationId(), tProfile.getNationId()) == Nation.NationRelation.ALLY)) return;

        Timer timer = new Timer(60 * 1000);
        dProfile.getTimers().put("§c§lCombat", timer.toMap());
        tProfile.getTimers().put("§c§lCombat", timer.toMap());
        dProfile.save();
        tProfile.save();
    }

}
