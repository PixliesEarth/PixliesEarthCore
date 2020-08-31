package eu.pixliesearth.core.customitems.ci.weapons.reach;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.customitems.CustomItem;
import eu.pixliesearth.core.customitems.listeners.ItemsInteractEvent;
import eu.pixliesearth.events.SlingShotEvent;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.utils.ItemBuilder.NBTTagType;
import eu.pixliesearth.utils.ItemBuilder.NBTUtil;
import eu.pixliesearth.utils.ItemBuilder.NBTUtil.NBTTags;

public class ItemSlingshot implements CustomItem {

    @Override
    public ItemStack getItem() {
        ItemStack slingshot = new ItemStack(Material.STICK);
        ItemMeta meta = slingshot.getItemMeta();
        meta.setDisplayName("§6Slingshot");
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("§7Era: 1");
        lore.add("§7Requires stone like objects to shoot");
        lore.add("§7Durability: 15");
        meta.setLore(lore);
        meta.setCustomModelData(1);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.setUnbreakable(true);
        //NamespacedKey key = new NamespacedKey(Main.getInstance(), "antistack");
        Random r = new Random();
        double randomValue = 1 + (10000 - 1) * r.nextDouble();
        
        //meta.getCustomTagContainer().setCustomTag(key, ItemTagType.DOUBLE, randomValue);
        slingshot.setItemMeta(meta);
        
        NBTTags tags = NBTUtil.getTagsFromItem(slingshot);
        tags.addTag("antistack", randomValue, NBTTagType.DOUBLE);
        slingshot = NBTUtil.addTagsToItem(slingshot, tags);

        // net.minecraft.server.v1_15_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(slingshot);
        //NBTTagCompound comp = nmsItem.getTag();
        //comp.set()
        return slingshot;
    }

    @Override
    public String getName() {
        return getItem().getItemMeta().getDisplayName();
    }

    @Override
    public List<String> getLore() {
        List<String> lore = new ArrayList<>();
        lore.add("§7Era: 1");
        lore.add("§7Requires stone like objects to shoot");
        lore.add("§7Durability: 15");
        return lore;
    }

    @Override
    public ItemStack getStatic(int durability) {
        ItemStack slingshot = new ItemStack(Material.STICK);
        ItemMeta meta = slingshot.getItemMeta();
        meta.setDisplayName("§6Slingshot");
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("§7Era: 1");
        lore.add("§7Requires stone like objects to shoot");
        lore.add("§7Durability: " + durability);
        meta.setLore(lore);
        meta.setCustomModelData(1);
        slingshot.setItemMeta(meta);
        return slingshot;
    }

    @Override
    public boolean enchantable() {
        return false;
    }

    @Override
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.LEFT_CLICK_BLOCK) || event.getAction().equals(Action.LEFT_CLICK_AIR)) {
            if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.STICK) {
                if (event.getPlayer().getInventory().getItemInMainHand().hasItemMeta()) {
                    if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasLore()) {
                        if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getLore().get(1).equals(new ItemSlingshot().getLore().get(1))) {
                            if (event.getPlayer().getInventory().contains(Material.COBBLESTONE) || event.getPlayer().getInventory().contains(Material.STONE) || event.getPlayer().getInventory().contains(Material.GRAVEL)) {
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
                                    ItemsInteractEvent.removeOne(event.getPlayer().getInventory().getItemInMainHand(), event.getPlayer());
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

}
