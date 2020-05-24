package eu.pixliesearth.nations.entities.chunk;

import com.google.common.collect.RowSortedTable;
import com.google.common.collect.TreeBasedTable;
import eu.pixliesearth.nations.managers.NationManager;

public class ChunkBank {

    public static RowSortedTable<Integer, Integer, String> table;

    public static void init() {
        table = TreeBasedTable.create();
        NationManager.nations.values().stream().parallel().forEach(nation -> {
            for (String s : nation.getChunks()) {
                NationChunk c = NationChunk.fromString(s);
                table.put(c.getX(), c.getZ(), nation.getNationId());
            }
        });
    }

}
