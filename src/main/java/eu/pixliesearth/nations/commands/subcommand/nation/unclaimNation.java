package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.events.TerritoryChangeEvent;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.chunk.NationChunk;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.entities.nation.ranks.Permission;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class unclaimNation implements SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"unclaim"};
    }

    @Override
    public Map<String, Integer> autoCompletion() {
        Map<String, Integer> returner = new HashMap<>();
        returner.put("auto", 1);
        returner.put("one", 1);
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
        Chunk c = player.getLocation().getChunk();
        NationChunk nc = NationChunk.get(c);
        if (nc == null) {
            player.sendMessage(Lang.NOT_CLAIMED.get(player));
            return false;
        }
        if (args.length == 1) {
            if (!profile.isInNation() && !instance.getUtilLists().staffMode.contains(player.getUniqueId())) {
                player.sendMessage(Lang.NOT_IN_A_NATION.get(sender));
                return false;
            }
            if (!Permission.hasNationPermission(profile, Permission.UNCLAIM) && !instance.getUtilLists().staffMode.contains(player.getUniqueId())) {
                Lang.NO_PERMISSIONS.send(sender);
                return false;
            }
            if (args[0].equalsIgnoreCase("one")) {
                NationChunk.unclaim(player, c.getWorld().getName(), c.getX(), c.getZ(), TerritoryChangeEvent.ChangeType.UNCLAIM_ONE_SELF);
            } else if (args[0].equalsIgnoreCase("auto")) {
                if (instance.getUtilLists().unclaimAuto.containsKey(player.getUniqueId())) {
                    instance.getUtilLists().unclaimAuto.remove(player.getUniqueId());
                    player.sendMessage(Lang.AUTOUNCLAIM_DISABLED.get(player));
                } else {
                    instance.getUtilLists().unclaimAuto.put(player.getUniqueId(), nc.getNationId());
                    player.sendMessage(Lang.AUTOUNCLAIM_ENABLED.get(player));
                }
            } else if (args[0].equalsIgnoreCase("fill")) {

            }
        }
        return false;
    }

}
