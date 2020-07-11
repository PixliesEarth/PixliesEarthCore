package eu.pixliesearth.core.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import static org.bukkit.event.EventPriority.HIGH;

public class ProtectionListener implements Listener {

    @EventHandler(priority = HIGH)
    public void onBreak(BlockBreakEvent event) {
        
    }

}
