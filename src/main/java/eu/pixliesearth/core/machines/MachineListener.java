package eu.pixliesearth.core.machines;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.listener.ProtectionManager;
import eu.pixliesearth.core.machines.autocrafters.AutoCrafterMachine;
import eu.pixliesearth.utils.Methods;
import lombok.SneakyThrows;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class MachineListener implements Listener {

    private final Main instance = Main.getInstance();

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (!ProtectionManager.canBreak(event)) return;
        if (!instance.getUtilLists().machines.containsKey(event.getBlock().getLocation())) return;
        event.setDropItems(false);
        Machine machine = instance.getUtilLists().machines.get(event.getBlock().getLocation());
        machine.getLocation().getWorld().dropItemNaturally(machine.getLocation(), machine.getItem());
        machine.remove();
    }

    @EventHandler
    @SneakyThrows
    public void onPlace(BlockPlaceEvent event) {
        if (!ProtectionManager.canPlace(event)) return;
        ItemStack item = event.getItemInHand();
        for (Machine.MachineType type : Machine.MachineType.values())
            if (item.isSimilar(type.getItem()))
                instance.getUtilLists().machines.put(event.getBlockPlaced().getLocation(), type.getClazz().getConstructor(String.class, Location.class).newInstance(Methods.generateId(7), event.getBlockPlaced().getLocation()));
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        InventoryView view = event.getView();
        Machine.MachineType type = null;
        for (Machine.MachineType types : Machine.MachineType.values())
            if (view.getTitle().startsWith(types.getItem().getItemMeta().getDisplayName() + " §8| "))
                type = types;
        if (type == null) return;
        if (event.getClickedInventory() == null) return;
        if (Machine.craftSlots.contains(event.getSlot()) || Machine.resultSlots.contains(event.getSlot())) return;
        if (!event.getClickedInventory().equals(view.getTopInventory())) return;
        event.setCancelled(true);
        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getType() == Material.BARRIER && event.getCurrentItem().getItemMeta().getDisplayName().equals("§c§lBack")) {
            Machine targetMill = instance.getUtilLists().openMachines.get(event.getWhoClicked().getUniqueId());
            if (targetMill instanceof AutoCrafterMachine) {
                AutoCrafterMachine acm = (AutoCrafterMachine) targetMill;
                for (int i : Machine.craftSlots) {
                    if (event.getClickedInventory().getItem(i) != null)
                        event.getWhoClicked().getWorld().dropItemNaturally(targetMill.getLocation(), event.getClickedInventory().getItem(i));
                }
                for (int i : Machine.resultSlots) {
                    if (event.getClickedInventory().getItem(i) != null)
                        event.getWhoClicked().getWorld().dropItemNaturally(targetMill.getLocation(), event.getClickedInventory().getItem(i));
                }
                acm.reopen((Player) event.getWhoClicked());
            }
        }
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        Block b = event.getClickedBlock();
        if (b == null) return;
        if (!instance.getUtilLists().machines.containsKey(b.getLocation())) return;
        instance.getUtilLists().openMachines.put(event.getPlayer().getUniqueId(),  instance.getUtilLists().machines.get(b.getLocation()));
        instance.getUtilLists().machines.get(b.getLocation()).open(event.getPlayer());
    }

}
