package eu.pixliesearth.core.custom.commands.subcommands.spawn;

import eu.pixliesearth.core.custom.CustomSubCommand;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand extends CustomSubCommand {

    @Override
    public String getCommandName() {
        return "setspawn";
    }

    @Override
    public String getCommandUsage() {
        return "Set Spawn";
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
            Lang.NO_PERMISSIONS.send(commandSender);
            return false;
        }
        instance.getConfig().set("spawn-location", player.getLocation());
        instance.saveConfig();
        instance.reloadConfig();
        instance.getFastConf().setSpawnLocation(player.getLocation());
        player.sendMessage(Lang.EARTH + "§7Spawn set.");
        return true;
    }

}
