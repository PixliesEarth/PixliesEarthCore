package eu.pixliesearth.core.custom.listeners;

import eu.pixliesearth.core.custom.CustomListener;
import org.bukkit.event.EventHandler;
import org.dynmap.DynmapWebChatEvent;

public class DynmapChatListener extends CustomListener {

    @EventHandler
    public void onChat(DynmapWebChatEvent event) {
        instance.getUtilLists().dynmapQueue.put(event.getName(), event.getMessage());
    }
}
