package eu.pixliesearth.guns;

import eu.pixliesearth.guns.guns.AK47;
import eu.pixliesearth.guns.guns.M16;
import lombok.Getter;

import java.util.UUID;

public enum Guns {

    M16(M16.class, 30),
    AK47(AK47.class, 40),
    ;

    private @Getter Class<? extends PixliesGun> clazz;
    private @Getter int maxAmmo;

    Guns(Class<? extends PixliesGun> clazz, int maxAmmo) {
        this.clazz = clazz;
        this.maxAmmo = maxAmmo;
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
