package eu.pixliesearth.utils;

import eu.pixliesearth.Main;
import org.bukkit.Bukkit;

public class GulagThread extends Thread {

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
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static final Main instance = Main.getInstance();

    private void tick() {
        if (instance.getCurrentWar() == null) return;
        if (!instance.getGulag().getFighting().isEmpty()) return;
        if (instance.getGulag().getPlayers().size() < 2) return;
        instance.getGulag().placeFighters();
    }

}
