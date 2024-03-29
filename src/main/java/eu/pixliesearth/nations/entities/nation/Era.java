package eu.pixliesearth.nations.entities.nation;

import lombok.Getter;

public enum Era {

    TRIBAL("Tribal", 1, 10, 0),
    ANCIENT("Ancient", 2, 25, 10),
    MEDIEVAL("Medieval", 3, 75, 25),
    VICTORIAN("Victorian", 4, 125, 50),
    MODERN("Modern", 5, 150, 100),
    FUTURE("Future", 6, 200, 800),
    ;

    private @Getter final String name;
    private @Getter final int number;
    private @Getter final int chunksPerPlayer;
    private @Getter final double cost;

    Era(String name, int number, int chunksPerPlayer, int cost) {
        this.name = name;
        this.number = number;
        this.chunksPerPlayer = chunksPerPlayer;
        this.cost = cost;
    }

    public static final Era HIGHEST = Era.FUTURE;

    public static Era getByName(String name) {
        for (Era era : values())
            if (era.name.equalsIgnoreCase(name))
                return era;
        return Era.TRIBAL;
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
