package eu.pixliesearth.nations.entities.nation;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public enum Era {

    TRIBAL("Tribal", 0, 15, 0),
    BRONZE("Bronze", 1, 30, 50),
    ;

    private @Getter String name;
    private @Getter int level;
    private @Getter int chunksPerPlayer;
    private @Getter int cost;

    Era(String name, int level, int chunksPerPlayer, int cost) {
        this.name = name;
        this.level = level;
        this.chunksPerPlayer = chunksPerPlayer;
        this.cost = cost;
    }

    public static Era getByName(String name) {
        for (Era era : values())
            if (era.name.equalsIgnoreCase(name))
                return era;
        return null;
    }

    public boolean canAccess(Nation nation) {
        return nation.getCurrentEra().level >= level;
    }

}
