package eu.pixliesearth.core.machines;

import eu.pixliesearth.Main;
import org.bukkit.scheduler.BukkitRunnable;

public class MachineTask extends BukkitRunnable {

    private static final Main instance = Main.getInstance();
    private boolean ready = false;

    public MachineTask() {
        this.runTaskTimer(instance, 0, 10);
    }

    public void init() {
        Machine.loadAll();
        this.ready = true;
    }

    @Override
    public void run() {
        if (ready) {
            for (Machine machine : instance.getUtilLists().machines.values())
                machine.tick();
        }
    }

}
