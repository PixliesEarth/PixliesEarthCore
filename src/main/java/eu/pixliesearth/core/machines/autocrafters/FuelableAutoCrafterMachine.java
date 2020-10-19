package eu.pixliesearth.core.machines.autocrafters;

import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import eu.pixliesearth.core.machines.cargo.InputNode;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.entities.chunk.NationChunk;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.utils.*;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.Map;

import static org.bukkit.Material.BLACK_STAINED_GLASS_PANE;

public class FuelableAutoCrafterMachine extends AutoCrafterMachine {

    public static final ItemStack item = new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/368ab8cf594da55f90f593bdc0b41e05925d75daa3ee7f1b49c2421d2bdead0")).setDisplayName("§c§lFuelableAutoCrafter").build();
    protected int fuel;

    public FuelableAutoCrafterMachine(String id, Location location, MachineType type, ItemStack item, Timer timer, Hologram armorStand, MachineCraftable wantsToCraft) {
        super(id, location, type, item, timer, armorStand, wantsToCraft);
    }

    public FuelableAutoCrafterMachine(String id, Location location, Hologram armorStand, Timer timer, MachineCraftable wantsToCraft, MachineType machineType, int fuel) {
        super(id, location, machineType, machineType.getItem(), timer, armorStand, wantsToCraft);
        this.fuel = fuel;
    }

    @Override
    public void open(Player player) {
        NationChunk nc = NationChunk.get(location.getChunk());
        if (nc == null) {
            player.sendActionBar(Lang.MACHINES_NEED_TO_BE_IN_CLAIMED_TERRITORY.get(player));
            return;
        }
        if (inventory != null) {
            player.openInventory(inventory);
            return;
        }
        Nation nation = nc.getCurrentNation();
        Gui gui = new Gui(instance, 6, getTitle());
        OutlinePane outline = new OutlinePane(0, 0, 9, 6);
        outline.addItem(new GuiItem(new ItemBuilder(BLACK_STAINED_GLASS_PANE).setNoName().build(), event -> event.setCancelled(true)));
        outline.setRepeat(true);
        gui.addPane(outline);
        StaticPane fuelPane = new StaticPane(4, 5, 1, 1);
        fuelPane.addItem(new GuiItem(new ItemBuilder(CustomItemUtil.getItemStackFromUUID("Pixlies:Carbon")).setDisplayName("§c§lFUEL").addLoreLine("§e" + fuel).build(), event -> {
            event.setCancelled(true);
            if (fuel != 0) return;
            boolean take = Methods.removeRequiredAmount(CustomItemUtil.getItemStackFromUUID("Pixlies:Carbon"), player.getInventory());
            if (take) {
                fuel = 100;
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1, 1);
            } else {
                player.playSound(player.getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 1, 1);
            }
        }), 0, 0);
        gui.addPane(fuelPane);
        StaticPane pane = new StaticPane(1, 1, 9, 4);
        int x = 0;
        int y = 0;
        for (MachineCraftable item : MachineCraftable.values()) {
            if (!item.type.equals(type)) continue;
            if (x + 1 > 7) {
                x = 0;
                y++;
            }
            ItemBuilder iconBuilder = new ItemBuilder(item.icon);
            iconBuilder.addLoreLine("§f§lLEFT §7click to open crafter");
            iconBuilder.addLoreLine("§7Time: §b" + item.seconds + " §7second(s)");
            if (item.eraNeeded.canAccess(nation)) {
                iconBuilder.setDisplayName(item.icon.hasItemMeta() ? "§a§l" + item.icon.getItemMeta().getDisplayName() : "§a§l" + item.icon.getI18NDisplayName());
                iconBuilder.addLoreLine("§7Era needed: §a" + item.eraNeeded.getName());
            } else {
                iconBuilder.setDisplayName(item.icon.hasItemMeta() ? "§c§l" + item.icon.getItemMeta().getDisplayName() : "§c§l" + item.icon.getI18NDisplayName());
                iconBuilder.addLoreLine("§7Era needed: §c" + item.eraNeeded.getName());
            }
            pane.addItem(new GuiItem(iconBuilder.build(), event -> {
                event.setCancelled(true);
                if (event.getClick() != ClickType.LEFT) return;
                if (item.eraNeeded.canAccess(nation))
                    openItemCrafter(player, item);
            }), x, y);
            x++;
        }
        gui.addPane(pane);
        gui.show(player);
    }

    @Override
    protected void beforeUpdate() {
        if (fuel == 0) {
            int radius = 1;
            final Block block = location.getBlock();
            x:
            for (int x = -(radius); x <= radius; x++) {
                for (int z = -(radius); z <= radius; z++) {
                    final Block relative = block.getRelative(x, 0, z);
                    if (instance.getUtilLists().machines.containsKey(relative.getLocation()) && instance.getUtilLists().machines.get(relative.getLocation()) instanceof InputNode) {
                        InputNode in = (InputNode) instance.getUtilLists().machines.get(relative.getLocation());
                        if (timer == null) {
                            boolean take = in.takeItem(CustomItemUtil.getItemStackFromUUID("Pixlies:Carbon"));
                            if (take)
                                fuel = 100;
                        }
                        break x;
                    }
                }
            }
        }
    }

    @Override
    public Map<String, String> extras() {
        return Collections.singletonMap("fuel", fuel + "");
    }

}
