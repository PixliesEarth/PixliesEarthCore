package eu.pixliesearth.warsystem;

import eu.pixliesearth.Main;
import eu.pixliesearth.localization.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
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
        //TODO: REMOVE THIS IF CHECK FOR WAR IS PRESENT
        if(e.getEntity().getKiller() == null) return;
        if (!p.isOnline()) {
            //TODO: funi message
            Date date = new Date(System.currentTimeMillis() + 60 * 60 * 1000 * 24);
            if(!p.isBanned()) {
                p.banPlayer("Trying to avoid gulag", date, "The gulag");
            }
            return;
        }
        if (Main.getInstance().getUtilLists().wasGulag.contains(p.getUniqueId())) {
            //TODO: funi message
            p.banPlayer("Died after being in gulag. No second chances. You will be unbanned after the war.");
            Main.getInstance().getUtilLists().wasGulag.remove(p.getUniqueId());
            return;
        }

        if (!Main.getInstance().getUtilLists().fightingGulag.containsKey(p.getUniqueId()) && !Main.getInstance().getUtilLists().fightingGulag.containsValue(p.getUniqueId())) {
            if (Main.getInstance().getUtilLists().awaitingGulag1.size() < Main.getInstance().getUtilLists().awaitingGulag2.size() || Main.getInstance().getUtilLists().awaitingGulag1.size() == Main.getInstance().getUtilLists().awaitingGulag2.size()) {
                Main.getInstance().getUtilLists().awaitingGulag1.add(p.getUniqueId());
            } else if (Main.getInstance().getUtilLists().awaitingGulag1.size() > Main.getInstance().getUtilLists().awaitingGulag2.size()) {
                Main.getInstance().getUtilLists().awaitingGulag2.add(p.getUniqueId());
            }
            p.spigot().respawn();
            //TODO: dont hardcode locations
            World world = Bukkit.getWorld("gulag");
            Location awaitingGulagLoc = new Location(world, 5, 73, 6);
            p.teleport(awaitingGulagLoc);
            p.sendMessage(Lang.GULAGED.get(p));
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
                @Override
                public void run() {
                    if (Main.getInstance().getUtilLists().awaitingGulag1.contains(p.getUniqueId()) || Main.getInstance().getUtilLists().awaitingGulag2.contains(p.getUniqueId())) {
                        p.sendMessage(Lang.GULAG_TIMED_OUT.get(p));
                        GulagStartListener.fightOver(null, p);
                        p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                        if (Main.getInstance().getUtilLists().awaitingGulag1.contains(p.getUniqueId())) {
                            Main.getInstance().getUtilLists().awaitingGulag1.remove(p.getUniqueId());
                        } else {
                            Main.getInstance().getUtilLists().awaitingGulag2.remove(p.getUniqueId());

                        }
                    }
                }
            }, 20*60*5);

    }else {
            Player killer = e.getEntity().getKiller();
            assert killer != null;
            GulagStartListener.fightOver(p, killer);


        }
    }
    //player leave if hes awaiting IS ALREADY HANDLED IN LeaveListener!!!!!!
}
