package eu.pixliesearth.guns;

import eu.pixliesearth.guns.guns.AK47;
import eu.pixliesearth.guns.guns.M16;
import eu.pixliesearth.guns.guns.Slingshot;
import eu.pixliesearth.guns.guns.Uzi;
import lombok.Getter;

public enum Guns {

    M16(M16.class, 30),
    AK47(AK47.class, 40),
    UZI(Uzi.class, 32),
    K98K(eu.pixliesearth.guns.guns.K98K.class, 5),
    MP5(eu.pixliesearth.guns.guns.MP5.class, 15),
    SLINGSHOT(Slingshot.class, 1),
    RPG7(eu.pixliesearth.guns.guns.RPG7.class, 1),
    ;

    private @Getter final Class<? extends PixliesGun> clazz;
    private @Getter final int maxAmmo;

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
