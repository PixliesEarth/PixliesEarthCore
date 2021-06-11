package eu.pixliesearth.core.custom.commands.subcommands.war;

import eu.pixliesearth.core.custom.CustomSubCommand;
import eu.pixliesearth.core.custom.interfaces.ITabable;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.entities.nation.ranks.Permission;
import eu.pixliesearth.warsystem.War;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CancelJustificationCommand extends CustomSubCommand {


    @Override
    public String getCommandName() {
        return "canceljustification";
    }

    @Override
    public String getCommandUsage() {
        return "Cancel a justification.";
    }

    @Override
    public ITabable[] getParams() {
        return new ITabable[]{new DeclareWarGoalCommand.TabableWars()};
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
        if (!Permission.hasNationPermission(profile, Permission.MANAGE_WAR)) {
            Lang.NO_PERMISSIONS.send(player);
            return false;
        }
        War war = War.getById(parameters[0]);
        if (war == null || !war.getMainAggressor().equals(profile.getNationId())) {
            player.sendMessage(Lang.WAR + "§cThis war does not exist.");
            return false;
        }
        war.cancel();
        player.sendMessage(Lang.WAR + "§7War cancelled.");
        return true;
    }

}
