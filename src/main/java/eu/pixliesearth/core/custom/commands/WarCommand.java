package eu.pixliesearth.core.custom.commands;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import eu.pixliesearth.Main;
import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.core.custom.CustomSubCommand;
import eu.pixliesearth.core.custom.commands.subcommands.war.*;
import eu.pixliesearth.core.custom.interfaces.Constants;
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
import eu.pixliesearth.warsystem.WarParticipant;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WarCommand extends CustomCommand implements Constants {

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
        if (!Main.WAR_ENABLED) return false;
        if (!profile.isInNation()) {
            Lang.NOT_IN_A_NATION.send(player);
            return false;
        }
        if (instance.getCurrentWar() != null) {
            War war = instance.getCurrentWar();
            player.sendMessage("  ");
            player.sendMessage(Methods.getCenteredMessage("§c" + war.getAggressorInstance().getName() + " §8- §c" + war.getDefenderInstance().getName()));
            if (war.getTimers().containsKey("gracePeriod")) {
                player.sendMessage(Methods.getCenteredMessage("§7Grace period"));
                player.sendMessage(Methods.getCenteredMessage("§b" + war.getTimers().get("gracePeriod").getRemainingAsString()));
            }
            player.sendMessage(Methods.getCenteredMessage("§7Players left"));
            player.sendMessage(Methods.getCenteredMessage("§c" + war.getLeft().get(WarParticipant.WarSide.AGGRESSOR) + " §8- §c" + war.getLeft().get(WarParticipant.WarSide.DEFENDER)));
            player.sendMessage("  ");
            return false;
        }
        if (!Permission.hasNationPermission(profile, Permission.MANAGE_WAR)) {
            Lang.NO_PERMISSIONS.send(player);
            return false;
        }
        Nation nation = profile.getCurrentNation();
        ChestGui gui = new ChestGui(6 , "§c§lWar");
        PaginatedPane wars = new PaginatedPane(0, 0, 9, 5);
        List<GuiItem> guiItems = new ArrayList<>();
        for (War war : War.getWars(nation)) {
            guiItems.add(new GuiItem(new ItemBuilder(war.getDefenderInstance().getFlag()) {{
                    setDisplayName("§c§l" + war.getDefenderInstance().getName());
                    addLoreLine("§7War-ID: §c" + war.getId());
                    addLoreLine("§7Declarable: " + (war.isDeclareAble() ? "§aYes" : "§cNo"));
                    if (!war.isDeclareAble())
                        addLoreLine("§7Declarable in: §c" + war.getTimeUntilDeclarable());
                    addLoreLine(" ");
                    addLoreLine("§f§lLEFT §7click to §a§ldeclare");
                    addLoreLine("§f§lRIGHT §7click to §c§ldelete");
            }}.build(), event -> {
                event.setCancelled(true);
                if (event.getClick().equals(ClickType.LEFT)) {
                    if (war.isDeclareAble()) {
                        ((Player) event.getWhoClicked()).performCommand("war declare " + war.getId());
                        profile.getAsPlayer().closeInventory();
                    }
                } else if (event.getClick().equals(ClickType.RIGHT)) {
                    ((Player)event.getWhoClicked()).performCommand("war canceljustification " + war.getId());
                    profile.getAsPlayer().closeInventory();
                }
            }));
        }
        wars.populateWithGuiItems(guiItems);
        gui.addPane(wars);
        StaticPane hotBar = new StaticPane(0, 5, 9, 1);
        hotBar.fillWith(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setNoName().build(), event -> event.setCancelled(true));
        hotBar.addItem(new GuiItem(new ItemBuilder(Material.HAY_BLOCK).setDisplayName("§b§lJustify war-goal").build(), event -> {
            event.setCancelled(true);
            openJustificationGui(profile, nation, gui);
        }), 4, 0);
        gui.addPane(hotBar);
        gui.show(player);
        return true;
    }

    private static void openJustificationGui(Profile profile, Nation nation, ChestGui gui) {
        gui.getPanes().clear();
        PaginatedPane nations = new PaginatedPane(0, 0, 9, 5);
        List<GuiItem> guiItems = new ArrayList<>();
        for (Nation n : NationManager.nations.values()) {
            if (n.getNationId().equals(nation.getNationId())) continue;
            if (Nation.getRelation(nation.getNationId(), n.getNationId()) == Nation.NationRelation.ALLY) continue;
            if (n.getLeaderName().equalsIgnoreCase("Server")) continue;
            guiItems.add(new GuiItem(new ItemBuilder(n.getFlag()){{
                setDisplayName("§c" + n.getName());
                addLoreLine("§7Cost: §3" + War.getCost(profile.getCurrentNation(), n) + "§bPP");
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
        hotBar.addItem(new GuiItem(backButtonMick, event -> {
            event.setCancelled(true);
            try {
                nations.setPage(nations.getPage() - 1);
                gui.addPane(nations);
                gui.show(profile.getAsPlayer());
            } catch (Exception ignored) { }
        }), 0, 0);
        hotBar.addItem(new GuiItem(nextButtonMick, event -> {
            event.setCancelled(true);
            try {
                nations.setPage(nations.getPage() + 1);
                gui.addPane(nations);
                gui.show(profile.getAsPlayer());
            } catch (Exception ignored) { }
        }), 8, 0);
        gui.addPane(hotBar);
        gui.show(profile.getAsPlayer());
    }
    
    @Override
    public ITabable[] getParams() {
    	return new ITabable[] {new CustomSubCommand.TabableSubCommand(new JustifyWarGoalCommand(), new DeclareWarGoalCommand(), new InvitePlayerCommand(), new AcceptCommand(), new CancelJustificationCommand(), new SkipJustificationCommand(), new SkipGraceCommand(), new StopCommand(), new ListCommand(), new KickCommand())};
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
