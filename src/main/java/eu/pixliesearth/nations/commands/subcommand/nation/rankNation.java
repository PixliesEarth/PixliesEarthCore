package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

public class rankNation implements SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"rank", "permissions", "perms"};
    }

    @Override
    public Map<String, Integer> autoCompletion() {
        //TODO
        return new HashMap<>();
    }

    @Override
    public boolean staff() {
        return false;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        return false;
    }

}
