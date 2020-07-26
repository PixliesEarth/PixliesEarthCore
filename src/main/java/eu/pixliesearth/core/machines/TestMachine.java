package eu.pixliesearth.core.machines;

import eu.pixliesearth.nations.entities.nation.Era;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

public class TestMachine extends Machine {

    public TestMachine() {
        super("test", Era.ANCIENT);
        register(this);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        
    }

}
