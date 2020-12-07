package eu.pixliesearth.core.custom.listeners;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.custom.CustomListener;
import eu.pixliesearth.core.objects.Profile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

public class WarListener extends CustomListener {

    private static final Main instance = Main.getInstance();

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (event.getEntity().getKiller() == null) return;
        Player killed = event.getEntity();
        Player killer = killed.getKiller();
        if (killer != null && instance.getUtilLists().inGulag.contains(killed.getUniqueId())) {
            instance.getGulag().handleKill(killer, killed);
            return;
        }
        if (!instance.getUtilLists().playersInWar.containsKey(killed.getUniqueId())) return;
        Profile killedProfile = instance.getProfile(killed.getUniqueId());
        instance.getUtilLists().playersInWar.get(killed.getUniqueId()).handleKill(killedProfile);
    }

}
