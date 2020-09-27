package eu.pixliesearth.nations.managers;

import com.google.gson.Gson;
import eu.pixliesearth.Main;
import eu.pixliesearth.nations.entities.nation.Nation;
import lombok.Data;
import org.bson.Document;

import java.util.*;

public class NationManager {

    public static Map<String, Nation> nations;
    public static Map<String, String> names;

    public static void init() {
        nations = new HashMap<>();
        names = new HashMap<>();
        Gson gson = new Gson();
        for (Document d : Main.getNationCollection().find()) {
            Nation nation = gson.fromJson(d.toJson(), Nation.class);
            if (nation.getNationId() != null) {
                nations.put(nation.getNationId(), nation);
                names.put(nation.getName(), nation.getNationId());
            }
        }
    }

    @Data
    public static class NationStorage {

        private static final Gson gson = new Gson();

        public boolean containsKey(String id) {
            return Main.getInstance().getJedis().get("nation:" + id) != null;
        }

        public Collection<Nation> values() {
            Collection<Nation> set = new HashSet<>();
            for (String s : Main.getInstance().getJedis().keys("nation:*"))
                set.add(gson.fromJson(s, Nation.class));
            return set;
        }

        public Nation get(String id) {
            return containsKey(id) ? gson.fromJson(Main.getInstance().getJedis().get("nation:" + id), Nation.class) : null;
        }

        public void put(String id, Nation value) {
            Main.getInstance().getJedis().set("nation:" + id, gson.toJson(value));
        }

        public void remove(String id) {
            Main.getInstance().getJedis().del("nation:" + id);
        }

    }

}
