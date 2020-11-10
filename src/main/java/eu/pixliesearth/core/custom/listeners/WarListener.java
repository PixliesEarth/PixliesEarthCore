package eu.pixliesearth.core.custom.listeners;

import eu.pixliesearth.core.custom.CustomListener;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.warsystem.War;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

public class WarListener extends CustomListener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (event.getEntity().getKiller() == null) return;
        Player killed = event.getEntity();
        Player killer = killed.getKiller();
        Profile killedProfile = instance.getProfile(killed.getUniqueId());
        Profile killerProfile = instance.getProfile(killer.getUniqueId());
        if (!killedProfile.isInNation() || !killerProfile.isInNation()) return;
        if (killedProfile.getNationId().equals(killerProfile.getNationId())) return;
        if (killedProfile.getCurrentNation().getExtras().containsKey("WAR:" + killerProfile.getNationId())) {
            War.getById((String) killedProfile.getCurrentNation().getExtras().get("WAR:" + killerProfile.getNationId())).handleKill(killedProfile, killerProfile, false);
        } else if (killerProfile.getCurrentNation().getExtras().containsKey("WAR:" + killedProfile.getNationId())) {
            War.getById((String) killerProfile.getCurrentNation().getExtras().get("WAR:" + killedProfile.getNationId())).handleKill(killedProfile, killerProfile, true);
        }
    }

}
