package eu.pixliesearth.core.managers;

import eu.pixliesearth.core.objects.Profile;

public class BoostManager {

    public static boolean take(Profile profile, int amount) {
        if (profile.getBoosts() - amount < 0) return false;
        profile.setBoosts(profile.getBoosts() - amount);
        return true;
    }

    public static void add(Profile profile, int amount) {
        profile.setBoosts(profile.getBoosts() + amount);
    }

    public static void set(Profile profile, int amount) {
        profile.setBoosts(amount);
    }

}
