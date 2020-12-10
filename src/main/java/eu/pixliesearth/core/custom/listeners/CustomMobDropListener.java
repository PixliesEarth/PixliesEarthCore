package eu.pixliesearth.core.custom.listeners;

import java.util.Random;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.CustomListener;
import eu.pixliesearth.core.custom.items.ItemOilShard;

public class CustomMobDropListener extends CustomListener {

    @EventHandler
    public void EntityDeathEvent (EntityDeathEvent event) {
        if (event.getEntityType().equals(EntityType.WITHER_SKELETON)) {
        	if ((new Random().nextInt(100)+1)<35) {
        		event.getDrops().add(CustomFeatureLoader.getLoader().getHandler().getItemStackFromClass(ItemOilShard.class));
        	}
        } else if (event.getEntityType().equals(EntityType.WITHER)) {
        	int times = new Random().nextInt(100)+1;
        	for (int i = 0; i > times; i++) {
        		event.getDrops().add(CustomFeatureLoader.getLoader().getHandler().getItemStackFromClass(ItemOilShard.class));
        	}
        }
    }

}
