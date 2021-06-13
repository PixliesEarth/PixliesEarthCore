package eu.pixliesearth.core.custom.commands.subcommands.vendors;

import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.core.custom.CustomSubCommand;
import eu.pixliesearth.core.custom.interfaces.ITabable;
import eu.pixliesearth.localization.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class OpenCommand extends CustomSubCommand {

    @Override
    public String getCommandName() {
        return "open";
    }

    @Override
    public String getCommandUsage() {
        return "Open Vendor for Player";
    }

    @Override
    public boolean onExecuted(CommandSender commandSender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
        if (!commandSender.hasPermission("earth.admin")) {
            Lang.NO_PERMISSIONS.send(commandSender);
            return false;
        }
        instance.getVendor().open(Bukkit.getPlayer(parameters[0]));
        return false;
    }

    @Override
    public ITabable[] getParams() {
        return new ITabable[] {new CustomCommand.TabablePlayer()};
    }

}
