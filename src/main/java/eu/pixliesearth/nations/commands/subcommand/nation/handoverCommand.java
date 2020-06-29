package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.managers.NationManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class handoverCommand implements SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"handover", "leader"};
    }

    @Override
    public Map<String, Integer> autoCompletion() {
        Map<String, Integer> returner = new HashMap<>();
        for (Player player : Bukkit.getOnlinePlayers())
            returner.put(player.getName(), 1);
        for (String n : NationManager.names.keySet())
            returner.put(n, 2);
        return returner;
    }

    @Override
    public boolean staff() {
        return false;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        switch (args.length) {
            case 1:
                if (!(sender instanceof Player)) {
                    Lang.ONLY_PLAYERS_EXEC.send(sender);
                    return false;
                }
                Player player = (Player) sender;
                Profile profile = instance.getProfile(player.getUniqueId());
                if (!profile.isInNation()) {
                    Lang.NOT_IN_A_NATION.send(player);
                    return false;
                }
                if (!profile.getNationRank().equalsIgnoreCase("leader")) {
                    Lang.YOU_HAVE_TO_BE_LEADER.send(player);
                    return false;
                }
                UUID targetUUID = Bukkit.getPlayerUniqueId(args[0]);
                if (targetUUID == null) {
                    Lang.PLAYER_DOES_NOT_EXIST.send(player);
                    return false;
                }
                Profile target = instance.getProfile(targetUUID);
                if (!profile.getNationId().equals(target.getNationId()))  {
                    Lang.PLAYER_IS_NOT_IN_SAME_NATION_AS_YOU.send(player);
                    return false;
                }
                target.setNationRank("leader");
                profile.setNationRank("admin");
                target.save();
                profile.save();
                Lang.PLAYER_TRANSFERED_LEADERSHIP.broadcast("%PLAYER%;" + player.getName(), "%NATION%;" + profile.getCurrentNation().getName(), "%TARGET%;" + target.getAsOfflinePlayer().getName());
                break;
            case 2:
                break;
        }
        return false;
    }

}
