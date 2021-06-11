package eu.pixliesearth.core.custom.listeners;

import eu.pixliesearth.Main;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class VendorListener implements Listener {

    private static final Main instance = Main.getInstance();

    @EventHandler
    public void onClick(NPCRightClickEvent event) {
        if (event.getNPC().getFullName().equalsIgnoreCase(instance.getVendor().getNpcName())) {
            instance.getVendor().open(event.getClicker());
        }
    }

}
