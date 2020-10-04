package eu.pixliesearth.utils;

import eu.pixliesearth.Main;
import eu.pixliesearth.warsystem.GulagStartListener;
import org.bukkit.Bukkit;

public class GulagThread extends Thread{

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
                sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void tick() {
        if(!Main.getInstance().isGulagActive()){
        if(Main.getInstance().getUtilLists().awaitingGulag1.size() > 0 && Main.getInstance().getUtilLists().awaitingGulag2.size() > 0) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
                @Override
                public void run() {
                    GulagStartListener.startGulag();
                }
            }, 20);


            }
        }
    }
}
