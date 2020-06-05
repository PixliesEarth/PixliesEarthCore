package eu.pixliesearth.customitems.listeners;


import com.destroystokyo.paper.event.player.PlayerReadyArrowEvent;
import eu.pixliesearth.customitems.ItemSlingshot;
import eu.pixliesearth.events.SlingShotEvent;
import net.minecraft.server.v1_15_R1.PacketPlayOutSetSlot;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ItemsInteractEvent implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)){
            if(event.getPlayer().getInventory().getItemInMainHand().getType() == Material.BOW){

            if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta() != null) {


                if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getLore().equals(new ItemSlingshot().getLore())){

                }
                if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getLore().equals(new ItemSlingshot().getLore())) {

                    if (event.getPlayer().getInventory().contains(Material.COBBLESTONE) || event.getPlayer().getInventory().contains(Material.STONE) || event.getPlayer().getInventory().contains(Material.GRAVEL)) {


                        ItemStack arrow = new ItemStack(Material.ARROW);
                        ItemMeta meta = arrow.getItemMeta();
                        meta.setDisplayName("§7Fake arrow");
                        ArrayList<String> lore = new ArrayList<String>();
                        lore.add("§7Fake arrow for the slingshot");
                        arrow.setItemMeta(meta);
                        ((CraftPlayer) event.getPlayer()).getHandle().playerConnection.sendPacket(new PacketPlayOutSetSlot(0, 9, CraftItemStack.asNMSCopy(arrow)));
                        Bukkit.getPluginManager().callEvent(new SlingShotEvent(event.getPlayer()));

                        if(event.getPlayer().getInventory().contains(Material.COBBLESTONE)){
                            Material type;
                            ItemStack cobble = new ItemStack(Material.COBBLESTONE);
                            cobble.setAmount(1);
                            event.getPlayer().getInventory().remove(cobble);
                        }else if(event.getPlayer().getInventory().contains(Material.GRAVEL)){
                            ItemStack gravel = new ItemStack(Material.GRAVEL);
                            gravel.setAmount(1);
                            event.getPlayer().getInventory().remove(gravel);
                        }else if(event.getPlayer().getInventory().contains(Material.STONE)){
                            Material type;
                            ItemStack stone = new ItemStack(Material.STONE);
                            stone.setAmount(1);
                            System.out.println("Trying to remove stone");
                            event.getPlayer().getInventory().remove(stone);
                        }

                        }
                    }
                }
            }
        }
    }


}
