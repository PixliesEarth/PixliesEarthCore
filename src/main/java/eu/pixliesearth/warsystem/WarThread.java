package eu.pixliesearth.warsystem;

import eu.pixliesearth.Main;
import eu.pixliesearth.warsystem.War;
import org.bukkit.Bukkit;

public class WarThread extends Thread {

    @Override
    public void run() {
        while(true) {
            //Tick
            try {
                tick();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //Thread Sleep
           try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static final Main instance = Main.getInstance();

    private void tick() {
        for (War war : instance.getUtilLists().wars.values())
            war.tick();
        if (instance.getCurrentWar() == null) return;
        if (!instance.getGulag().getFighting().isEmpty()) return;
        if (instance.getGulag().getPlayers().size() < 2) return;
        instance.getGulag().placeFighters();
    }

}
