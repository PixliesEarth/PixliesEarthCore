package eu.pixliesearth.warsystem;

import eu.pixliesearth.Main;
import eu.pixliesearth.events.GulagStartEvent;
import eu.pixliesearth.localization.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GulagStartListener implements Listener {

    @EventHandler
    public void onFightStart(GulagStartEvent e){
        Player p = e.getPlayer();
        Player enemy = e.getEnemy();

        if(!p.isOnline()){

            fightOver(p, enemy);
            return;
        }
        if(!enemy.isOnline()){
            fightOver(enemy, p);
            return;
        }

        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                if(Main.getInstance().getUtilLists().fightingGulag.containsKey(p.getUniqueId()) && Main.getInstance().getUtilLists().fightingGulag.containsValue(enemy.getUniqueId())){
                    p.sendActionBar(Lang.GULAG_CAP.get(p));
                    enemy.sendActionBar(Lang.GULAG_CAP.get(enemy));
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
                        @Override
                        public void run() {
                            //TODO: Dont hardcode locations
                            World world;
                            if (Main.getInstance().getUtilLists().fightingGulag.containsKey(p.getUniqueId()) && Main.getInstance().getUtilLists().fightingGulag.containsValue(enemy.getUniqueId())){
                                Location middle = new Location(Bukkit.getWorld("gulag"), 4, 73, 2);
                            if (p.getLocation().distance(middle) > enemy.getLocation().distance(middle)) {
                                fightOver(p, enemy);
                                }else if(p.getLocation().distance(middle) < enemy.getLocation().distance(middle)){
                                fightOver(enemy, p);
                              }
                            }
                        }

                    }, 20*10);
                }
            }
        }, 20*60);
        //KILL END IS HANDLED IN DeathListener


    }

    static void fightOver(Player loser, Player winner) {
        Main.getInstance().getUtilLists().fightingGulag.remove(loser.getUniqueId(), winner.getUniqueId());
        loser.getInventory().clear();
        loser.teleport(Bukkit.getWorld("world").getSpawnLocation());
        loser.banPlayer("Died in gulag. You will be unbanned after the war.");
        Main.getInstance().getUtilLists().wasGulag.add(winner.getUniqueId());
        winner.getInventory().clear();
        winner.sendMessage(Lang.WON_GULAG.get(winner));
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                winner.teleport(Bukkit.getWorld("world").getSpawnLocation());
            }
        }, 20*5);


    }
}
