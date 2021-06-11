package eu.pixliesearth.nations.managers.dynmap.pojo;

public class NationBlock {
    private final int x;
    private final int z;

    public NationBlock(final int x, final int z) {
        this.x = x;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }
}
