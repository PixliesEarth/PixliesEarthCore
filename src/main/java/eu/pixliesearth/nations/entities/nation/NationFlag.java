package eu.pixliesearth.nations.entities.nation;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum NationFlag {

    OPEN(false),
    MONSTERS(false),
    PERMANENT(true),
    PEACEFUL(true),
    INF_POWER(true),
    ;

    private final boolean requiresStaff;

    NationFlag(boolean requiresStaff) {
        this.requiresStaff = requiresStaff;
    }

    public static List<String> defaultServerNations() {
        return Arrays.asList(PERMANENT.name(), PEACEFUL.name(), INF_POWER.name());
    }

    public static boolean exists(String name) {
        for (NationFlag flag : values())
            if (flag.name().equalsIgnoreCase(name))
                return true;
        return false;
    }

}
