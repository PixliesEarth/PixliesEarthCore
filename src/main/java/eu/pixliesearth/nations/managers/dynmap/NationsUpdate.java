package eu.pixliesearth.nations.managers.dynmap;

public class NationsUpdate implements Runnable {
    private final DynmapEngine kernel;
    private boolean runOnce;

    public NationsUpdate(final DynmapEngine kernel) {
        this.kernel = kernel;
    }

    public boolean isRunOnce() {
        return runOnce;
    }

    public void setRunOnce(boolean runOnce) {
        this.runOnce = runOnce;
    }

    @Override
    public synchronized void run() {
        if (!kernel.isStop()) {
            kernel.updateClaimedChunk();
            if (!isRunOnce()) {
                kernel.scheduleSyncDelayedTask(this, kernel.getUpdperiod());
            }
        }
    }
}
