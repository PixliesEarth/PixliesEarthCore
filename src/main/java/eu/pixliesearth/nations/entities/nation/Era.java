package eu.pixliesearth.nations.entities.nation;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public enum Era {

    START("start", 1, 0),
    WOODEN("wooden", 5, 5),
    STONE("stone", 10,10),
    TRIBAL("tribal", 15,20),
    PROTO("proto", 25,50),
    ANCIENT("ancient", 100,100),
    BRONZE("bronze", 125,150),
    IRON("iron", 200,200);

    private @Getter String name;
    private @Getter int chunksPerPlayer;
    private @Getter int cost;

    Era(String name, int chunksPerPlayer, int cost) {
        this.name = name;
        this.chunksPerPlayer = chunksPerPlayer;
        this.cost = cost;
    }

    public static List<Era> listEras() {
        List<Era> list = new ArrayList<>();
        list.add(START);
        list.add(WOODEN);
        list.add(STONE);
        list.add(TRIBAL);
        list.add(PROTO);
        list.add(ANCIENT);
        list.add(BRONZE);
        list.add(IRON);
        return list;
    }

    public static Era getByName(String name) {
        for (Era era : listEras())
            if (era.name.equalsIgnoreCase(name))
                return era;
        return null;
    }

}
