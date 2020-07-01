package eu.pixliesearth.nations.commands.subcommand.nation;

import com.google.common.collect.RowSortedTable;
import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.events.TerritoryChangeEvent;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.entities.chunk.NationChunk;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.entities.nation.ranks.Permission;
import eu.pixliesearth.nations.managers.NationManager;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

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
    public Map<String, Integer> autoCompletion() {
        Map<String, Integer> returner = new HashMap<>();
        returner.put("auto", 1);
        returner.put("one", 1);
        returner.put("fill", 1);
        for (Map.Entry<String, String> entry : NationManager.names.entrySet())
            returner.put(entry.getKey(), 2);
        return returner;
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
        switch (args.length) {
            case 1:
                if (!profile.isInNation()) {
                    player.sendMessage(Lang.NOT_IN_A_NATION.get(sender));
                    return false;
                }
                if (!Permission.hasNationPermission(profile, Permission.CLAIM)) {
                    Lang.NO_PERMISSIONS.send(sender);
                    return false;
                }
                if (args[0].equalsIgnoreCase("one")) {
                    NationChunk.claim(player, player.getWorld().getName(), player.getChunk().getX(), player.getChunk().getZ(), TerritoryChangeEvent.ChangeType.CLAIM_ONE_SELF, profile.getNationId());
                } else if (args[0].equalsIgnoreCase("auto")) {
                    if (instance.getUtilLists().claimAuto.containsKey(player.getUniqueId())) {
                        instance.getUtilLists().claimAuto.remove(player.getUniqueId());
                        player.sendMessage(Lang.AUTOCLAIM_DISABLED.get(player));
                    } else {
                        instance.getUtilLists().claimAuto.put(player.getUniqueId(), profile.getNationId());
                        player.sendMessage(Lang.AUTOCLAIM_ENABLED.get(player));
                    }
                } else if (args[0].equalsIgnoreCase("fill")) {
                    long start = System.currentTimeMillis();
                    instance.getUtilLists().claimFill.add(player.getUniqueId());
                    Table<Integer, Integer, NationChunk> toClaim = TreeBasedTable.create();
                    floodSearch(player, profile.getCurrentNation(), c.getX(), c.getZ(), c.getWorld().getName(), toClaim);
                    claimFill(player, profile.getCurrentNation(), toClaim);
                    player.sendMessage(System.currentTimeMillis() - start + "ms");
                } else if (args[0].contains(";")) {
                    int x = Integer.parseInt(args[0].split(";")[0]);
                    int z = Integer.parseInt(args[0].split(";")[1]);
                    String world = player.getWorld().getName();
                    NationChunk.claim(player, world, x, z, TerritoryChangeEvent.ChangeType.CLAIM_ONE_SELF, profile.getNationId());
                }
                break;
            case 2:
                Nation nation = Nation.getByName(args[1]);
                if (args[0].equalsIgnoreCase("one")) {
                    if (!instance.getUtilLists().staffMode.contains(player.getUniqueId())) {
                        Lang.NO_PERMISSIONS.send(player);
                        return false;
                    }
                    if (nation == null) {
                        Lang.NATION_DOESNT_EXIST.send(player);
                        return false;
                    }
                    NationChunk.claim(player, player.getWorld().getName(), player.getChunk().getX(), player.getChunk().getZ(), TerritoryChangeEvent.ChangeType.CLAIM_ONE_OTHER, nation.getNationId());
                } else if (args[0].equalsIgnoreCase("auto")) {
                    if (!instance.getUtilLists().staffMode.contains(player.getUniqueId())) {
                        Lang.NO_PERMISSIONS.send(player);
                        return false;
                    }
                    if (nation == null) {
                        Lang.NATION_DOESNT_EXIST.send(player);
                        return false;
                    }
                    if (instance.getUtilLists().claimAuto.containsKey(player.getUniqueId())) {
                        instance.getUtilLists().claimAuto.remove(player.getUniqueId());
                        player.sendMessage(Lang.AUTOCLAIM_DISABLED.get(player));
                    } else {
                        instance.getUtilLists().claimAuto.put(player.getUniqueId(), nation.getNationId());
                        player.sendMessage(Lang.AUTOCLAIM_ENABLED.get(player));
                    }
                } else if (args[0].equalsIgnoreCase("fill")) {
                    instance.getUtilLists().claimFill.add(player.getUniqueId());
                    Table<Integer, Integer, NationChunk> toClaim = TreeBasedTable.create();
                    floodSearch(player, nation, c.getX(), c.getZ(), c.getWorld().getName(), toClaim);
                    claimFill(player, nation, toClaim);
                }
                break;
        }

        return false;
    }

    private void floodSearch(Player player, Nation nation, int x, int z, String world, Table<Integer, Integer, NationChunk> toClaim) {

        if (!instance.getUtilLists().claimFill.contains(player.getUniqueId())) return;

        if (toClaim.contains(x, z)) return;

        if (NationChunk.get(world, x, z) != null) return;

        //TODO
        int claimFillLimit = 500;
        if ((toClaim.size() > nation.getClaimingPower() || toClaim.size() > claimFillLimit) && !instance.getUtilLists().staffMode.contains(player.getUniqueId())) {
            instance.getUtilLists().claimFill.remove(player.getUniqueId());
            toClaim.clear();
            Lang.CLAIMFILL_LIMIT_REACHED.send(player);
            return;
        }

        toClaim.put(x, z, new NationChunk(nation.getNationId(), world, x, z));

        floodSearch(player, nation, x + 1, z, world, toClaim);
        floodSearch(player, nation, x - 1, z, world, toClaim);
        floodSearch(player, nation, x, z + 1, world, toClaim);
        floodSearch(player, nation, x, z - 1, world, toClaim);
    }

    private void claimFill(Player claimer, Nation nation, Table<Integer, Integer, NationChunk> toClaim) {
        if (toClaim.size() > 0) {
            int claimed = 0;
            for (NationChunk chunk : toClaim.values()) {
                chunk.claim();
                claimed++;
            }
            for (Player member : nation.getOnlineMemberSet())
                Lang.PLAYER_CLAIMFILLED.send(member, "%CHUNKS%;" + claimed, "%PLAYER%;" + claimer.getName());
            Lang.PLAYER_CLAIMFILLED.send(Bukkit.getConsoleSender(), "%CHUNKS%;" + claimed, "%PLAYER%;" + claimer.getName());
        }
    }

}
