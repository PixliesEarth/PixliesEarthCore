package eu.pixliesearth.core.custom.listeners;

import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.CustomListener;
import eu.pixliesearth.core.vendors.Vendor;
import lombok.SneakyThrows;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.event.EventHandler;

public class VendorListener extends CustomListener {

/*    @SneakyThrows
    @EventHandler
    public void onNPCClick(NPCRightClickEvent event) {
        if (!CustomFeatureLoader.getLoader().getHandler().getVendorMap().containsKey(event.getNPC().getName())) return;
        Vendor vendor = CustomFeatureLoader.getLoader().getHandler().getVendorMap().get(event.getNPC().getName()).getClass().getConstructor().newInstance();
        vendor.open(event.getClicker());
    }*/

}
