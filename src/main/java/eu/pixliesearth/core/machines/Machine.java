package eu.pixliesearth.core.machines;

import eu.pixliesearth.Main;
import eu.pixliesearth.nations.entities.nation.Era;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

@Data
@AllArgsConstructor
public class Machine implements Listener {

    private String name;
    private Era era;

    public void register(Machine machine) {
        Bukkit.getPluginManager().registerEvents(machine, Main.getInstance());
    }

}
