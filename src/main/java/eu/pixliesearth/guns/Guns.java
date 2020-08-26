package eu.pixliesearth.guns;

import eu.pixliesearth.guns.guns.M16;
import lombok.Getter;

public enum Guns {

    M16(new M16(30)),
    ;

    private @Getter
    PixliesGun clazz;

    Guns(PixliesGun clazz) {
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
