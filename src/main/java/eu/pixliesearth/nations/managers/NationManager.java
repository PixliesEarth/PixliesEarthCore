package eu.pixliesearth.nations.managers;

import com.google.gson.Gson;
import eu.pixliesearth.Main;
import eu.pixliesearth.nations.entities.nation.Nation;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

public class NationManager {

    public static Map<String, Nation> nations;

    public static void init() {
        nations = new HashMap<>();
        Gson gson = new Gson();
        for (Document d : Main.getNationCollection().find()) {
            Nation nation = gson.fromJson(d.toJson(), Nation.class);
            if (nation.getNationId() != null)
                nations.put(nation.getNationId(), nation);
        }
    }

}
