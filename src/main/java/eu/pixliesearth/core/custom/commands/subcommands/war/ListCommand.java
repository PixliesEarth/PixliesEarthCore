package eu.pixliesearth.core.custom.commands.subcommands.war;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import eu.pixliesearth.core.custom.CustomSubCommand;
import eu.pixliesearth.core.custom.interfaces.Constants;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.warsystem.War;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ListCommand extends CustomSubCommand implements Constants {

    @Override
    public String getCommandName() {
        return "list";
    }

    @Override
    public String getCommandUsage() {
        return "List wars.";
    }

    @Override
    public boolean onExecuted(CommandSender commandSender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
        if (!ranByPlayer) {
            Lang.ONLY_PLAYERS_EXEC.send(commandSender);
            return false;
        }
        Player player = (Player) commandSender;
        ChestGui gui = new ChestGui(6 , "§c§lWars");
        StaticPane outerPane = new StaticPane(0, 0, 9, 6);
        outerPane.fillWith(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setNoName().build(), event -> event.setCancelled(true));

        PaginatedPane wars = new PaginatedPane(1, 1, 7, 4, Pane.Priority.HIGHEST);
        List<GuiItem> warItems = new ArrayList<>();
        for (War war : instance.getUtilLists().wars.values())
            warItems.add(new GuiItem(
                    new ItemBuilder(Material.HAY_BLOCK){{
                        setDisplayName("§c" + war.getId());
                        addLoreLine("§7Attacker: §b" + war.getAggressorInstance().getName());
                        addLoreLine("§7Defender: §b" + war.getDefenderInstance().getName());
                        addLoreLine("§7Declarable: " + (war.isDeclareAble() ? "§aYes" : "§cNo"));
                        if (!war.isDeclareAble())
                            addLoreLine("§7Declarable in: §c" + war.getTimeUntilDeclarable());
                    }}.build(),
                    event -> event.setCancelled(true)
            ));

        wars.populateWithGuiItems(warItems);

        outerPane.addItem(new GuiItem(backButtonMick, event -> {
            event.setCancelled(true);
            try {
                wars.setPage(wars.getPage() - 1);
                gui.update();
            } catch (Exception ignored) {}
        }), 0, 5);
        outerPane.addItem(new GuiItem(nextButtonMick, event -> {
            event.setCancelled(true);
            try {
                wars.setPage(wars.getPage() + 1);
                gui.update();
            } catch (Exception ignored) {}
        }), 8, 5);

        gui.addPane(outerPane);
        gui.addPane(wars);
        gui.show(player);
        return true;
    }

}
