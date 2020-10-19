package eu.pixliesearth.core.custom.listeners;

import eu.pixliesearth.core.custom.CustomListener;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.utils.Methods;
import org.bukkit.Sound;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;

import java.util.Random;

public class NationXPListener extends CustomListener {

    @EventHandler
    public void onXPChange(PlayerLevelChangeEvent event) {
        if (event.getNewLevel() < event.getOldLevel()) return;
        if (event.getNewLevel() < 30) return;
        Player player = event.getPlayer();
        Profile profile = instance.getProfile(player.getUniqueId());
        if (!profile.isInNation()) return;
        Random r = new Random();
        double xpToAdd = Methods.round(0.5 + (1.0 - 0.5) * r.nextDouble(), 2);
        Nation nation = profile.getCurrentNation();
        nation.setXpPoints(nation.getXpPoints() + xpToAdd);
        nation.save();
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        player.sendActionBar("§b+" + xpToAdd + "§3N-XP");
    }

}
