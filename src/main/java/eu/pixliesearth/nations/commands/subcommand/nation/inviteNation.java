package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class inviteNation implements SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"invite"};
    }

    @Override
    public String[] autocompletion() {
        List<String> returner = new ArrayList<>();
        return new String[]{};
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
