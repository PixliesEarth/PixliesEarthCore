package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.entities.nation.ranks.Permission;
import eu.pixliesearth.nations.managers.NationManager;
import org.bukkit.Bukkit;
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
        UUID targetUUID;
        Profile target;
        Nation nation;
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
                if (!profile.isLeader()) {
                    Lang.YOU_HAVE_TO_BE_LEADER.send(player);
                    return false;
                }
                targetUUID = Bukkit.getPlayerUniqueId(args[0]);
                if (targetUUID == null) {
                    Lang.PLAYER_DOES_NOT_EXIST.send(player);
                    return false;
                }
                target = instance.getProfile(targetUUID);
                if (!profile.getNationId().equals(target.getNationId()))  {
                    Lang.PLAYER_IS_NOT_IN_SAME_NATION_AS_YOU.send(player);
                    return false;
                }
                profile.setNationRank("admin");
                target.setNationRank("leader");
                profile.save();
                target.save();
                nation = profile.getCurrentNation();
                nation.setLeader(target.getUniqueId());
                nation.save();
                Lang.PLAYER_TRANSFERED_LEADERSHIP.broadcast("%PLAYER%;" + player.getName(), "%NATION%;" + nation.getName(), "%TARGET%;" + target.getAsOfflinePlayer().getName());
                break;
            case 2:
                nation = Nation.getByName(args[1]);
                boolean allowed = false;
                if (!(sender instanceof Player)) allowed = true;
                if (sender instanceof Player && instance.getUtilLists().staffMode.contains(((Player) sender).getUniqueId())) allowed = true;
                if (sender instanceof Player && Permission.hasForeignPermission(instance.getProfile(((Player) sender).getUniqueId()), Permission.CHANGE_LEADERSHIP, nation)) allowed = true;
                if (!allowed) {
                    Lang.NO_PERMISSIONS.send(sender);
                    return false;
                }
                targetUUID = Bukkit.getPlayerUniqueId(args[0]);
                if (targetUUID == null) {
                    Lang.PLAYER_DOES_NOT_EXIST.send(sender);
                    return false;
                }
                target = instance.getProfile(targetUUID);
                if (nation == null) {
                    Lang.NATION_DOESNT_EXIST.send(sender);
                    return false;
                }
                if (target.isInNation() && !target.getNationId().equals(nation.getNationId())) {
                    Nation oldNation = target.getCurrentNation();
                    oldNation.setLeader("NONE");
                    oldNation.getMembers().remove(target.getUniqueId());
                    oldNation.save();
                }
                if (!nation.getLeader().equalsIgnoreCase("NONE")) {
                    Profile oldLeader = instance.getProfile(UUID.fromString(nation.getLeader()));
                    oldLeader.setNationRank("admin");
                    oldLeader.save();
                }
                target.setNationId(nation.getNationId());
                target.setInNation(true);
                target.setNationRank("leader");
                nation.setLeader(target.getUniqueId());
                nation.save();
                target.save();
                Lang.PLAYER_TRANSFERED_LEADERSHIP.broadcast("%PLAYER%;" + sender.getName(), "%NATION%;" + nation.getName(), "%TARGET%;" + target.getAsOfflinePlayer().getName());
                break;
        }
        return false;
    }

}
