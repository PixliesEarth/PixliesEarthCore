package eu.pixliesearth.nations.managers.dynmap.pojo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class NationBlocks {
    private final Map<String, LinkedList<NationBlock>> blocks = new HashMap<>();

    public Map<String, LinkedList<NationBlock>> getBlocks() {
        return blocks;
    }

    public void clear() {
        blocks.clear();
    }
}
