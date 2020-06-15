package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.events.TerritoryChangeEvent;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.chunk.NationChunk;
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
        if (NationChunk.get(c) == null) {
            player.sendMessage(Lang.NOT_CLAIMED.get(player));
            return false;
        }
        if (args.length == 1) {
            if (!profile.isInNation()) {
                player.sendMessage(Lang.NOT_IN_A_NATION.get(sender));
                return false;
            }
            //TODO PERMISSION SYSTEM
            if (args[0].equalsIgnoreCase("one")) {
                NationChunk nc = NationChunk.get(c);
                boolean allowed = false;
                if (instance.getUtilLists().staffMode.contains(player.getUniqueId())) allowed = true;
                if (profile.getNationId().equals(nc.getNationId())) allowed = true;
                if (!allowed) {
                    Lang.CHUNK_NOT_YOURS.send(player);
                    return false;
                }
                TerritoryChangeEvent event = new TerritoryChangeEvent(player, nc, TerritoryChangeEvent.ChangeType.UNCLAIM_ONE_SELF);
                Bukkit.getPluginManager().callEvent(event);
                if (!event.isCancelled()) {
                    nc.unclaim();
                    for (Player members : profile.getCurrentNation().getOnlineMemberSet())
                        members.sendMessage(Lang.PLAYER_UNCLAIMED.get(members).replace("%PLAYER%", player.getDisplayName()).replace("%X%", c.getX() + "").replace("%Z%", c.getZ() + ""));
                    System.out.println("§bChunk unclaimed at §e" + nc.getX() + "§8, §e" + nc.getZ());
                }
            } else if (args[0].equalsIgnoreCase("auto")) {
                if (instance.getUtilLists().unclaimAuto.containsKey(player.getUniqueId())) {
                    instance.getUtilLists().unclaimAuto.remove(player.getUniqueId());
                    player.sendMessage(Lang.AUTOUNCLAIM_DISABLED.get(player));
                } else {
                    instance.getUtilLists().unclaimAuto.put(player.getUniqueId(), profile.getNationId());
                    player.sendMessage(Lang.AUTOUNCLAIM_ENABLED.get(player));
                }
            }
        }
        return false;
    }

}
