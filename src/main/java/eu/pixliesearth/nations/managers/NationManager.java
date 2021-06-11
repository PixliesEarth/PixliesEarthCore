package eu.pixliesearth.nations.managers;

import com.google.gson.Gson;
import eu.pixliesearth.Main;
import eu.pixliesearth.nations.entities.nation.Nation;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

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

/*    @Data
    public static class NationStorage {

        private static final Gson gson = new Gson();
        private static final Main instance = Main.getInstance();

        public boolean containsKey(String id) {
            return instance.getRedissonClient().getBucket("nation:" + id).isExists();
        }

        public Collection<Nation> values() {
            Collection<Nation> set = new HashSet<>();
            for (String s : instance.getRedissonClient().getKeys().getKeysByPattern("nation:*"))
                set.add(instance.getGson().fromJson((String) instance.getRedissonClient().getBucket(s).get(), Nation.class));
            return set;
        }

        public Nation get(String id) {
            return containsKey(id) ? instance.getGson().fromJson((String) instance.getRedissonClient().getBucket("nation:" + id).get(), Nation.class) : null;
        }

        public void put(String id, Nation value) {
            instance.getRedissonClient().getBucket("nation:" + id).set(instance.getGson().toJson(value));
        }

        public void remove(String id) {
            instance.getRedissonClient().getBucket("nation:" + id).delete();
        }

    }*/

}
