package eu.pixliesearth.core.machines;

import eu.pixliesearth.Main;

import java.io.Serializable;

public class MachineTask extends Thread implements Serializable {

    private static final Main instance = Main.getInstance();
    private static final long serialVersionUID = -4595269124932363893L;
    private boolean ready = false;

    public MachineTask() {
        start();
    }

    @Override
    public void run() {
        while (true) {
            //Tick
            try {
                if (ready)
                    for (Machine machine : instance.getUtilLists().machines.values())
                        machine.tick();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //Thread Sleep
            try {
                sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void init() {
        Machine.loadAll();
        this.ready = true;
    }

    public void stopThread() {
        this.ready = false;
        try {
            join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
