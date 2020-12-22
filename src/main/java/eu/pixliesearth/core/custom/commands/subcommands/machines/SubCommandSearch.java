package eu.pixliesearth.core.custom.commands.subcommands.machines;

import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.core.custom.CustomSubCommand;
import eu.pixliesearth.core.custom.interfaces.ITabable;
import eu.pixliesearth.pixliefun.PixliesFunGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SubCommandSearch extends CustomSubCommand {

    @Override
    public String getCommandName() {
        return "search";
    }

    @Override
    public String getCommandUsage() {
        return "Search items";
    }

    @Override
    public ITabable[] getParams() {
        return new ITabable[]{new CustomCommand.TabableString("KeyWord")};
    }

    @Override
    public boolean onExecuted(CommandSender commandSender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
        if (parameters.length == 0) {
            commandSender.sendMessage("Wrong usage.");
            return false;
        }
        new PixliesFunGUI((Player)commandSender).renderSearchItems(parameters[0]);
        return true;
    }

}
