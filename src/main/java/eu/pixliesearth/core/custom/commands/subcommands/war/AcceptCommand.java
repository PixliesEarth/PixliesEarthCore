package eu.pixliesearth.core.custom.commands.subcommands.war;

import eu.pixliesearth.core.custom.CustomSubCommand;
import eu.pixliesearth.localization.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AcceptCommand extends CustomSubCommand {


    @Override
    public String getCommandName() {
        return "accept";
    }

    @Override
    public String getCommandUsage() {
        return "Accept war invitation.";
    }

    @Override
    public boolean onExecuted(CommandSender commandSender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
        if (!ranByPlayer) {
            Lang.ONLY_PLAYERS_EXEC.send(commandSender);
            return false;
        }
        Player player = (Player) commandSender;
        if (!instance.getUtilLists().invitationsToWar.containsKey(player.getUniqueId())) {
            player.sendMessage(Lang.WAR + "§7You do not have any open invitations.");
            return false;
        }
        instance.getUtilLists().wars.get(instance.getUtilLists().invitationsToWar.get(player.getUniqueId()).getWar()).addPlayer(player, instance.getUtilLists().invitationsToWar.get(player.getUniqueId()));
        instance.getUtilLists().wars.get(instance.getUtilLists().invitationsToWar.get(player.getUniqueId()).getWar()).broadcastInGame(Lang.WAR + "§6" + player.getName() + " §7just joined the war.");
        return true;
    }

}
