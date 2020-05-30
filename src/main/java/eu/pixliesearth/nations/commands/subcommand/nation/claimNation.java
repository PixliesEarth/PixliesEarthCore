package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.chunk.ChunkBank;
import eu.pixliesearth.nations.entities.chunk.NationChunk;
import eu.pixliesearth.nations.entities.nation.Nation;
import org.bukkit.Chunk;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class claimNation implements SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"claim"};
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
        if (!profile.isInNation()) {
            player.sendMessage(Lang.NOT_IN_A_NATION.get(sender));
            return false;
        }
        Chunk c = player.getLocation().getChunk();
        if (ChunkBank.table.get(c.getX(), c.getZ()) != null) {
            player.sendMessage(Lang.ALREADY_CLAIMED.get(player));
            return false;
        }
        //TODO PERMISSION SYSTEM
        if (args[0].equalsIgnoreCase("one")) {
            NationChunk nc = new NationChunk(profile.getNationId(), c.getX(), c.getZ());
            nc.claim();
            for (Player members : profile.getCurrentNation().getOnlineMemberSet())
                members.sendMessage(Lang.PLAYER_CLAIMED.get(members).replace("%PLAYER%", player.getDisplayName()).replace("%X%", c.getX()+"").replace("%Z%", c.getZ()+""));
        }

        return false;
    }

}
