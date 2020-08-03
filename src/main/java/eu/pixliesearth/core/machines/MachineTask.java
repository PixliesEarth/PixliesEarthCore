package eu.pixliesearth.core.machines;

import com.google.gson.Gson;
import eu.pixliesearth.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MachineTask extends BukkitRunnable {

    private static final Main instance = Main.getInstance();

    public MachineTask() {
        this.runTaskTimer(instance, 0, 10);
    }

    public static void init() {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File("machines.yml"));
        Gson gson = new Gson();
        for (String string : config.getStringList("machines")) {
            Machine machine = gson.fromJson(string, Machine.class);
            instance.getUtilLists().machines.put(machine.getLocation(), machine);
        }
    }

    @Override
    public void run() {
        for (Machine machine : instance.getUtilLists().machines.values())
            machine.tick();
    }

}
