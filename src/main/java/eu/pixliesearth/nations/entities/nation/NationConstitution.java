package eu.pixliesearth.nations.entities.nation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum NationConstitution {

    /*
     * Values:
     * 0 - No
     * 1 - Restricted
     * 2 - Yes
     */
    ALCOHOL_CONSUMPTION(2),
    FIREARM_POSSESSION(1),
    ELECTIONS(2),
    CENTRALIZED_GOVERNMENT(2),
    FREEDOM_OF_SPEECH(2),
    FREEDOM_OF_PRESS(2),
    PRIVATE_PROPERTY(2),

    ;

    private @Getter final int defaultValue;

}
