package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

public class settlementsCommand implements SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"settlements"};
    }

    @Override
    public Map<String, Integer> autoCompletion() {
        Map<String, Integer> returner = new HashMap<>();
        returner.put("travel", 1);
        returner.put("remove", 1);
        returner.put("add", 1);
        return returner;
    }

    @Override
    public boolean staff() {
        return false;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        //TODO
        return false;
    }

}
