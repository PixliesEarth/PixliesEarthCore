package eu.pixliesearth.nations.commands.subcommand.nation;

import com.google.gson.Gson;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.events.TerritoryChangeEvent;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.chunk.NationChunk;
import eu.pixliesearth.nations.entities.chunk.NationChunkType;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.entities.nation.ranks.Permission;
import eu.pixliesearth.nations.entities.settlements.Settlement;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class capitalNation extends SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"capital", "setcapital"};
    }

    @Override
    public Map<String, Integer> autoCompletion(CommandSender sender, String[] args) {
        Map<String, Integer> map = new HashMap<>();
        Player player = (Player) sender;
        Profile profile = instance.getProfile(player.getUniqueId());
        if (!profile.isInNation()) return map;
        for (String s : profile.getCurrentNation().getSettlements().keySet())
            map.put(s, 1);
        return map;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, String[] args) {
        if (args.length == 0) {
            Lang.WRONG_USAGE_NATIONS.send(sender, "%USAGE%;/n capital <settlement>");
            return false;
        }
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
        if (!nation.getSettlements().containsKey(args[0])) {
            Lang.SETTLEMENT_DOESNT_EXIST.send(sender);
            return false;
        }
        Settlement settlement = new Gson().fromJson(nation.getSettlements().get(args[0]), Settlement.class);
        NationChunk chunk = NationChunk.get(settlement.getAsBukkitLocation().getChunk());
        if (chunk == null || !chunk.getNationId().equals(nation.getNationId())) {
            Lang.NOT_CLAIMED.send(sender);
            return false;
        }
        Settlement currentCapital = nation.getCapital();
        if (currentCapital != null) {
            currentCapital.setCapital(false);
            nation.getSettlements().put(currentCapital.getName(), new Gson().toJson(currentCapital));
            NationChunk nc = NationChunk.get(currentCapital.getAsBukkitLocation().getChunk());
            nc.unclaim();
            nc.setType(NationChunkType.NORMAL);
            nc.claim();
        }
        chunk.unclaim();
        chunk.setType(NationChunkType.CAPITAL);
        chunk.claim();
        settlement.setCapital(true);
        nation.getSettlements().put(settlement.getName(), new Gson().toJson(settlement));
        nation.save();
        nation.broadcastMembers(Lang.PLAYER_SET_CAPITAL, "%PLAYER%;" + player.getName(), "%X%;" + player.getLocation().getBlock().getX(), "%Z%;" + player.getLocation().getBlock().getZ());
        return true;
    }

}
