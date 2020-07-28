package eu.pixliesearth.core.machines;

import eu.pixliesearth.Main;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Machine extends Thread implements Listener {

    private String name;
    private Location location;

    protected static final Main instance = Main.getInstance();

    public void register(Machine machine) {
        Bukkit.getPluginManager().registerEvents(machine, Main.getInstance());
    }

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
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void tick() {
        for (Machine machine : instance.getUtilLists().machines.values())
            machine.tick();
    }

}
