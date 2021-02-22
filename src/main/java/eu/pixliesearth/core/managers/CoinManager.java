package eu.pixliesearth.core.managers;

import eu.pixliesearth.core.objects.Profile;

public class CoinManager {

    public static boolean take(Profile profile, double amount) {
        if (profile.getPixliecoins() - amount < 0.0) return false;
        profile.setPixliecoins(profile.getPixliecoins() - amount);
        return true;
    }

    public static void add(Profile profile, double amount) {
        profile.setPixliecoins(profile.getPixliecoins() + amount);
    }

    public static void set(Profile profile, double amount) {
        profile.setPixliecoins(amount);
    }

}
