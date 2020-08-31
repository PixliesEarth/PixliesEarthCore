package eu.pixliesearth.guns.listeners;

import eu.pixliesearth.Main;
import eu.pixliesearth.guns.PixliesGun;
import eu.pixliesearth.utils.NBTUtil;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.lang.reflect.InvocationTargetException;

public class GunListener implements Listener {

    public GunListener(Main plugin){
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Player player = event.getPlayer();
        if (event.getItem() == null) return;
        if(player.getInventory().getItemInMainHand().getItemMeta() == null) return;
        if (!event.getItem().isSimilar(player.getInventory().getItemInMainHand())) return;
        if (event.getHand() == EquipmentSlot.HAND) {
            PixliesGun gun = PixliesGun.getByItem(player.getInventory().getItemInMainHand());
            if (gun == null) return;
            event.setCancelled(true);
            gun.trigger(event);
        }
    }

}
