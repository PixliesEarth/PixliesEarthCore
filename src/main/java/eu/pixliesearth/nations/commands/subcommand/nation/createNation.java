package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.nations.commands.subcommand.RegisterSub;
import eu.pixliesearth.nations.managers.NationManager;
import eu.pixliesearth.utils.Methods;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.nation.Era;
import eu.pixliesearth.nations.entities.nation.Ideology;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.entities.nation.Religion;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RegisterSub(
        command = "nations"
)
public class createNation implements SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"create", "form"};
    }

    @Override
    public Map<String, Integer> autoCompletion() {
        Map<String, Integer> returner = new HashMap<>();
        returner.put("§bNAME", 1);
        return returner;
    }

    @Override
    public boolean staff() {
        return false;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Lang.ONLY_PLAYERS_EXEC.get(sender));
            return false;
        }
        Player player = (Player) sender;
        Profile profile = instance.getProfile(player.getUniqueId());
        if (profile.isInNation()) {
            player.sendMessage(Lang.ALREADY_IN_NATION.get(player));
            return false;
        }
        if (args.length != 1) {
            player.sendMessage(Lang.WRONG_USAGE_NATIONS.get(player).replace("%USAGE%", "/n create <name>"));
            return false;
        }
        String name = args[0];
        if (Nation.getByName(name) != null) {
            player.sendMessage(Lang.NATION_WITH_NAME_ALREADY_EXISTS.get(player));
            return false;
        }
        if (name.length() < 3 || name.length() > 10 || !StringUtils.isAlphanumeric(name)) {
            player.sendMessage(Lang.NATION_NAME_UNVALID.get(player));
            return false;
        }
        Nation nation = new Nation(Methods.generateId(7), name, "No description :(", Era.START.getName(), Ideology.DEMOCRACY.name(), Religion.ATHEIST.name(),0,0.0, player.getUniqueId().toString(), new HashMap<>(), new ArrayList<>(), new ArrayList<>());
        nation.save();
        profile.addToNation(nation.getNationId());
        for (Player op : Bukkit.getOnlinePlayers())
            op.sendMessage(Lang.PLAYER_FORMED_NATION.get(op).replace("%PLAYER%", player.getDisplayName()).replace("%NAME%", name));
        return false;
    }

}
