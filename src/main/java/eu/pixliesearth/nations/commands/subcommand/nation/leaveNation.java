package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class leaveNation implements SubCommand {

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
    public boolean execute(CommandSender sender, String[] args) {
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
        if (profile.getNationRank().equals("LEADER")) {
            Lang.LEADER_CANT_LEAVE_NATION.send(player);
            return false;
        }
        final String nation = profile.getCurrentNation().getName();
        profile.leaveNation();
        player.sendMessage(Lang.YOU_LEFT_NATION.get(player).replace("%NATION%", nation));
        return false;
    }

}
