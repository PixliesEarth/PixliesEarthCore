package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.chunk.NationChunk;
import org.bukkit.Chunk;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class unclaimNation implements SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"unclaim"};
    }

    @Override
    public String[] autocompletion() {
        return new String[]{"one", "auto"};
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
        if (!profile.isInNation() && !instance.getUtilLists().staffMode.contains(player.getUniqueId())) {
            player.sendMessage(Lang.NOT_IN_A_NATION.get(sender));
            return false;
        }
        Chunk c = player.getLocation().getChunk();
        NationChunk nationChunk = NationChunk.get(c);
        if (nationChunk == null) {
            player.sendMessage(Lang.NOT_CLAIMED.get(player));
            return false;
        }
        if (!nationChunk.getNationId().equals(profile.getNationId()) && !instance.getUtilLists().staffMode.contains(player.getUniqueId())) {
            player.sendMessage();
            return false;
        }
        //TODO PERMISSION SYSTEM
        if (args[0].equalsIgnoreCase("one")) {
            NationChunk nc = new NationChunk(profile.getNationId(), c.getWorld().getName(), c.getX(), c.getZ());
            nc.unclaim();
            for (Player members : nationChunk.getCurrentNation().getOnlineMemberSet())
                members.sendMessage(Lang.PLAYER_UNCLAIMED.get(members).replace("%PLAYER%", player.getDisplayName()).replace("%X%", c.getX()+"").replace("%Z%", c.getZ()+""));
            System.out.println("§bChunk unclaimed at §e" + nc.getX() + "§8, §e" + nc.getZ());
        } else if (args[0].equalsIgnoreCase("auto")) {
            if (instance.getUtilLists().unclaimAuto.contains(player.getUniqueId())) {
                instance.getUtilLists().unclaimAuto.remove(player.getUniqueId());
                player.sendMessage(Lang.AUTOUNCLAIM_DISABLED.get(player));
            } else {
                instance.getUtilLists().unclaimAuto.add(player.getUniqueId());
                player.sendMessage(Lang.AUTOUNCLAIM_ENABLED.get(player));
            }
        }
        return false;
    }

}
