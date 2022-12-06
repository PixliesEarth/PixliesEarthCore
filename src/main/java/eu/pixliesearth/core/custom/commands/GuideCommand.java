package eu.pixliesearth.core.custom.commands;

import eu.pixliesearth.core.custom.CustomCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuideCommand extends CustomCommand {

    @Override
    public String getCommandName() {
        return "guide";
    }

    @Override
    public String getCommandDescription() {
        return "Gives the guide books";
    }

    @Override
    public boolean onExecuted(CommandSender commandSender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
        Player player = (Player) commandSender;
        // PixlieFunGuide.giveBooks(player);
        return true;
    }

}
