package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.entities.nation.ranks.Rank;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class promoteNation extends SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"promote"};
    }

    @Override
    public Map<String, Integer> autoCompletion(CommandSender sender, String[] args) {
        Map<String, Integer> map = new HashMap<>();
        Bukkit.getOnlinePlayers().forEach(p -> map.put(p.getName(), 1));
        return map;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {
        OfflinePlayer target = Bukkit.getOfflinePlayerIfCached(args[0]);
        if (target == null) {
            Lang.PLAYER_DOES_NOT_EXIST.send(sender);
            return false;
        }
        Profile targetProfile = instance.getProfile(target.getUniqueId());
        if (!targetProfile.isInNation()) {
            Lang.PLAYER_NOT_IN_NATION.send(sender);
            return false;
        }
        Nation nation = targetProfile.getCurrentNation();
        int max;
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Profile profile = instance.getProfile(player.getUniqueId());
            if (!profile.isStaff() && profile.getNationId().equals(targetProfile.getNationId()) && profile.getCurrentNationRank().getPriority() < targetProfile.getCurrentNationRank().getPriority()) {
                Lang.NO_PERMISSIONS.send(sender);
                return false;
            }
            max = (int) Math.min(profile.isStaff() ? 665 : profile.getCurrentNationRank().getPriority(), 665);
        } else {
            max = 665;
        }
        for (int i = (int) (targetProfile.getCurrentNationRank().getPriority() + 1); i <= max; i++) {
            Rank rank = nation.getRankByPriority(i);
            if (rank != null) {
                targetProfile.setNationRank(rank.getName());
                targetProfile.save();
                Lang.CHANGED_PLAYERS_NATION_RANK.send(sender, "%RANK%;" + rank.getName(), "%PLAYER%;" + target.getName());
                return false;
            }
        }
        Lang.PLAYER_IS_ALREADY_ON_THE_HIGHEST_RANK.send(sender);
        return false;
    }
}
