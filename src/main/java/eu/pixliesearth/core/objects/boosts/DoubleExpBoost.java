package eu.pixliesearth.core.objects.boosts;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Boost;
import eu.pixliesearth.utils.Timer;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

public class DoubleExpBoost extends Boost implements Listener {

    private static final Main instance = Main.getInstance();

    public DoubleExpBoost() {
        super("2x XP", BoostType.DOUBLE_EXP, new Timer(600000));
    }

    @EventHandler
    public void onExp(PlayerExpChangeEvent event) {
        if (instance.getUtilLists().boosts.containsKey(getBoostType())) {
            event.setAmount(event.getAmount() * 2);
            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        }
    }

}
