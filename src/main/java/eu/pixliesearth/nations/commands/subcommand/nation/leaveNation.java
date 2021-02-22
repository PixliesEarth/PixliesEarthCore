package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.entities.nation.NationFlag;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class leaveNation extends SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"leave", "quit"};
    }

    @Override
    public Map<String, Integer> autoCompletion() {
        return new HashMap<>();
    }

    @Override
    public boolean staff() {
        return false;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, String[] args) {
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
        if (profile.isLeader()) {
            final Nation nation = profile.getCurrentNation();
            if (nation.getFlags().contains(NationFlag.PERMANENT.name())) {
                nation.setLeader("NONE");
                nation.save();
                profile.leaveNation();
                player.sendMessage(Lang.YOU_LEFT_NATION.get(player).replace("%NATION%", nation.getName()));
                return false;
            }
            Lang.LEADER_CANT_LEAVE_NATION.send(player);
            return false;
        }
        final String nation = profile.getCurrentNation().getName();
        profile.leaveNation();
        player.sendMessage(Lang.YOU_LEFT_NATION.get(player).replace("%NATION%", nation));
        return false;
    }

}
