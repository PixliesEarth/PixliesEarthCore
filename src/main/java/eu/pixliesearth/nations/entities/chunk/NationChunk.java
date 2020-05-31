package eu.pixliesearth.nations.entities.chunk;

import eu.pixliesearth.nations.entities.nation.Nation;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NationChunk {

    private String nationId;
    private int x;
    private int z;

    public void claim() {
        if (ChunkBank.table.get(x, z) == null)
            ChunkBank.table.put(x, z, nationId);
        Nation nation = Nation.getById(nationId);
        if (!nation.getChunks().contains(serialize())) {
            nation.getChunks().add(serialize());
            nation.save();
        }
    }

    public void unclaim() {
        if (ChunkBank.table.get(x, z) != null)
            ChunkBank.table.remove(x, z);
        Nation nation = Nation.getById(nationId);
        if (nation.getChunks().contains(serialize())) {
            nation.getChunks().remove(serialize());
            nation.save();
        }
    }

    public String serialize() {
        return nationId + ";" + x + ";" + z;
    }

    public static NationChunk fromString(String s) {
        String[] split = s.split(";");
        return new NationChunk(split[0], Integer.parseInt(split[1]), Integer.parseInt(split[2]));
    }

}
