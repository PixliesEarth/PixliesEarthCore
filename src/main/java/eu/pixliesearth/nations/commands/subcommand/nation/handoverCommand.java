package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.entities.nation.ranks.Permission;
import eu.pixliesearth.nations.entities.nation.ranks.Rank;
import eu.pixliesearth.nations.managers.NationManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class handoverCommand extends SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"handover", "leader"};
    }

    @Override
    public Map<String, Integer> autoCompletion() {
        Map<String, Integer> returner = new HashMap<>();
        for (Player player : Bukkit.getOnlinePlayers())
            returner.put(player.getName(), 1);
        for (String s : NationManager.names.keySet())
            returner.put(s, 2);
        return returner;
    }

    @Override
    public boolean staff() {
        return false;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, String[] args) {
        OfflinePlayer targetPlayer = Bukkit.getOfflinePlayerIfCached(args[0]);
        if (targetPlayer == null) {
            Lang.PLAYER_DOES_NOT_EXIST.send(sender);
            return false;
        }
        Profile target = instance.getProfile(targetPlayer.getUniqueId());
        Nation nation;
        if (args.length > 1) {
            nation = Nation.getByName(args[1]);
        } else {
            if (!target.isInNation()) {
                sender.sendMessage(Lang.NATION + "§7Player does not have a nation.");
                return false;
            }
            nation = target.getCurrentNation();
        }
        if (sender instanceof Player) {
            Profile profile = instance.getProfile(((Player) sender).getUniqueId());
            if (!profile.isStaff() && !profile.isInNation()) {
                Lang.NO_PERMISSIONS.send(sender);
                return false;
            }
            if (profile.isInNation() && !Permission.CHANGE_LEADERSHIP.hasPermission(sender)) {
                Lang.NO_PERMISSIONS.send(sender);
                return false;
            }
        }
        if (!nation.getLeader().equalsIgnoreCase("NONE")) {
            Profile leader = instance.getProfile(UUID.fromString(nation.getLeader()));
            leader.setNationRank(Rank.ADMIN().getName());
            leader.save();
        }
        if (!target.getNationId().equals(nation.getNationId())) {
            Nation targetNation = target.getCurrentNation();
            if (target.isLeader()) {
                targetNation.setLeader("NONE");
                targetNation.getMembers().remove(target.getUniqueId());
                targetNation.save();
                target.setNationId(nation.getNationId());
            } else {
                targetNation.getMembers().remove(target.getUniqueId());
                targetNation.save();
            }
            nation.getMembers().add(target.getUniqueId());
        }
        nation.setLeader(target.getUniqueId());
        nation.save();
        target.setNationRank("leader");
        target.save();
        Lang.PLAYER_TRANSFERED_LEADERSHIP.broadcast("%PLAYER%;" + sender.getName(), "%NATION%;" + nation.getName(), "%TARGET%;" + target.getAsOfflinePlayer().getName());
        return false;
    }

}
