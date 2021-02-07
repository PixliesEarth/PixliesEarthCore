package eu.pixliesearth.nations.commands.subcommand.nation;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.events.TerritoryChangeEvent;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.chunk.NationChunk;
import eu.pixliesearth.nations.entities.chunk.NationChunkType;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.entities.nation.ranks.Permission;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;

public class capitalNation extends SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"capital", "setcapital"};
    }

    @Override
    public Map<String, Integer> autoCompletion(CommandSender sender, String[] args) {
        return Collections.emptyMap();
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, String[] args) {
        Player player = (Player) sender;
        Profile profile = instance.getProfile(player.getUniqueId());
        if (!profile.isInNation()) {
            Lang.NOT_IN_A_NATION.send(sender);
            return false;
        }
        if (!Permission.CHANGE_CAPITAL.hasPermission(sender)) {
            Lang.NO_PERMISSIONS.send(sender);
            return false;
        }
        Nation nation = profile.getCurrentNation();
        NationChunk chunk = NationChunk.get(player.getLocation().getChunk());
        if (chunk == null || !chunk.getNationId().equals(nation.getNationId())) {
            Lang.NOT_CLAIMED.send(sender);
            return false;
        }
        chunk.unclaim();
        NationChunk.claim(player, chunk.getWorld(), chunk.getX(), chunk.getZ(), TerritoryChangeEvent.ChangeType.CLAIM_ONE_SELF, nation.getNationId(), NationChunkType.CAPITAL);
        nation.setCapital(player.getLocation());
        nation.save();
        nation.broadcastMembers(Lang.PLAYER_SET_CAPITAL, "%PLAYER%;" + player.getName(), "%X%;" + player.getLocation().getBlock().getX(), "%Z%;" + player.getLocation().getBlock().getZ());
        return true;
    }

}
