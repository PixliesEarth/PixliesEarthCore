package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;

public class capitalNation extends SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"capital", "setcapital"};
    }

    @Override
    public Map<String, Integer> autoCompletion() {
        return Collections.emptyMap();
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, String[] args) {

        return true;
    }

}
