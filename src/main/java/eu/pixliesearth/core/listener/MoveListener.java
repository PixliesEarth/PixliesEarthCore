package eu.pixliesearth.core.listener;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Profile profile = Main.getInstance().getProfile(event.getPlayer().getUniqueId());
        if (event.getFrom().getX() != event.getTo().getX() && event.getFrom().getY() != event.getTo().getY() && event.getFrom().getZ() != event.getTo().getZ()) {
            if (profile.getTimers().containsKey("Teleport")) {
                profile.getTimers().remove("Teleport");
                profile.save();
                event.getPlayer().sendMessage(Lang.TELEPORTATION_FAILURE.get(event.getPlayer()));
            }
            if (Main.getInstance().getPlayerLists().afk.contains(event.getPlayer().getUniqueId())) {
                Main.getInstance().getPlayerLists().afk.remove(event.getPlayer().getUniqueId());
                Bukkit.broadcastMessage("§8Player §7" + event.getPlayer().getDisplayName() + " §8is §aback§8.");
            }
        }
    }

}
