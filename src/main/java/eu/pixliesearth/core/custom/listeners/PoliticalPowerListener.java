package eu.pixliesearth.core.custom.listeners;

import eu.pixliesearth.core.custom.CustomListener;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.nations.entities.nation.Ideology;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.entities.nation.NationUpgrade;
import eu.pixliesearth.utils.Methods;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLevelChangeEvent;

import java.util.Random;

public class PoliticalPowerListener extends CustomListener {

    @EventHandler
    public void onXPChange(PlayerLevelChangeEvent event) {
        if (event.getNewLevel() < event.getOldLevel()) return;
        if (event.getNewLevel() < 30) return;
        Player player = event.getPlayer();
        Profile profile = instance.getProfile(player.getUniqueId());
        if (!profile.isInNation()) return;
        Random r = new Random();
        double xpToAdd = Methods.round(0.1 + (0.3 - 0.1) * r.nextDouble(), 2);
        Nation nation = profile.getCurrentNation();
        Ideology ideology = Ideology.valueOf(nation.getIdeology());
        if (nation.getUpgrades().contains(NationUpgrade.DOUBLE_PP.name())) xpToAdd = xpToAdd * 2;
        if (ideology == Ideology.LIBERAL_DEMOCRACY) xpToAdd += 0.05 * nation.getAllies().size();
        if (ideology == Ideology.CONSERVATIVE_DEMOCRACY) xpToAdd += (xpToAdd / 100 * 10);
        nation.setXpPoints(nation.getXpPoints() + xpToAdd);
        nation.save();
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        player.sendActionBar("§b+" + xpToAdd + "§3PoliticalPower");
    }

}
