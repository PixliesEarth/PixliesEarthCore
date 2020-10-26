package eu.pixliesearth.core.machines;

import eu.pixliesearth.Main;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.Serializable;

public class MachineTask extends Thread {

    private static final Main instance = Main.getInstance();

    public MachineTask() {
        this.start();
    }

    @Override
    public void run() {
        while (true) {
            //Tick
            try {
                for (Machine machine : instance.getUtilLists().machines.values())
                    machine.tick();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                sleep(50);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void init() {
        Machine.loadAll();
    }

    public void stopThread() {
        this.stop();
    }

}
