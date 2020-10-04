package eu.pixliesearth.core.commands.player;

import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import eu.pixliesearth.Main;
import eu.pixliesearth.core.machines.Machine;
import eu.pixliesearth.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import static org.bukkit.Material.BARRIER;
import static org.bukkit.Material.BLACK_STAINED_GLASS_PANE;

public class MachinesCommand implements CommandExecutor {

    private static final Main instance = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command label, String s, String[] args) {
        Player player = (Player) sender;
        execute(player);
        return false;
    }

    private void openMachineRecipe(Player player, Machine.MachineType type) {
        Gui gui = new Gui(instance, 5, type.getItem().getItemMeta().getDisplayName());
        StaticPane pane = new StaticPane(0, 0, 9, 5);
        pane.fillWith(new ItemBuilder(BLACK_STAINED_GLASS_PANE).setNoName().build(), event -> event.setCancelled(true));
        pane.addItem(new GuiItem(type.getWhereToCraft(), event -> event.setCancelled(true)), 4, 2);
        int xC = 0;
        int yC = 0;
        for (ItemStack ingredient : type.getRecipe()) {
            if (xC + 1 > 3) {
                yC++;
                xC = 0;
            }
            pane.addItem(new GuiItem(ingredient, event -> event.setCancelled(true)), xC, yC);
            xC++;
        }
        ItemBuilder builder = new ItemBuilder(type.getItem());
        if (player.hasPermission("earth.machines.admin"))
            builder.addLoreLine("§f§lLEFT §7click to get");
        pane.addItem(new GuiItem(builder.build(), event -> {
            event.setCancelled(true);
            if (event.getClick() != ClickType.LEFT) return;
            if (player.hasPermission("earth.machines.admin"))
                player.getInventory().addItem(type.getItem());
        }), 6, 2);
        pane.addItem(new GuiItem(new ItemBuilder(BARRIER).setDisplayName("§c§lGo back").build(), event -> {
            execute(player);
        }), 4, 4);
        gui.addPane(pane);
        gui.show(player);
        for (int i : Machine.craftSlots)
            if (gui.getInventory().getItem(i).getType() == BLACK_STAINED_GLASS_PANE)
                gui.getInventory().clear(i);
    }

    private void openItemRecipe(Player player, Machine.MachineCraftable item) {
        Gui gui = new Gui(instance, 5, item.icon.getItemMeta().getDisplayName());
        StaticPane pane = new StaticPane(0, 0, 9, 5);
        pane.fillWith(new ItemBuilder(BLACK_STAINED_GLASS_PANE).setNoName().build(), event -> event.setCancelled(true));
        pane.addItem(new GuiItem(item.type.getItem(), event -> event.setCancelled(true)), 4, 2);
        int xC = 0;
        int yC = 0;
        for (ItemStack ingredient : item.ingredients) {
            if (xC + 1 > 3) {
                yC++;
                xC = 0;
            }
            pane.addItem(new GuiItem(ingredient, event -> event.setCancelled(true)), xC, yC);
            xC++;
        }
        int xR = 5;
        int yR = 0;
        for (ItemStack result : item.results) {
            if (xR + 1 > 9) {
                yR++;
                xR = 0;
            }
            pane.addItem(new GuiItem(result, event -> event.setCancelled(true)) , xR, yR);
            xR++;
        }
        ItemBuilder builder = new ItemBuilder(item.results.get(0));
        pane.addItem(new GuiItem(new ItemBuilder(BARRIER).setDisplayName("§c§lGo back").build(), event -> {
            execute(player);
        }), 4, 4);
        gui.addPane(pane);
        gui.show(player);
        for (int i : Machine.craftSlots)
            if (gui.getInventory().getItem(i).getType() == BLACK_STAINED_GLASS_PANE)
                gui.getInventory().clear(i);
        for (int i : Machine.resultSlots)
            if (gui.getInventory().getItem(i).getType() == BLACK_STAINED_GLASS_PANE)
                gui.getInventory().clear(i);
    }

    private void execute(Player player) {
        Gui gui = new Gui(instance, 3, "§b§lMachinery");
        StaticPane categoriesPane = new StaticPane(0, 0, 9, 3);
        categoriesPane.fillWith(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setNoName().build(), event -> event.setCancelled(true));
        Machine.MachineType type = Machine.MachineType.KILN;
        categoriesPane.addItem(new GuiItem(new ItemBuilder(type.getItem().clone()).setDisplayName("§b§lMachines").build(), event -> {
            event.setCancelled(true);
            Gui sixRow = new Gui(instance, 6, "§c§lMachines");
            StaticPane machinesPane = new StaticPane(0, 0, 9, 6);
            machinesPane.fillWith(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setNoName().build(), event1 -> event1.setCancelled(true));
            int x = 0;
            int y = 0;
            for (Machine.MachineType machines : Machine.MachineType.values()) {
                if (x + 1 > 9) {
                    y++;
                    x = 0;
                }
                machinesPane.addItem(new GuiItem(new ItemBuilder(machines.getItem().clone()).addLoreLine("§f§lLEFT §7click for more info").build(), event1 -> {
                    event1.setCancelled(true);
                    if (machines.getRecipe() != null)
                        openMachineRecipe(player, machines);
                }), x, y);
                x++;
            }
            sixRow.addPane(machinesPane);
            sixRow.show(player);
        }), 3, 1);
        Machine.MachineCraftable craftable = Machine.MachineCraftable.MUD_BRICK_KILN;
        categoriesPane.addItem(new GuiItem(new ItemBuilder(craftable.icon.clone()).setDisplayName("§b§lItems").build(), event -> {
            event.setCancelled(true);
            Gui sixRow = new Gui(instance, 6, "§b§lItems");
            StaticPane itemsPane = new StaticPane(0, 0, 9, 6);
            itemsPane.fillWith(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setNoName().build(), event1 -> event1.setCancelled(true));
            for (int i : Machine.craftSlots)
                sixRow.getInventory().clear(i);
            int x = 0;
            int y = 0;
            for (Machine.MachineCraftable craftables : Machine.MachineCraftable.values()) {
                if (x + 1 > 9) {
                    y++;
                    x = 0;
                }
                itemsPane.addItem(new GuiItem(new ItemBuilder(craftables.icon.clone()).addLoreLine("§f§lLEFT §7click for more info").addLoreLine("§7Machine: " + craftables.type.getItem().getItemMeta().getDisplayName()).build(), event1 -> {
                    event1.setCancelled(true);
                    openItemRecipe(player, craftables);
                }), x, y);
                x++;
            }
            sixRow.addPane(itemsPane);
            sixRow.show(player);
        }), 5, 1);
        gui.addPane(categoriesPane);
        gui.show(player);
    }

}
