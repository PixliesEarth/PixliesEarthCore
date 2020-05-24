package eu.pixliesearth.nations.entities.chunk;

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
    }

    public void unclaim() {
        if (ChunkBank.table.get(x, z) != null)
            ChunkBank.table.remove(x, z);
    }

    public String serialize() {
        return nationId + ";" + x + ";" + z;
    }

    public static NationChunk fromString(String s) {
        String[] split = s.split(";");
        return new NationChunk(split[0], Integer.parseInt(split[1]), Integer.parseInt(split[2]));
    }

}
