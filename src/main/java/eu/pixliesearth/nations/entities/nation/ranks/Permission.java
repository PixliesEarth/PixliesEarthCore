package eu.pixliesearth.nations.entities.nation.ranks;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.nations.entities.nation.Nation;

public enum Permission {

    INVITE,
    MODERATE,
    CLAIM,
    UNCLAIM,
    BUILD,
    INTERACT;

    public static boolean hasNationPermission(Profile profile, Permission permission) {
        if (!profile.isInNation()) return false;
        if (profile.getNationRank().equals("leader")) return true;
        Nation nation = profile.getCurrentNation();
        Rank rank = Rank.getFromMap(nation.getRanks().get(profile.getNationRank()));
        return rank.getPermissions().contains(permission.name());
    }

}
