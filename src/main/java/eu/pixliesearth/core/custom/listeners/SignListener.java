package eu.pixliesearth.core.custom.listeners;

import eu.pixliesearth.core.custom.CustomListener;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.SignChangeEvent;

public class SignListener extends CustomListener {

    @EventHandler
    public void onSign(SignChangeEvent event) {
        for (int i = 0; i < event.getLines().length; i++)
            if (event.getPlayer().hasPermission("earth.coloredsigns"))
                event.setLine(i, ChatColor.translateAlternateColorCodes('&', event.getLine(i)));
    }

}
