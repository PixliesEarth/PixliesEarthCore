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

import java.util.Random;

public class NationXPListener extends CustomListener {

    @EventHandler
    public void onAnimalKill(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof Monster)) return;
        Monster entity = (Monster) event.getEntity();
        if (entity.getKiller() == null) return;
        Player killer = entity.getKiller();
        Profile killerProfile = instance.getProfile(killer.getUniqueId());
        if (!killerProfile.isInNation()) return;
        Random r = new Random();
        double xpToAdd = Methods.round(0.0 + (0.3 - 0.00) * r.nextDouble(), 2);
        if (xpToAdd == 0.0) return;
        Nation nation = killerProfile.getCurrentNation();
        nation.setXpPoints(nation.getXpPoints() + xpToAdd);
        nation.save();
        killer.playSound(killer.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        killer.sendActionBar("§b+" + xpToAdd + "§3N-XP");
    }

}
