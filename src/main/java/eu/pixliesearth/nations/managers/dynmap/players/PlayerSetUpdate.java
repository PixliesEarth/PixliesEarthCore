package eu.pixliesearth.nations.managers.dynmap.players;

import eu.pixliesearth.nations.managers.dynmap.DynmapEngine;

import static eu.pixliesearth.nations.managers.dynmap.players.PlayerSetCommon.updatePlayerSet;

public class PlayerSetUpdate implements Runnable {
    private final DynmapEngine kernel;
    private final String                 factionUUID;

    public PlayerSetUpdate(final DynmapEngine kernel, final String factionUUID) {
        this.kernel = kernel;
        this.factionUUID = factionUUID;
    }

    @Override
    public void run() {
        if (!kernel.isStop()) {
            updatePlayerSet(kernel.getMarkerAPI(), factionUUID);
        }
    }
}
