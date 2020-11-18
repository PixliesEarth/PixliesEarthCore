package eu.pixliesearth.core.custom.commands.subcommands.war;

import eu.pixliesearth.core.custom.CustomSubCommand;
import eu.pixliesearth.core.custom.interfaces.ITabable;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.entities.nation.ranks.Permission;
import eu.pixliesearth.utils.Methods;
import eu.pixliesearth.warsystem.War;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class DeclareWarGoalCommand extends CustomSubCommand {

    @Override
    public String getCommandName() {
        return "declare";
    }

    @Override
    public String getCommandUsage() {
        return "Declare a war.";
    }

    @Override
    public ITabable[] getParams() {
        return new ITabable[]{new TabableWars()};
    }

    @Override
    public boolean onExecuted(CommandSender commandSender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
        if (!ranByPlayer) {
            Lang.ONLY_PLAYERS_EXEC.send(commandSender);
            return false;
        }
        Player player = (Player) commandSender;
        Profile profile = instance.getProfile(player.getUniqueId());
        if (!profile.isInNation()) {
            Lang.NOT_IN_A_NATION.send(player);
            return false;
        }
        if (!Permission.hasNationPermission(profile, Permission.DECLARE_WAR)) {
            Lang.NO_PERMISSIONS.send(player);
            return false;
        }
        War war = War.getById(parameters[0]);
        if (war == null || !war.getMainAggressor().equals(profile.getNationId())) {
            player.sendMessage(Lang.WAR + "§cThis war does not exist.");
            return false;
        }
        boolean declare = war.declare();
        if (!declare) {
            player.sendMessage(Lang.WAR + "§cWar is not declarable yet.");
            return false;
        }
        profile.getCurrentNation().broadcastMembers(Lang.WAR + "§6" + player.getName() + " §7just declared a war against §b" + Nation.getById(war.getMainDefender()).getName() + "§7! It will start in §b10 minutes§7.");
        return true;
    }

    public static class TabableWars implements ITabable {

        @Override
        public List<String> getTabable(CommandSender commandSender, String[] params) {
            return Methods.convertSetIntoList(instance.getUtilLists().wars.keySet());
        }

        @Override
        public String getTabableName() {
            return "wars";
        }

    }

}
