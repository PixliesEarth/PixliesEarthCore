package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.entities.nation.ranks.Permission;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class wikiNation extends SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"wiki", "setwiki"};
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
    public boolean execute(@NotNull CommandSender sender, String[] args) {
        if (!checkIfPlayer(sender)) return false;
        Player player = (Player) sender;
        Profile profile = instance.getProfile(player.getUniqueId());
        if (!profile.isInNation()) {
            Lang.NOT_IN_A_NATION.send(sender);
            return false;
        }
        if (!Permission.hasNationPermission(profile, Permission.SET_WIKI_LINK)) {
            Lang.NO_PERMISSIONS.send(sender);
            return false;
        }
        if (args.length != 1 || !args[0].toLowerCase().startsWith("https://pixlies.fandom.com/wiki/")) {
            Lang.WRONG_USAGE_NATIONS.send(sender, "%USAGE%;/n wiki https://pixlies.fandom.com/wiki/[NAME]");
            return false;
        }
        Nation nation = profile.getCurrentNation();
        nation.getExtras().put("wikiUrl", args[0]);
        nation.save();
        sender.sendMessage(Lang.NATION + "§7Successfully changed wiki link.");
        return true;
    }

}
