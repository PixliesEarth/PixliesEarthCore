package eu.pixliesearth.nations.entities.nation;

public class NationThread extends Thread {

    private volatile boolean running = false;

    @Override
    public void run() {
        while (running) {

        }
    }

    public void startThread() {
        this.running = true;
    }

}
