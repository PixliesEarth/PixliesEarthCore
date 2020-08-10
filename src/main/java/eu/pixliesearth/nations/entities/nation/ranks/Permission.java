package eu.pixliesearth.nations.entities.nation.ranks;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.nations.entities.nation.Nation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public enum Permission {

    INVITE,
    MODERATE,
    CLAIM,
    UNCLAIM,
    BUILD,
    INTERACT,
    MANAGE_SETTLEMENTS,
    EDIT_RANKS,
    MANAGE_RELATIONS,
    BANK_DEPOSIT,
    BANK_WITHDRAW,
    PURCHASE_UPGRADES,
    FOREIGN_PERMS
    ;

    public static boolean hasNationPermission(Profile profile, Permission permission) {
        if (!profile.isInNation()) return false;
        if (profile.getNationRank().equals("leader")) return true;
        Nation nation = profile.getCurrentNation();
        Rank rank = Rank.get(nation.getRanks().get(profile.getNationRank()));
        return rank.getPermissions().contains(permission.name());
    }

    public static boolean hasForeignPermission(Profile profile, Permission permission, Nation host) {
        if (profile.getExtras().containsKey("PERMISSION:" + host.getNationId() + ":" + permission.name())) return true;
        if (profile.isInNation())
            return profile.getCurrentNation().getExtras().containsKey("PERMISSION:" + host.getNationId() + ":" + permission.name());
        return false;
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

    public static boolean exists(String name) {
        for (Permission p : values()) {
            if (p.name().equalsIgnoreCase(name))
                return true;
        }
        return false;
    }

}
