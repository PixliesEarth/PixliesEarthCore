package eu.pixliesearth.core.custom.commands.subcommands.gulag;

import eu.pixliesearth.core.custom.CustomSubCommand;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.utils.Methods;
import eu.pixliesearth.warsystem.gulag.Gulag;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetLocationOneCommand extends CustomSubCommand {

    @Override
    public String getCommandName() {
        return "setlocationone";
    }

    @Override
    public String getCommandUsage() {
        return "Set the first spawn for the gulag.";
    }

    @Override
    public boolean onExecuted(CommandSender commandSender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
        if (!ranByPlayer) {
            Lang.ONLY_PLAYERS_EXEC.send(commandSender);
            return false;
        }
        Player player = (Player) commandSender;
        Profile profile = instance.getProfile(player.getUniqueId());
        if (!profile.isStaff()) {
            Lang.NO_PERMISSIONS.send(player);
            return false;
        }
        Gulag gulag = instance.getGulag();
        gulag.setFighterOne(Methods.locationToSaveableString(player.getLocation()));
        instance.setGulag(gulag);
        instance.getConfig().set("gulag", instance.getGson().toJson(gulag));
        instance.saveConfig();
        player.sendMessage("§7Changed the first spawn.");
        return true;
    }

}
