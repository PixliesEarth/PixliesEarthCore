package eu.pixliesearth.core.customitems.listeners;


import eu.pixliesearth.Main;
import eu.pixliesearth.core.customitems.ci.ItemSlingshot;
import eu.pixliesearth.events.SlingShotEvent;
import eu.pixliesearth.localization.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ItemsInteractEvent implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.LEFT_CLICK_BLOCK) || event.getAction().equals(Action.LEFT_CLICK_AIR)) {
            if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.STICK) {

                if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta() != null) {

                    if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getLore() != null) {

                        if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getLore().get(1).equals(new ItemSlingshot().getLore().get(1))) {

                            if (event.getPlayer().getInventory().contains(Material.COBBLESTONE) || event.getPlayer().getInventory().contains(Material.STONE) || event.getPlayer().getInventory().contains(Material.GRAVEL)) {


                        /*ItemStack arrow = new ItemStack(Material.ARROW);
                        ItemMeta meta = arrow.getItemMeta();
                        meta.setDisplayName("§7Fake arrow");
                        ArrayList<String> lore = new ArrayList<String>();
                        lore.add("§7Fake arrow for the slingshot");
                        arrow.setItemMeta(meta);
                        ((CraftPlayer) event.getPlayer()).getHandle().playerConnection.sendPacket(new PacketPlayOutSetSlot(0, 9, CraftItemStack.asNMSCopy(arrow)));
                        */
                                if (!(Main.getInstance().getUtilLists().reloading.isEmpty())) {
                                    if (Main.getInstance().getUtilLists().reloading.contains(event.getPlayer().getUniqueId())) {
                                        event.getPlayer().sendActionBar(Lang.CUSTOM_SLINGSHOT_RELOADING_ACTIONBAR.get(event.getPlayer()));
                                        return;
                                    }
                                }
                                String[] splitted = event.getPlayer().getInventory().getItemInMainHand().getLore().get(2).split(":");
                                String predurability = splitted[1].replace(" ", "");
                                int durability = Integer.parseInt(predurability);

                                int newDurability = durability - 1;
                                if (newDurability == 0) {
                                    removeOne(event.getPlayer().getInventory().getItemInMainHand(), event.getPlayer());
                                } else {
                                    ArrayList<String> lore = new ArrayList<String>();
                                    lore.add(new ItemSlingshot().getLore().get(0));
                                    lore.add(new ItemSlingshot().getLore().get(1));
                                    lore.add(splitted[0] + ": " + newDurability);
                                    event.getPlayer().getInventory().getItemInMainHand().setLore(lore);
                                }
                                Bukkit.getPluginManager().callEvent(new SlingShotEvent(event.getPlayer(), "SnowStone"));


                            }
                        }
                    }
                }
            }
        }
    }

    public void removeOne(ItemStack i, Player p) {
        HashMap<Integer, ? extends ItemStack> map = p.getInventory().all(i);
        if (map.isEmpty()) {
            return;
        }
        for (Map.Entry<Integer, ? extends ItemStack> entry : map.entrySet())
            if (entry.getValue().getAmount() != 0) {
                if (entry.getValue().getAmount() == 1) p.getPlayer().getInventory().setItem(entry.getKey(), null);
                else {
                    entry.getValue().setAmount(entry.getValue().getAmount() - 1);
                    p.getPlayer().getInventory().setItem(entry.getKey(), entry.getValue());
                }
                break;
            }
    }


}