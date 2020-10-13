package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.entities.nation.NationFlag;
import eu.pixliesearth.nations.entities.nation.ranks.Rank;
import eu.pixliesearth.nations.managers.NationManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class joinNation extends SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"join"};
    }

    @Override
    public Map<String, Integer> autoCompletion() {
        Map<String, Integer> returner = new HashMap<>();
        for (Map.Entry<String, String> entry : NationManager.names.entrySet())
            returner.put(entry.getKey(), 1);
        return returner;
    }

    @Override
    public boolean staff() {
        return false;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, String[] args) {
        Nation nation = Nation.getByName(args[0]);
        if (nation == null) {
            sender.sendMessage(Lang.NATION_DOESNT_EXIST.get(sender));
            return false;
        }
        switch (args.length) {
            case 1:
                Player player = (Player) sender;
                Profile profile = instance.getProfile(player.getUniqueId());
                if (profile.isInNation()) {
                    player.sendMessage(Lang.YOU_ARE_ALREADY_IN_NATION.get(player));
                    return false;
                }
                if (!profile.getInvites().contains(nation.getNationId()) && !profile.isStaff() && !nation.getFlags().contains(NationFlag.OPEN.name())) {
                    player.sendMessage(Lang.YOU_DONT_HAVE_OPEN_INV.get(player));
                    return false;
                }
                profile.getInvites().remove(nation.getNationId());
                profile.addToNation(nation.getNationId(), Rank.get(nation.getRanks().get("newbie")));
                for (Player np : nation.getOnlineMemberSet())
                    np.sendMessage(Lang.PLAYER_JOINED_NATION.get(np).replace("%PLAYER%", player.getName()));
                break;
            case 2:
                if (sender instanceof Player && !instance.getUtilLists().staffMode.contains(((Player) sender).getUniqueId())) {
                    sender.sendMessage(Lang.NO_PERMISSIONS.get(sender));
                    return false;
                }
                UUID targetUUID = Bukkit.getPlayerUniqueId(args[1]);
                if (targetUUID == null) return false;
                Profile target = instance.getProfile(targetUUID);
                if (target.isInNation()) {
                    Nation oldNation = target.getCurrentNation();
                    oldNation.getMembers().remove(target.getUniqueId());
                    oldNation.save();
                }
                target.getInvites().remove(nation.getNationId());
                target.addToNation(nation.getNationId(), Rank.get(nation.getRanks().get("newbie")));
                for (Player np : nation.getOnlineMemberSet())
                    np.sendMessage(Lang.PLAYER_JOINED_NATION.get(np).replace("%PLAYER%", target.getAsOfflinePlayer().getName()));
                break;
            case 3:
                if (nation.getRanks().get(args[2]) == null) {
                    Lang.RANK_DOESNT_EXIST.send(sender);
                    return false;
                }
                if (sender instanceof Player && !instance.getUtilLists().staffMode.contains(((Player) sender).getUniqueId())) {
                    sender.sendMessage(Lang.NO_PERMISSIONS.get(sender));
                    return false;
                }
                UUID targetUUID2 = Bukkit.getPlayerUniqueId(args[1]);
                Profile target2 = instance.getProfile(targetUUID2);
                target2.getInvites().remove(nation.getNationId());
                target2.addToNation(nation.getNationId(), Rank.get(nation.getRanks().get(args[2])));
                for (Player np : nation.getOnlineMemberSet())
                    np.sendMessage(Lang.PLAYER_JOINED_NATION.get(np).replace("%PLAYER%", target2.getAsOfflinePlayer().getName()));
                break;
        }
        return false;
    }

}
