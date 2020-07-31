package eu.pixliesearth.core.machines;

import eu.pixliesearth.Main;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class MachineTask extends BukkitRunnable {

    private static final Main instance = Main.getInstance();

    private final List<Machine> machines = new ArrayList<>();

    public MachineTask() {
        this.runTaskTimer(instance, 0, 20);
    }

    @Override
    public void run() {
        for (Machine machine : machines)
            machine.tick();
    }

}
