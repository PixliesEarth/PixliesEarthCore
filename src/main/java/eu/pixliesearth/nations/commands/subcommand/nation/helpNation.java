package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.nations.commands.NationCommand;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.Map;

public class helpNation extends SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"help", "commands"};
    }

    @Override
    public Map<String, Integer> autoCompletion() {
        return Collections.emptyMap();
    }

    @Override
    public boolean staff() {
        return false;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        int i = 0;
        if (args.length > 0)
            i = Integer.parseInt(args[0]);
        NationCommand.sendHelp(sender, i);
        return false;
    }

}
