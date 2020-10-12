package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.nation.Nation;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

public class xpNation extends SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"setnxp"};
    }

    @Override
    public Map<String, Integer> autoCompletion() {
        return new HashMap<>();
    }

    @Override
    public boolean staff() {
        return true;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length != 2) {
            Lang.WRONG_USAGE_NATIONS.send(sender, "%USAGE%;/n setnxp NATION AMOUNT");
            return false;
        }
        Nation nation = Nation.getByName(args[0]);
        int amount = Integer.parseInt(args[1]);
        nation.setXpPoints(amount);
        nation.save();
        Lang.PLAYER_ADDED_X.send(sender, "%PLAYER%;" + sender.getName(), "%X%;" + amount, "%Y%;Nation-XP");
        return false;
    }

}
