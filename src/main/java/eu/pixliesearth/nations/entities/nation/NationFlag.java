package eu.pixliesearth.nations.entities.nation;

import lombok.Getter;

@Getter
public enum NationFlag {

    OPEN(false),
    MONSTERS(false),
    ;

    private final boolean requiresStaff;

    NationFlag(boolean requiresStaff) {
        this.requiresStaff = requiresStaff;
    }

}
