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
        Map<String, Integer> returner = new HashMap<>();
        returner.put("create", 1);
        returner.put("remove", 1);
        returner.put("addpermission", 1);
        returner.put("removepermission", 1);
        return returner;
    }

    @Override
    public boolean staff() {
        return false;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        switch (args.length) {
            
        }
        return false;
    }

}
