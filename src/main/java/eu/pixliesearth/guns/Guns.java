package eu.pixliesearth.guns;

import eu.pixliesearth.guns.guns.M16;
import lombok.Getter;

import java.util.UUID;

public enum Guns {

    M16(new M16(30, UUID.randomUUID())),
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
