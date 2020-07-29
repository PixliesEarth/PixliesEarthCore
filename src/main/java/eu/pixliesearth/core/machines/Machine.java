package eu.pixliesearth.core.machines;

import eu.pixliesearth.Main;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Machine extends Thread implements Listener {

    private String machineName;
    private Location location;

    public Machine() {
        start();
    }

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
