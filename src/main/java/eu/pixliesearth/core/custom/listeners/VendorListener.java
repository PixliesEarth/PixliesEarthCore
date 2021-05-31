package eu.pixliesearth.core.custom.listeners;

import eu.pixliesearth.core.custom.CustomListener;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.event.EventHandler;

public class VendorListener extends CustomListener {

    @EventHandler
    public void onClick(NPCRightClickEvent event) {
        if (event.getNPC().getFullName().equalsIgnoreCase(instance.getVendor().getNpcName())) instance.getVendor().open(event.getClicker());
    }

}
