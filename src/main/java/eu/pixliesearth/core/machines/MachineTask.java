package eu.pixliesearth.core.machines;

import eu.pixliesearth.Main;
import org.bukkit.scheduler.BukkitRunnable;

public class MachineTask extends Thread {

    private static final Main instance = Main.getInstance();
    private boolean ready = false;

    @Override
    public void run() {
        while(true) {
            //Tick
            try {
                if (ready)
                    for (Machine machine : instance.getUtilLists().machines.values())
                        machine.tick();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //Thread Sleep
            try {
                sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void init() {
        Machine.loadAll();
        this.ready = true;
    }

    public void stopThread() {
        this.ready = false;
        try {
            join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
