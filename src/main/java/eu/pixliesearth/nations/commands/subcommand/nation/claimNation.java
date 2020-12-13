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
import eu.pixliesearth.nations.managers.NationManager;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class claimNation extends SubCommand {

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
        returner.put("here", 1);
        returner.put("fill", 1);
        returner.put("all", 1);
        for (Map.Entry<String, String> entry : NationManager.names.entrySet())
            returner.put(entry.getKey(), 2);
        return returner;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, String[] args) {
        if (!checkIfPlayer(sender)) return false;
        Player player = (Player) sender;
        Profile profile = instance.getProfile(player.getUniqueId());
        Chunk c = player.getLocation().getChunk();
        Nation nation;
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
                if (args[0].equalsIgnoreCase("one") || args[0].equalsIgnoreCase("here")) {
                    NationChunk.claim(player, player.getWorld().getName(), player.getLocation().getChunk().getX(), player.getLocation().getChunk().getZ(), TerritoryChangeEvent.ChangeType.CLAIM_ONE_SELF, profile.getNationId());
                } else if (args[0].equalsIgnoreCase("auto")) {
                    if (instance.getUtilLists().claimAuto.containsKey(player.getUniqueId())) {
                        instance.getUtilLists().claimAuto.remove(player.getUniqueId());
                        player.sendMessage(Lang.AUTOCLAIM_DISABLED.get(player));
                    } else {
                        instance.getUtilLists().claimAuto.put(player.getUniqueId(), profile.getNationId());
                        player.sendMessage(Lang.AUTOCLAIM_ENABLED.get(player));
                    }
                } else if (args[0].equalsIgnoreCase("fill")) {
                    instance.getUtilLists().claimFill.add(player.getUniqueId());
                    Table<Integer, Integer, NationChunk> toClaim = HashBasedTable.create();
                    floodSearch(player, profile.getCurrentNation(), c.getX(), c.getZ(), c.getWorld().getName(), toClaim);
                    TerritoryChangeEvent event = new TerritoryChangeEvent(player, toClaim.values(), TerritoryChangeEvent.ChangeType.CLAIM_FILL_SELF);
                    Bukkit.getPluginManager().callEvent(event);
                    if (!event.isCancelled()) {
                        claimFill(player, profile.getCurrentNation(), toClaim);
                    }
                } else if (args[0].contains(";")) {
                    int x = Integer.parseInt(args[0].split(";")[0]);
                    int z = Integer.parseInt(args[0].split(";")[1]);
                    String world = player.getWorld().getName();
                    NationChunk.claim(player, world, x, z, TerritoryChangeEvent.ChangeType.CLAIM_ONE_SELF, profile.getNationId());
                }
                break;
            case 2:
                nation = Nation.getByName(args[1]);
                if (nation == null) {
                    Lang.NATION_DOESNT_EXIST.send(player);
                    return false;
                }
                if (args[0].equalsIgnoreCase("one") || args[0].equalsIgnoreCase("here")) {
                    if (!instance.getUtilLists().staffMode.contains(player.getUniqueId()) && !Permission.hasForeignPermission(profile, Permission.CLAIM, nation)) {
                        Lang.NO_PERMISSIONS.send(player);
                        return false;
                    }
                    NationChunk.claim(player, player.getWorld().getName(), player.getLocation().getChunk().getX(), player.getLocation().getChunk().getZ(), TerritoryChangeEvent.ChangeType.CLAIM_ONE_OTHER, nation.getNationId());
                } else if (args[0].equalsIgnoreCase("auto")) {
                    if (!instance.getUtilLists().staffMode.contains(player.getUniqueId()) && !Permission.hasForeignPermission(profile, Permission.CLAIM, nation)) {
                        Lang.NO_PERMISSIONS.send(player);
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
                    if (!instance.getUtilLists().staffMode.contains(player.getUniqueId()) && !Permission.hasForeignPermission(profile, Permission.CLAIM, nation)) {
                        Lang.NO_PERMISSIONS.send(player);
                        return false;
                    }
                    instance.getUtilLists().claimFill.add(player.getUniqueId());
                    Table<Integer, Integer, NationChunk> toClaim = HashBasedTable.create();
                    floodSearch(player, nation, c.getX(), c.getZ(), c.getWorld().getName(), toClaim);
                    TerritoryChangeEvent event = new TerritoryChangeEvent(player, toClaim.values(), TerritoryChangeEvent.ChangeType.CLAIM_FILL_OTHER);
                    Bukkit.getPluginManager().callEvent(event);
                    if (!event.isCancelled()) {
                        claimFill(player, nation, toClaim);
                    }
                } /*else if (args[0].equalsIgnoreCase("line")) {
                    long start = System.currentTimeMillis();
                    if (!profile.isInNation()) {
                        Lang.NOT_IN_A_NATION.send(player);
                        return false;
                    }
                    if (!instance.getUtilLists().staffMode.contains(player.getUniqueId()) && !Permission.hasForeignPermission(profile, Permission.CLAIM, profile.getCurrentNation())) {
                        Lang.NO_PERMISSIONS.send(player);
                        return false;
                    }
                    int max = Integer.parseInt(args[1]);
                    if (max > 10) {
                        Lang.CLAIMFILL_LIMIT_REACHED.send(player, "Claim-fill;Claim-line");
                        return false;
                    }
                    int claimed = 0;
                    final String world = c.getWorld().getName();
                    final String nationId = profile.getNationId();
                    final int x = c.getX();
                    final int z = c.getZ();
                    NationChunk nc = NationChunk.get(c);
                    if (nc != null) {
                        Lang.ALREADY_CLAIMED.send(player);
                        return false;
                    }
                    nc = new NationChunk(nationId, world, x, z);
                    for (int i = 0; i < max; i++) {
                        if (NationChunk.get(world, x, z + i) != null) continue;
                        NationChunk ncn = nc.withChunkZNew(nc.getZ() - i);
                        ncn.claim();
                        claimed++;
                    }
                    for (Player member : profile.getCurrentNation().getOnlineMemberSet())
                        Lang.PLAYER_CLAIMLINED.send(member, "%CHUNKS%;" + claimed, "%PLAYER%;" + player.getName());
                    Lang.PLAYER_CLAIMLINED.send(Bukkit.getConsoleSender(), "%CHUNKS%;" + claimed, "%PLAYER%;" + player.getName());
                    player.sendMessage(System.currentTimeMillis() - start + "ms");
                }*/
                break;
            case 3:
/*                if (args[0].equalsIgnoreCase("line")) {
                    long start = System.currentTimeMillis();
                    if (!profile.isStaff()) {
                        Lang.NO_PERMISSIONS.send(player);
                        return false;
                    }
                    nation = Nation.getByName(args[2]);
                    if (nation == null) {
                        Lang.NATION_DOESNT_EXIST.send(player);
                        return false;
                    }
                    if (!instance.getUtilLists().staffMode.contains(player.getUniqueId()) && !Permission.hasForeignPermission(profile, Permission.CLAIM, nation)) {
                        Lang.NO_PERMISSIONS.send(player);
                        return false;
                    }
                    int max = Integer.parseInt(args[1]);
                    if (max > 10) {
                        Lang.CLAIMFILL_LIMIT_REACHED.send(player, "Claim-fill;Claim-line");
                        return false;
                    }
                    int claimed = 0;
                    final String world = c.getWorld().getName();
                    final String nationId = nation.getNationId();
                    final int x = c.getX();
                    final int z = c.getZ();
                    NationChunk nc = NationChunk.get(c);
                    if (nc != null) {
                        Lang.ALREADY_CLAIMED.send(player);
                        return false;
                    }
                    nc = new NationChunk(nationId, world, x, z);
                    for (int i = 0; i < max; i++) {
                        if (NationChunk.get(world, x, z + i) != null) continue;
                        NationChunk ncn = nc.withChunkZNew(nc.getZ() - i);
                        ncn.claim();
                        claimed++;
                    }
                    for (Player member : nation.getOnlineMemberSet())
                        Lang.PLAYER_CLAIMLINED.send(member, "%CHUNKS%;" + claimed, "%PLAYER%;" + player.getName());
                    Lang.PLAYER_CLAIMLINED.send(Bukkit.getConsoleSender(), "%CHUNKS%;" + claimed, "%PLAYER%;" + player.getName());
                    player.sendMessage(System.currentTimeMillis() - start + "ms");
                } else*/
                if (args[0].equalsIgnoreCase("all")) {
                    if (!profile.isStaff()) {
                        Lang.NO_PERMISSIONS.send(player);
                        return false;
                    }
                    Nation nation1 = Nation.getByName(args[1]);
                    Nation nation2 = Nation.getByName(args[2]);
                    if (nation1 == null) {
                        Lang.NATION_DOESNT_EXIST.send(player);
                        return false;
                    }
                    if (nation2 == null) {
                        Lang.NATION_DOESNT_EXIST.send(player);
                        return false;
                    }
                    if (!instance.getUtilLists().staffMode.contains(player.getUniqueId()) && (!Permission.hasForeignPermission(profile, Permission.CLAIM, nation1) || !Permission.hasForeignPermission(profile, Permission.CLAIM, nation2))) {
                        Lang.NO_PERMISSIONS.send(player);
                        return false;
                    }
                    final List<String> chunks1 = new ArrayList<>(nation1.getChunks());
                    nation1.unclaimAll();
                    for (String s : chunks1)
                        NationChunk.fromString(s.replace(nation1.getNationId(), nation2.getNationId())).claim();
                    Lang.PLAYER_CLAIM_ALLED.broadcast("%PLAYER%;" + player.getName(), "%NATION1%;" + nation1.getName(), "%NATION2%;" + nation2.getName());
                }
                break;
            default:
                sendSyntax(sender, "claim");
        }

        return false;
    }

    @Override
    public String getSyntax() {
        return "§7Claim the chunk you are in: §b/n claim §cone§8/§chere §8[§eNATION§8]\n" +
                "§7Enable/Disable autoclaim: §b/n claim §cauto §8[§eNATION§8]\n" +
                "§7Fill your claims: §b/n claim §cfill §8[§eNATION§8]";
    }

    private void floodSearch(Player player, Nation nation, int x, int z, String world, Table<Integer, Integer, NationChunk> toClaim) {

        if (!instance.getUtilLists().claimFill.contains(player.getUniqueId())) return;

        if (toClaim.contains(x, z)) return;

        if (NationChunk.get(world, x, z) != null) return;

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
                // if (nation.getMoney() < claimed * 15) break;
                chunk.claim();
                claimed++;
            }
            double price = claimed * 15;
            nation.withdraw(price);
            for (Player member : nation.getOnlineMemberSet())
                Lang.PLAYER_CLAIMFILLED.send(member, "%CHUNKS%;" + claimed, "%PLAYER%;" + claimer.getName());
            Lang.PLAYER_CLAIMFILLED.send(Bukkit.getConsoleSender(), "%CHUNKS%;" + claimed, "%PLAYER%;" + claimer.getName());
        }
    }

}
