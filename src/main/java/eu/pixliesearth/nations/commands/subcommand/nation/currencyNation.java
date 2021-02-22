package eu.pixliesearth.nations.commands.subcommand.nation;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;

public class currencyNation extends SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"currency"};
    }

    @Override
    public Map<String, Integer> autoCompletion(CommandSender sender, String[] args) {
        Map<String, Integer> returner = new HashMap<>();
        returner.put("NAME", 1);
        returner.put("SHORTNAME", 2);
        returner.put("BANK", 3);
        returner.put("PRICE", 4);
        return returner;
    }

    @Override
    public boolean staff() {
        return false;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, String[] args) {
        if (!checkIfPlayer(sender)) return false;
        Player player = (Player) sender;
        Profile profile = instance.getProfile(player.getUniqueId());
        if (!profile.isInNation()) {
            Lang.NOT_IN_A_NATION.send(player);
            return false;
        }
        // Nation nation = profile.getCurrentNation();
        // NationCurrency currency = new NationCurrency(args[0], args[1], args[2], Double.parseDouble(args[3]), 0);

        return true;
    }

}
