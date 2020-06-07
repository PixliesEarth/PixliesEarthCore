package eu.pixliesearth.core.listener;

import eu.pixliesearth.localization.Lang;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.Set;

public class AnvilListener implements Listener {
    //Doesnt work yet(hopefully will)
    /*@EventHandler
    public void onDrag(InventoryDragEvent e){
        Player player = (Player) e.getWhoClicked();
        if(e.getInventory().getType().equals(InventoryType.CRAFTING)) {
            Set<Integer> slots = e.getRawSlots();
            System.out.println(slots);
            if(slots.contains(1) || slots.contains(2) || slots.contains(3) || slots.contains(4)){
                e.setCancelled(true);
                player.sendMessage(Lang.CANT_PUT_IN_INV.get(player));
            }
        }
    }*/
    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(!e.getSlotType().equals(InventoryType.SlotType.CONTAINER) && !e.getSlotType().equals(InventoryType.SlotType.QUICKBAR)) return;
        InventoryType clickedInv = e.getInventory().getType();

        if (!clickedInv.equals(InventoryType.ENCHANTING) && !clickedInv.equals(InventoryType.ANVIL) && !clickedInv.equals(InventoryType.BEACON) && !clickedInv.equals(InventoryType.CARTOGRAPHY)  && !clickedInv.equals(InventoryType.BREWING) && !clickedInv.equals(InventoryType.FURNACE) && !clickedInv.equals(InventoryType.GRINDSTONE) && !clickedInv.equals(InventoryType.HOPPER) && !clickedInv.equals(InventoryType.LOOM) && !clickedInv.equals(InventoryType.MERCHANT) && !clickedInv.equals(InventoryType.BLAST_FURNACE) && !clickedInv.equals(InventoryType.SMOKER) && !clickedInv.equals(InventoryType.STONECUTTER) && !clickedInv.equals(InventoryType.WORKBENCH)) return;

        Player player = (Player) e.getWhoClicked();
        if(e.getCurrentItem() == null) return;

        if(!e.getCurrentItem().hasItemMeta()) return;

        if(e.getCurrentItem().getItemMeta().getLore() == null) return;

        if(e.getCurrentItem().getItemMeta().getLore() != null){
            e.setCancelled(true);
            player.sendMessage(Lang.CANT_PUT_IN_INV.get(player));
        }

    }

}
