package eu.pixliesearth.nations.commands.subcommand.nation;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
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
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class unclaimNation extends SubCommand {

    @Override
    public String[] aliases() {
        return new String[]{"unclaim"};
    }

    @Override
    public Map<String, Integer> autoCompletion() {
        Map<String, Integer> returner = new HashMap<>();
        returner.put("auto", 1);
        returner.put("one", 1);
        returner.put("fill", 1);
        returner.put("here", 1);
        return returner;
    }

    @Override
    public boolean staff() {
        return false;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, String[] args) {
        if (!checkIfPlayer(sender)) return false;
        Player player = (Player) sender;
        Profile profile = instance.getProfile(player.getUniqueId());
        Chunk c = player.getLocation().getChunk();
        NationChunk nc = NationChunk.get(c);
        if (nc == null) {
            player.sendMessage(Lang.NOT_CLAIMED.get(player));
            return false;
        }
        switch (args.length) {
            case 1:
                if (!profile.isInNation() && !instance.getUtilLists().staffMode.contains(player.getUniqueId())) {
                    player.sendMessage(Lang.NOT_IN_A_NATION.get(sender));
                    return false;
                }
                if (args[0].equalsIgnoreCase("auto")) {
                    if (instance.getUtilLists().unclaimAuto.contains(player.getUniqueId())) {
                        instance.getUtilLists().unclaimAuto.remove(player.getUniqueId());
                        player.sendMessage(Lang.AUTOUNCLAIM_DISABLED.get(player));
                    } else {
                        instance.getUtilLists().unclaimAuto.add(player.getUniqueId());
                        player.sendMessage(Lang.AUTOUNCLAIM_ENABLED.get(player));
                    }
                }
                if (!args[0].equalsIgnoreCase("auto") && !Permission.hasNationPermission(profile, Permission.UNCLAIM) && !instance.getUtilLists().staffMode.contains(player.getUniqueId())) {
                    Lang.NO_PERMISSIONS.send(sender);
                    return false;
                }
                if (args[0].equalsIgnoreCase("one") || args[0].equalsIgnoreCase("here")) {
                    NationChunk.unclaim(player, c.getWorld().getName(), c.getX(), c.getZ(), TerritoryChangeEvent.ChangeType.UNCLAIM_ONE_SELF);
                } else if (args[0].equalsIgnoreCase("fill")) {
                    long start = System.currentTimeMillis();
                    instance.getUtilLists().unclaimFill.add(player.getUniqueId());
                    Table<Integer, Integer, NationChunk> toClaim = HashBasedTable.create();
                    floodSearch(player, profile.getCurrentNation(), c.getX(), c.getZ(), c.getWorld().getName(), toClaim);
                    unclaimFill(player, profile.getCurrentNation(), toClaim);
                    player.sendMessage(System.currentTimeMillis() - start + "ms");
                } else if (args[0].equalsIgnoreCase("all")) {
                    Nation nation = profile.getCurrentNation();
                    final int chunks = nation.getChunks().size();
                    nation.unclaimAll();
                    for (Player p : nation.getOnlineMemberSet())
                        p.sendMessage(Lang.PLAYER_UNCLAIMFILLED.get(p).replace("%CHUNKS%", chunks + "").replace("Claim-fill", "Claim-all"));
                }
                break;
            case 2:
                if (args[0].equalsIgnoreCase("fill")) {
                    Nation nation = Nation.getByName(args[1]);
                    instance.getUtilLists().unclaimFill.add(player.getUniqueId());
                    Table<Integer, Integer, NationChunk> toClaim = HashBasedTable.create();
                    floodSearch(player, nation, c.getX(), c.getZ(), c.getWorld().getName(), toClaim);
                    unclaimFill(player, nation, toClaim);
                }
                break;
        }
        return false;
    }

    private void floodSearch(Player player, Nation nation, int x, int z, String world, Table<Integer, Integer, NationChunk> toUnclaim) {

        if (!instance.getUtilLists().unclaimFill.contains(player.getUniqueId())) return;

        if (toUnclaim.contains(x, z)) return;

        NationChunk nc = NationChunk.get(world, x, z);

        if (nc == null) return;

        if (!nc.getNationId().equals(nation.getNationId())) return;

        int claimFillLimit = 500;
        if (toUnclaim.size() > claimFillLimit && !instance.getUtilLists().staffMode.contains(player.getUniqueId())) {
            instance.getUtilLists().claimFill.remove(player.getUniqueId());
            toUnclaim.clear();
            Lang.UNCLAIMFILL_LIMIT_REACHED.send(player);
            return;
        }

        toUnclaim.put(x, z, nc);

        floodSearch(player, nation, x + 1, z, world, toUnclaim);
        floodSearch(player, nation, x - 1, z, world, toUnclaim);
        floodSearch(player, nation, x, z + 1, world, toUnclaim);
        floodSearch(player, nation, x, z - 1, world, toUnclaim);
    }

    private void unclaimFill(Player claimer, Nation nation, Table<Integer, Integer, NationChunk> toClaim) {
        if (toClaim.size() > 0) {
            int unclaimed = 0;
            for (NationChunk chunk : toClaim.values()) {
                chunk.unclaim();
                unclaimed++;
            }
            for (Player member : nation.getOnlineMemberSet())
                Lang.PLAYER_UNCLAIMFILLED.send(member, "%CHUNKS%;" + unclaimed, "%PLAYER%;" + claimer.getName());
            Lang.PLAYER_UNCLAIMFILLED.send(Bukkit.getConsoleSender(), "%CHUNKS%;" + unclaimed, "%PLAYER%;" + claimer.getName());
        }
    }

}
