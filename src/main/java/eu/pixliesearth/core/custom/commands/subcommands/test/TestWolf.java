package eu.pixliesearth.core.custom.commands.subcommands.test;
import eu.pixliesearth.core.custom.CustomSubCommand;
import eu.pixliesearth.core.custom.mobs.PixliesWolf;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestWolf extends CustomSubCommand {

    @Override
    public String getCommandName() {
        return "wolf";
    }

    @Override
    public String getCommandUsage() {
        return "";
    }

    @Override
    public boolean onExecuted(CommandSender commandSender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
        new PixliesWolf(((Player)commandSender).getLocation());
        return true;
    }

}
