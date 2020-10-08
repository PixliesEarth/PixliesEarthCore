package eu.pixliesearth.core.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

import eu.pixliesearth.core.custom.CustomItem;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.utils.CustomItemUtil;

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
    //Disallows hoppers from moving cis into craft inventories
     @EventHandler
     public void onItemMove(InventoryMoveItemEvent e) {
         if (!e.getDestination().getType().equals(InventoryType.CHEST)
                 && !e.getDestination().getType().equals(InventoryType.BARREL)
                 && !e.getDestination().getType().equals(InventoryType.DISPENSER)
                 && !e.getDestination().getType().equals(InventoryType.DROPPER)
                 && !e.getDestination().getType().equals(InventoryType.HOPPER)
                 && !e.getDestination().getType().equals(InventoryType.SHULKER_BOX)
         ){
             if (e.getItem().hasItemMeta()) {
                 if (e.getItem().getItemMeta().getLore() != null) {
                     e.setCancelled(true);
                 }
             }
         }
     }

    //Disallow in every other craft
    @EventHandler
    public void onClick(InventoryClickEvent e){
        
    	if (!(e.getWhoClicked() instanceof Player) || e.getCurrentItem()==null || e.getCurrentItem().getType() == Material.AIR) return;
        
        String id = CustomItemUtil.getUUIDFromItemStack(e.getCurrentItem());
        
        if (id==null) return;
        
        CustomItem ci = CustomItemUtil.getCustomItemFromUUID(id);
        
        if (ci==null) return;
        
        InventoryType it = e.getInventory().getType();
        
        if (it==null) return;
        
        if (it.equals(InventoryType.ANVIL) || it.equals(InventoryType.ENCHANTING)) {
        	if (!e.getCurrentItem().getItemMeta().getDisplayName().equals(ci.getDefaultDisplayName())) {
        		((Player)e.getWhoClicked()).sendMessage(Lang.CANT_PUT_IN_INV.get((Player)e.getWhoClicked()));
            	e.setCancelled(true);
            	return;
        	} else 
        		return;
        } else
        	return;
    }

}
