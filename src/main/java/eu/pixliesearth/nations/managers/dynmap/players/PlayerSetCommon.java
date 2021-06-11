package eu.pixliesearth.nations.managers.dynmap.players;

import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.managers.NationManager;
import org.dynmap.markers.MarkerAPI;
import org.dynmap.markers.PlayerSet;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static eu.pixliesearth.nations.managers.dynmap.DynmapEngine.info;
import static eu.pixliesearth.nations.managers.dynmap.commons.Constant.PREFIX_FACTION_SET_ID;

public class PlayerSetCommon {

    private static final String MESSAGE_ADDED_PLAYER    = "Added player visibility set '";
    private static final String MESSAGE_FOR_FACTION     = "' for Nation ";

    public static void updatePlayerSet(final MarkerAPI markerApi, String factionUUID) {
        /* If Wilderness or other unassociated factions (guid-style ID), skip */
        if(factionUUID.indexOf('-') >= 0) {
            return;
        }
        final Set<String> playerIds = new HashSet<>();

        final Nation nation = Nation.getById(factionUUID); // Get Nation
        if (nation != null) {
            final List<String> mPlayers = nation.getMembers();
            playerIds.addAll(mPlayers);
            factionUUID = nation.getNationId();
        }

        final String setId = PREFIX_FACTION_SET_ID + factionUUID;
        final PlayerSet set = markerApi.getPlayerSet(setId); // See if set exists
        if (set == null && nation != null) {
            markerApi.createPlayerSet(setId, true, playerIds, false);
            info(MESSAGE_ADDED_PLAYER + setId + MESSAGE_FOR_FACTION + factionUUID);
        }
        else if (nation != null) {
            set.setPlayers(playerIds);
        }
        else {
            set.deleteSet();
        }
    }

    public static void updatePlayerSets(final MarkerAPI markerAPI, final boolean hasPlayer) {
        if (hasPlayer) {
            for (final Nation nation : NationManager.nations.values()) {
                if (nation.getNationId().equalsIgnoreCase("safezone") || nation.getNationId().equalsIgnoreCase("warzone")) {
                    continue;
                }
                updatePlayerSet(markerAPI, nation.getNationId());
            }
        }
    }
}
