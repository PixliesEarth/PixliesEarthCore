package eu.pixliesearth.guns;

import eu.pixliesearth.guns.gunObjects.AK;
import eu.pixliesearth.guns.gunObjects.GunInterface;
import lombok.Getter;

public enum Guns {

    AK(new AK());

    private @Getter GunInterface clazz;

    Guns(GunInterface clazz) {
        this.clazz = clazz;
    }

}
