package eu.pixliesearth.core.custom.commands;

import eu.pixliesearth.core.custom.CustomCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DisposeCommand extends CustomCommand {

    @Override
    public String getCommandName() {
        return "dispose";
    }

    @Override
    public String getCommandDescription() {
        return "dispose command";
    }

    @Override
    public String getPermission() {
        return "earth.dispose";
    }

    @Override
    public boolean isPlayerOnlyCommand() {
        return true;
    }

    @Override
    public boolean onExecuted(CommandSender commandSender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
        ((Player)commandSender).openInventory(Bukkit.createInventory(null, 27, "Disposal"));
        return true;
    }

}
