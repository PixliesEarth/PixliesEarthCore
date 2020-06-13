package eu.pixliesearth.core.guns;

import eu.pixliesearth.core.guns.gunObjects.AK;
import eu.pixliesearth.core.guns.gunObjects.GunInterface;
import lombok.Getter;

public enum Guns {

    AK(new AK());

    private @Getter GunInterface clazz;

    Guns(GunInterface clazz) {
        this.clazz = clazz;
    }

    public static boolean contains(String test) {
        for (Guns c : values()) {
            if (c.name().equals(test)) {
                return true;
            }
        }
        return false;
    }

}