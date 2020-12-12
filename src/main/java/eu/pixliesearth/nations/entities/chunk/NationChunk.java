package eu.pixliesearth.nations.entities.chunk;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.gson.Gson;
import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.events.TerritoryChangeEvent;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.entities.nation.NationFlag;
import eu.pixliesearth.nations.entities.nation.ranks.Permission;
import eu.pixliesearth.nations.entities.settlements.Settlement;
import eu.pixliesearth.nations.managers.NationManager;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
public class NationChunk {

    public static Map<String, Table<Integer, Integer, NationChunk>> table;

    private String nationId;
    private String world;
    private int x;
    private int z;

    public boolean claim() {
        try {
            if (table.get(world).get(x, z) == null) {
                Table<Integer, Integer, NationChunk> rst = table.get(world);
                rst.put(x, z, this);
                table.put(world, rst);
                Nation nation = Nation.getById(nationId);
                if (!nation.getChunks().contains(serialize())) {
                    nation.getChunks().add(serialize());
                    nation.save();
                }
                System.out.println("§bChunk claimed at §e" + x + "§8, §e" + z + " §bfor §e" + nation.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void unclaim() {
        if (table.get(world).get(x, z) != null) {
            Table<Integer, Integer, NationChunk> rst = table.get(world);
            Nation nation = Nation.getById(nationId);
            if (nation.getChunks().contains(serialize())) {
                nation.getChunks().remove(serialize());
                nation.save();
            }
            Map<String, String> settlements = new HashMap<>(nation.getSettlements());
            for (Map.Entry<String, String> s : settlements.entrySet()) {
                Settlement settlement = new Gson().fromJson(s.getValue(), Settlement.class);
                try {
                    if (NationChunk.get(settlement.getAsBukkitLocation().getChunk()).equals(this))
                        nation.getSettlements().remove(s.getKey());
                    nation.save();
                } catch (Exception ignored) {}
            }
            rst.remove(x, z);
            table.put(world, rst);
        }
    }

    public String serialize() {
        return nationId + ";" + world + ";" + x + ";" + z;
    }

    public Nation getCurrentNation() {
        return Nation.getById(nationId);
    }

    public static NationChunk fromString(String s) {
        String[] split = s.split(";");
        return new NationChunk(split[0], split[1], Integer.parseInt(split[2]), Integer.parseInt(split[3]));
    }

    public static NationChunk get(String world, int x, int z) {
        if (!table.get(world).contains(x, z))
            return null;
        return table.get(world).get(x, z);
    }

    public static NationChunk get(Chunk chunk) {
        if (!table.get(chunk.getWorld().getName()).contains(chunk.getX(), chunk.getZ()))
            return null;
        return table.get(chunk.getWorld().getName()).get(chunk.getX(), chunk.getZ());
    }

    public NationChunk withChunkX(Integer chunkX) { return get(this.getWorld(), chunkX, this.getZ()); }
    public NationChunk withChunkZ(Integer chunkZ) { return get(this.getWorld(), this.getX(), chunkZ); }
    public NationChunk withChunkXNew(Integer chunkX) { return new NationChunk(nationId, world, chunkX, z); }
    public NationChunk withChunkZNew(Integer chunkZ) { return new NationChunk(nationId, world, x, chunkZ); }

    public static Nation getNationData(Chunk chunk) {
        NationChunk c = get(chunk);
        if (c == null)
            return null;
        return Nation.getById(c.getNationId());
    }

    public static String getNationId(Chunk chunk) {
        NationChunk c = get(chunk);
        if (c == null)
            return null;
        return c.getNationId();
    }

    public static Nation getNationData(String world, int x, int z) {
        NationChunk c = get(world, x, z);
        if (c == null)
            return null;
        return Nation.getById(c.getNationId());
    }

    public static boolean claim(Player player, String world, int x, int z, TerritoryChangeEvent.ChangeType changeType, String nationId) {
        if (!world.equalsIgnoreCase("world") && !Main.getInstance().getUtilLists().staffMode.contains(player.getUniqueId())) {
            player.sendMessage(Lang.NATION + "§cYou can't claim in this world.");
            return false;
        }
        if (get(world, x, z) != null) {
            player.sendMessage(Lang.ALREADY_CLAIMED.get(player));
            return false;
        }
        Nation nation = Nation.getById(nationId);
        if (nation.getClaimingPower() <= 0 && !Main.getInstance().getUtilLists().staffMode.contains(player.getUniqueId()) && !nation.getFlags().contains(NationFlag.INF_POWER.name())) {
            Lang.NOT_ENOUGH_POWER_TO_CLAIM.send(player);
            return false;
        }
/*        if (nation.getMoney() < 15 && !Main.getInstance().getProfile(player.getUniqueId()).isStaff()) {
            Lang.NOT_ENOUGH_MONEY_IN_NATION.send(player);
            return false;
        }*/
        NationChunk nc = new NationChunk(nationId, world, x, z);
        TerritoryChangeEvent event = new TerritoryChangeEvent(player, Arrays.asList(nc), changeType);
        Bukkit.getPluginManager().callEvent(event);
        if (!event.isCancelled()) {
            boolean claim = nc.claim();
            // nation.withdraw(15);
            for (Player members : nation.getOnlineMemberSet())
                members.sendMessage(Lang.PLAYER_CLAIMED.get(members).replace("%PLAYER%", player.getDisplayName()).replace("%X%", x + "").replace("%Z%", z + ""));
        }
        return true;
    }

    public static boolean unclaim(Player player, String world, int x, int z, TerritoryChangeEvent.ChangeType changeType) {
        NationChunk nc = get(world, x, z);
        if (nc == null) return false;
        boolean allowed = false;
        if (Main.getInstance().getUtilLists().staffMode.contains(player.getUniqueId())) allowed = true;
        Profile profile = Main.getInstance().getProfile(player.getUniqueId());
        if (changeType.equals(TerritoryChangeEvent.ChangeType.UNCLAIM_ONE_SELF) && profile.isInNation() && Permission.hasNationPermission(profile, Permission.UNCLAIM) && profile.getNationId().equals(nc.getNationId())) allowed = true;
        if (changeType.equals(TerritoryChangeEvent.ChangeType.UNCLAIM_ONE_OTHER) && profile.isInNation() && Permission.hasForeignPermission(profile, Permission.UNCLAIM, Nation.getById(nc.getNationId())) && !profile.getNationId().equals(nc.getNationId())) allowed = true;
        if (changeType.equals(TerritoryChangeEvent.ChangeType.UNCLAIM_AUTO_SELF) && profile.isInNation() && Permission.hasNationPermission(profile, Permission.UNCLAIM) && profile.getNationId().equals(nc.getNationId())) allowed = true;
        if (changeType.equals(TerritoryChangeEvent.ChangeType.UNCLAIM_AUTO_OTHER) && profile.isInNation() && Permission.hasForeignPermission(profile, Permission.UNCLAIM, Nation.getById(nc.getNationId())) && !profile.getNationId().equals(nc.getNationId())) allowed = true;
        if (!allowed) {
            Lang.CHUNK_NOT_YOURS.send(player);
            return false;
        }
        TerritoryChangeEvent event = new TerritoryChangeEvent(player, Collections.singletonList(nc), changeType);
        Bukkit.getPluginManager().callEvent(event);
        if (!event.isCancelled()) {
            // Nation.getById(nc.getNationId()).deposit(15);
            nc.unclaim();
            for (Player members : profile.getCurrentNation().getOnlineMemberSet())
                members.sendMessage(Lang.PLAYER_UNCLAIMED.get(members).replace("%PLAYER%", player.getDisplayName()).replace("%X%", x + "").replace("%Z%", z + ""));
            System.out.println("§bChunk unclaimed at §e" + nc.getX() + "§8, §e" + nc.getZ());
        }
        return true;
    }

    //ONLY RUN ONCE
    public static void init() {
        table = new HashMap<>();
        for (World world : Bukkit.getWorlds())
            table.put(world.getName(), HashBasedTable.create());
        NationManager.nations.values().stream().parallel().forEach(nation -> {
            for (String s : nation.getChunks()) {
                NationChunk c = NationChunk.fromString(s);
                Table<Integer, Integer, NationChunk> rst = table.get(c.getWorld());
                rst.put(c.getX(), c.getZ(), c);
                table.put(c.getWorld(), rst);
            }
        });
    }

}
