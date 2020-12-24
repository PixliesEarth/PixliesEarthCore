package eu.pixliesearth.core.listener;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.nations.entities.nation.NationFlag;
import eu.pixliesearth.utils.Timer;

public class PlayerCombatListener implements Listener {

    private static final Main instance = Main.getInstance();

    @EventHandler(priority=EventPriority.HIGH)
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) return;
        if (!(event.getEntity() instanceof Player)) return;
        Player target = (Player) event.getEntity();
        if (event.getDamager() instanceof Projectile && ((Projectile) event.getDamager()).getShooter() instanceof Player) {
            Player damager = (Player) ((Projectile) event.getDamager()).getShooter();

            Profile dProfile = instance.getProfile(damager.getUniqueId());
            Profile tProfile = instance.getProfile(target.getUniqueId());

            if (dProfile.isInNation() && tProfile.isInNation() && dProfile.getNationId().equals(tProfile.getNationId()) && !dProfile.getCurrentNation().getFlags().contains(NationFlag.FRIENDLY_FIRE.name())) {
                event.setCancelled(true);
                return;
            }

            startCombatTimer(tProfile, dProfile);
        } else if (event.getDamager() instanceof Player) {
            Player damager = (Player) event.getDamager();

            Profile dProfile = instance.getProfile(damager.getUniqueId());
            Profile tProfile = instance.getProfile(target.getUniqueId());

            if (dProfile.isInNation() && tProfile.isInNation() && dProfile.getNationId().equals(tProfile.getNationId()) && !dProfile.getCurrentNation().getFlags().contains(NationFlag.FRIENDLY_FIRE.name())) {
                event.setCancelled(true);
                return;
            }

            startCombatTimer(tProfile, dProfile);
        }
    }

    public static void startCombatTimer(Profile tProfile, Profile dProfile) {
        Timer timer = new Timer(15 * 1000);
        dProfile.getTimers().put("§c§lCombat", timer.toMap());
        tProfile.getTimers().put("§c§lCombat", timer.toMap());
        dProfile.save();
        tProfile.save();
    }

}
