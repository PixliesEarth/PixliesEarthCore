package eu.pixliesearth.core.customitems.ci.machines;

import eu.pixliesearth.core.customitems.CustomItem;
import eu.pixliesearth.core.machines.Machine;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class KilnCI implements CustomItem {

    @Override
    public ItemStack getItem() {
        return Machine.MachineType.KILN.getItem();
    }

    @Override
    public String getName() {
        return getItem().getItemMeta().getDisplayName();
    }

    @Override
    public List<String> getLore() {
        return getItem().getLore();
    }

    @Override
    public ItemStack getStatic(int durability) {
        return getItem();
    }

    @Override
    public boolean enchantable() {
        return false;
    }

    @Override
    public void onInteract(PlayerInteractEvent event) {

    }

}
