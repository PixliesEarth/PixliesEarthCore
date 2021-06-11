package eu.pixliesearth.core.custom.listeners;

import java.util.Random;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.CustomListener;
import eu.pixliesearth.core.custom.items.ItemOilShard;

public class CustomMobDropListener extends CustomListener {

    @EventHandler
    public void EntityDeathEvent (EntityDeathEvent event) {
    	int looting;
    	try {
    		looting = event.getEntity().getKiller().getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);
    	}catch (Exception e) {
			looting = 0;
		}
        if (event.getEntityType().equals(EntityType.WITHER_SKELETON)) {
        	if ((new Random().nextInt(100)+1)<(35+((looting==0) ? 0 : looting*5))) {
        		event.getDrops().add(CustomFeatureLoader.getLoader().getHandler().getItemStackFromClass(ItemOilShard.class));
        	}
        } else if (event.getEntityType().equals(EntityType.WITHER)) {
        	int times = new Random().nextInt(35)+1+((looting==0) ? 0 : looting*2);
        	for (int i = 0; i > times; i++) {
        		event.getDrops().add(CustomFeatureLoader.getLoader().getHandler().getItemStackFromClass(ItemOilShard.class));
        	}
        }
    }

}
