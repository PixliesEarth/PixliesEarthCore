package eu.pixliesearth.warsystem;

import eu.pixliesearth.Main;
import eu.pixliesearth.localization.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Date;

public class GulagDeathListener implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        //TODO: Check if in war
        Player p = e.getEntity().getPlayer();
        if (!p.isOnline()) {
            //TODO: funi message
            Date date = new Date(System.currentTimeMillis() + 60 * 60 * 1000 * 24);

            p.banPlayer("Trying to avoid gulag", date, "The gulag");
            return;
        }
        if (Main.getInstance().getUtilLists().wasGulag.contains(p.getUniqueId())) {
            //TODO: funi message
            p.banPlayer("Died after being in gulag. No second chances. You will be unbanned after the war.");
            return;
        }

        if (!Main.getInstance().getUtilLists().fightingGulag.containsKey(p.getUniqueId())) {
            if (Main.getInstance().getUtilLists().awaitingGulag1.size() > Main.getInstance().getUtilLists().awaitingGulag2.size() || Main.getInstance().getUtilLists().awaitingGulag1.size() == Main.getInstance().getUtilLists().awaitingGulag2.size()) {
                Main.getInstance().getUtilLists().awaitingGulag1.add(p.getUniqueId());
            } else if (Main.getInstance().getUtilLists().awaitingGulag1.size() < Main.getInstance().getUtilLists().awaitingGulag2.size()) {
                Main.getInstance().getUtilLists().awaitingGulag2.add(p.getUniqueId());
            }
            p.spigot().respawn();
            //TODO: dont hardcode locations
            World world = Bukkit.getWorld("gulag");
            Location awaitingGulagLoc = new Location(world, 5, 73, 6);
            p.teleport(awaitingGulagLoc);
            p.sendMessage(Lang.GULAGED.get(p));


    }else {
            Player killer = e.getEntity().getKiller();
            assert killer != null;
            GulagStartListener.fightOver(p, killer);


        }
    }
    //player leave if hes awaiting IS ALREADY HANDLED IN LeaveListener!!!!!!
}
