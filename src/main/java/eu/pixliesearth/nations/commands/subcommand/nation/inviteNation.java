package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.managers.NationManager;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

public class inviteNation implements SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"invite"};
    }

    @Override
    public Map<String, Integer> autoCompletion() {
        Map<String, Integer> returner = new HashMap<>();
        returner.put("remove", 2);
        returner.put("add", 2);
        for (Map.Entry<String, String> entry : NationManager.names.entrySet())
            returner.put(entry.getKey(), 3);
        return returner;
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
