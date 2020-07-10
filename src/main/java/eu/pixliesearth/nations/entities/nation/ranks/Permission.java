package eu.pixliesearth.nations.entities.nation.ranks;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.nations.entities.nation.Nation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public enum Permission {

    INVITE(1),
    MODERATE(2),
    CLAIM(4),
    UNCLAIM(8),
    BUILD(16),
    INTERACT(32),
    MANAGE_SETTLEMENTS(64);

    int number;

    Permission(int number) {
        this.number = number;
    }

    public static boolean hasNationPermission(Profile profile, Permission permission) {
        if (!profile.isInNation()) return false;
        if (profile.getNationRank().equals("leader")) return true;
        Nation nation = profile.getCurrentNation();
        Rank rank = (Rank) nation.getRanks().get(profile.getNationRank());
        return rank.getPermissions().contains(permission.name());
    }

/*    public static List<Permission> getPermissions(int day) {
        List<Integer> places = new ArrayList<>();
        for (Permission p : values())
            places.add(p.number);
        ArrayList<Permission> d = new ArrayList<>();
        for (Integer i : places) {
            if (bitWiseAnd(day, i)) {
                d.add(getByNumber(i));
            }
        }
        return d;
    }

    public static boolean bitWiseAnd(int bitwise, int operator) {
        return (bitwise & operator) > 0;
    }

    public static Permission getByNumber(int number) {
        for (Permission p : values())
            if (p.number == number)
                return p;
        return null;
    }*/

}
