package eu.pixliesearth.core.custom.commands;

import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import eu.pixliesearth.Main;
import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.core.custom.CustomSubCommand;
import eu.pixliesearth.core.custom.commands.subcommands.war.AcceptCommand;
import eu.pixliesearth.core.custom.commands.subcommands.war.DeclareWarGoalCommand;
import eu.pixliesearth.core.custom.commands.subcommands.war.InvitePlayerCommand;
import eu.pixliesearth.core.custom.commands.subcommands.war.JustifyWarGoalCommand;
import eu.pixliesearth.core.custom.interfaces.ITabable;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.entities.nation.Ideology;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.entities.nation.Religion;
import eu.pixliesearth.nations.entities.nation.ranks.Permission;
import eu.pixliesearth.nations.managers.NationManager;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.Methods;
import eu.pixliesearth.warsystem.War;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.*;

public class WarCommand extends CustomCommand {

    @Override
    public String getCommandName() {
        return "war";
    }
    
    @Override
    public String getCommandDescription() {
    	return "War command";
    }
    
    @Override
    public boolean isPlayerOnlyCommand() {
    	return true;
    }
    
    @Override
    public boolean onExecuted(CommandSender commandSender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
        Player player = (Player) commandSender;
        Profile profile = instance.getProfile(player.getUniqueId());
        if (!profile.isInNation()) {
            Lang.NOT_IN_A_NATION.send(player);
            return false;
        }
        if (!Permission.hasNationPermission(profile, Permission.MANAGE_WAR)) {
            Lang.NO_PERMISSIONS.send(player);
            return false;
        }
        Nation nation = profile.getCurrentNation();
        Gui gui = new Gui(instance, 6 , "§c§lWar");
        PaginatedPane wars = new PaginatedPane(0, 0, 9, 5);
        List<GuiItem> guiItems = new ArrayList<>();
        for (War war : War.getWars(nation)) {
            guiItems.add(new GuiItem(new ItemBuilder(war.getDefenderInstance().getFlag()) {{
                    setDisplayName(war.getDefenderInstance().getName());
                    addLoreLine("§7War-ID: §c" + war.getId());
                    addLoreLine("§7Declarable: " + (war.isDeclareAble() ? "§aYes" : "§cNo"));
                    if (!war.isDeclareAble())
                        addLoreLine("§7Declarable in: §c" + war.getTimeUntilDeclarable());
            }}.build(), event -> {
                event.setCancelled(true);
                if (war.isDeclareAble()) {
                    ((Player) event.getWhoClicked()).performCommand("war declare " + war.getId());
                    profile.getAsPlayer().closeInventory();
                }
            }));
        }
        wars.populateWithGuiItems(guiItems);
        gui.addPane(wars);
        StaticPane hotBar = new StaticPane(0, 5, 9, 1);
        hotBar.fillWith(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setNoName().build(), event -> event.setCancelled(true));
        hotBar.addItem(new GuiItem(new ItemBuilder(Material.NETHER_STAR).setDisplayName("§b§lJustify war-goal").build(), event -> {
            event.setCancelled(true);
            openJustificationGui(profile, nation, gui);
        }), 4, 0);
        gui.addPane(hotBar);
        gui.show(player);
        return true;
    }

    private static void openJustificationGui(Profile profile, Nation nation, Gui gui) {
        gui.getPanes().clear();
        PaginatedPane nations = new PaginatedPane(0, 0, 9, 5);
        List<GuiItem> guiItems = new ArrayList<>();
        for (Nation n : NationManager.nations.values()) {
            if (n.getNationId().equals(nation.getNationId())) continue;
            if (Nation.getRelation(nation.getNationId(), n.getNationId()) == Nation.NationRelation.ALLY) continue;
            guiItems.add(new GuiItem(new ItemBuilder(n.getFlag()){{
                setDisplayName("§c" + n.getName());
                addLoreLine("§7Leader: §c" + n.getLeaderName());
                addLoreLine("§7Ideology: " + Ideology.valueOf(n.getIdeology()).getDisplayName());
                addLoreLine("§7Religion: " + Religion.valueOf(n.getReligion()).getDisplayName());
                addLoreLine(" ");
                addLoreLine("§7§oClick to justify war-goal");
            }}.build(), event -> {
                event.setCancelled(true);
                profile.getAsPlayer().performCommand("war justify " + n.getName());
                profile.getAsPlayer().closeInventory();
            }));
        }
        nations.populateWithGuiItems(guiItems);
        gui.addPane(nations);
        StaticPane hotBar = new StaticPane(0, 5, 9, 1);
        hotBar.fillWith(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setNoName().build(), event -> event.setCancelled(true));
        hotBar.addItem(new GuiItem(new ItemBuilder(Material.HEART_OF_THE_SEA).setDisplayName("§bLast page").build(), event -> {
            event.setCancelled(true);
            try {
                nations.setPage(nations.getPage() - 1);
                gui.show(profile.getAsPlayer());
            } catch (Exception ignored) { }
        }), 0, 0);
        hotBar.addItem(new GuiItem(new ItemBuilder(Material.HEART_OF_THE_SEA).setDisplayName("§bNext page").build(), event -> {
            event.setCancelled(true);
            try {
                nations.setPage(nations.getPage() + 1);
                gui.show(profile.getAsPlayer());
            } catch (Exception ignored) { }
        }), 8, 0);
        gui.addPane(hotBar);
        gui.show(profile.getAsPlayer());
    }
    
    @Override
    public ITabable[] getParams() {
    	return new ITabable[] {new CustomSubCommand.TabableSubCommand(new JustifyWarGoalCommand(), new DeclareWarGoalCommand(), new InvitePlayerCommand(), new AcceptCommand())};
    }
    
    public static class TabableNation implements ITabable {

		@Override
		public List<String> getTabable(CommandSender commandSender, String[] params) {
			List<String> list = new ArrayList<>();
			StringUtil.copyPartialMatches(params[params.length-1], Methods.convertSetIntoList(NationManager.names.keySet()), list);
	        Collections.sort(list);
	        return list;
		}
    	
		@Override
		public String getTabableName() {
			return "nation";
		}
		
    }
    
}
