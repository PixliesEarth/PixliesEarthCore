package eu.pixliesearth.core.customitems;

import java.util.List;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public interface CustomItem {

    public ItemStack getItem();

    public String getName();

    public List<String> getLore();

    public ItemStack getStatic(int durability);

    public boolean enchantable();

    public void onInteract(PlayerInteractEvent event);

}
