package eu.pixliesearth.core.custom.listeners;

import eu.pixliesearth.core.custom.CustomFeatureHandler;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.CustomItem;
import eu.pixliesearth.core.custom.CustomListener;
import eu.pixliesearth.core.custom.interfaces.IConsumable;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public class CustomConsumableListener extends CustomListener {

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent event) {
        ItemStack item = event.getItem();
        if (item == null || item.getType().equals(Material.AIR)) return;
        CustomFeatureHandler handler = CustomFeatureLoader.getLoader().getHandler();
        CustomItem ci = handler.getCustomItemFromItemStack(item);
        if (ci == null) return;
        if (ci instanceof IConsumable)
            event.setCancelled(((IConsumable) ci).PlayerItemConsumeEvent(event));
    }

}
