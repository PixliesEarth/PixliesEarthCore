package eu.pixliesearth.core.guns;

import eu.pixliesearth.core.guns.gunObjects.AK47;
import eu.pixliesearth.core.guns.gunObjects.Musket;
import lombok.Getter;

public enum Guns {

    AK(new AK47()),
    MUSKET(new Musket());
    private @Getter Gun clazz;

    Guns(Gun clazz) {
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
