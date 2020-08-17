package eu.pixliesearth.nations.entities.nation;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public enum Era {

    TRIBAL("Tribal", 1, 3, 0),
    ANCIENT("Ancient", 2, 20, 15),
    MEDIEVAL("Medieval", 3, 100, 40),
    VICTORIAN("Victorian", 4, 150, 75),
    MODERN("Modern", 5, 300, 150),
    FUTURE("Future", 6, 300, 300),
    ;

    private @Getter final String name;
    private @Getter final int number;
    private @Getter final int chunksPerPlayer;
    private @Getter final int cost;

    Era(String name, int number, int chunksPerPlayer, int cost) {
        this.name = name;
        this.number = number;
        this.chunksPerPlayer = chunksPerPlayer;
        this.cost = cost;
    }

    public static Era getByName(String name) {
        for (Era era : values())
            if (era.name.equalsIgnoreCase(name))
                return era;
        return null;
    }

    public static Era getByNumber(int number) {
        for (Era era : values())
            if (era.number == number)
                return era;
        return null;
    }

    public boolean canAccess(Nation nation) {
        return nation.getCurrentEra().number >= number;
    }

}
