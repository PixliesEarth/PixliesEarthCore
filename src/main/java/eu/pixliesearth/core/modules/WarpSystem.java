package eu.pixliesearth.core.modules;

import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import eu.pixliesearth.core.interfaces.Module;
import eu.pixliesearth.core.objects.Warp;
import eu.pixliesearth.core.utils.ItemBuilder;
import eu.pixliesearth.core.utils.Methods;
import eu.pixliesearth.core.utils.SkullBuilder;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class WarpSystem implements CommandExecutor, Module {

    @Override
    public String name() {
        return "warp";
    }

    @Override
    public boolean enabled() {
        return config.getBoolean("modules.warps.enabled");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        switch (args.length) {

            case 0:
                if (!(sender instanceof Player)) {
                    sender.sendMessage("§aEARTH §8| §7This command is only accessible as a player.");
                    return false;
                }
                Player player = (Player) sender;
                Gui warpGui = new Gui(instance, 4, "§cWhere do you wanna go?");
                PaginatedPane warps = new PaginatedPane(0, 0, 9, 3);
                StaticPane toolbar = new StaticPane(0, 3, 9, 1);
                List<GuiItem> items = new ArrayList<>();
                for (Warp warp : Warp.getWarps())
                    items.add(new GuiItem(new ItemBuilder(warp.getItem()).setDisplayName(warp.getName()).build(), event -> {
                        event.setCancelled(true);
                        player.closeInventory();
                        warp.teleport(player);
                    }));
                warps.populateWithGuiItems(items);
                toolbar.fillWith(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setNoName().build());
                if (player.hasPermission("earth.warps.edit"))
                    toolbar.addItem(new GuiItem(new SkullBuilder("{display:{Name:\\\"Black Plus\\\"},SkullOwner:{Id:\\\"4721d0df-0210-403f-a281-e0467b104fb6\\\",Properties:{textures:[{Value:\\\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWEyZDg5MWM2YWU5ZjZiYWEwNDBkNzM2YWI4NGQ0ODM0NGJiNmI3MGQ3ZjFhMjgwZGQxMmNiYWM0ZDc3NyJ9fX0=\\\"}]}}}", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWEyZDg5MWM2YWU5ZjZiYWEwNDBkNzM2YWI4NGQ0ODM0NGJiNmI3MGQ3ZjFhMjgwZGQxMmNiYWM0ZDc3NyJ9fX0=").setDisplayname("§aAdd").build(), event -> {
                        event.setCancelled(true);
                        player.performCommand("warp add");
                    }), 4, 0);
                warpGui.addPane(warps);
                warpGui.addPane(toolbar);
                warpGui.show(player);
                break;
            case 1:
                if (args[0].equalsIgnoreCase("add")) {
                    Player player1 = (Player) sender;
                    if (!player1.hasPermission("earth.warps.edit")) {
                        player1.sendMessage("§aEARTH §8| §cInsufficent permissions.");
                        return false;
                    }
                    instance.getPlayerLists().warpAdder.add(player1.getUniqueId());
                    player1.sendMessage("§aEARTH §8| §7Enter the name of the warp you want to add in chat.");
                }
                break;
            case 2:
                if (args[0].equalsIgnoreCase("remove")) {
                    if (args[1] == null) {
                        sender.sendMessage("§aEARTH §8| §7You need to specify the warp you want to remove.");
                        return false;
                    }
                    if (!Methods.testPermission(sender, "earth.warps.edit")) {
                        sender.sendMessage("§aEARTH §8| §cInsufficient permissions.");
                        return false;
                    }
                    Warp warp = Warp.get(args[1]);
                    if (warp == null) {
                        sender.sendMessage("§aEARTH §8| §7This warp does not exist.");
                        return false;
                    }
                    warp.remove();
                    sender.sendMessage("§aEARTH §8| §7Warp §b" + warp.getName() + " §7successfully removed.");
                }
                break;

        }
        return false;
    }

}
