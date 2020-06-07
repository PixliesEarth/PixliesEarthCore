package eu.pixliesearth.core.listener;

import eu.pixliesearth.core.guns.gunObjects.AK;
import eu.pixliesearth.localization.Lang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class AnvilListener implements Listener {
    @EventHandler
    public void onDrag(PrepareItemCraftEvent e){
        //for players 2x2
        for(ItemStack items : e.getInventory().getMatrix()){
        if(items != null){
                if (items.hasItemMeta()) {
                    if (items.getItemMeta().getLore() != null) {
                        if (e.getRecipe() != null) {
                                e.getInventory().setResult(null);
                            }
                        }
                    }
                }
            }
        }

    //Disallow in every other craft
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
