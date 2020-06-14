package eu.pixliesearth.nations.entities.chunk;

import com.google.common.collect.RowSortedTable;
import com.google.common.collect.TreeBasedTable;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.managers.NationManager;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
public class NationChunk {

    public static Map<String, RowSortedTable<Integer, Integer, NationChunk>> table;

    private String nationId;
    private String world;
    private int x;
    private int z;

    public void claim() {
        if (table.get(world).get(x, z) == null) {
            RowSortedTable<Integer, Integer, NationChunk> rst = table.get(world);
            rst.put(x, z, this);
            table.put(world, rst);
            Nation nation = Nation.getById(nationId);
            if (!nation.getChunks().contains(serialize())) {
                nation.getChunks().add(serialize());
                nation.save();
            }
        }
    }

    public void unclaim() {
        if (table.get(world).get(x, z) != null) {
            RowSortedTable<Integer, Integer, NationChunk> rst = table.get(world);
            Nation nation = Nation.getById(nationId);
            if (nation.getChunks().contains(serialize())) {
                nation.getChunks().remove(serialize());
                nation.save();
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

    //ONLY RUN ONCE ONENABLE
    public static void init() {
        table = new HashMap<>();
        for (World world : Bukkit.getWorlds())
            table.put(world.getName(), TreeBasedTable.create());
        NationManager.nations.values().stream().parallel().forEach(nation -> {
            for (String s : nation.getChunks()) {
                NationChunk c = NationChunk.fromString(s);
                RowSortedTable<Integer, Integer, NationChunk> rst = table.get(c.getWorld());
                rst.put(c.getX(), c.getZ(), c);
                table.put(c.getWorld(), rst);
            }
        });
    }

}
