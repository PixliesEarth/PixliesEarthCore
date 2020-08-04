package eu.pixliesearth.core.machines;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import eu.pixliesearth.Main;
import eu.pixliesearth.core.machines.carpentrymill.CarpentryMill;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.Timer;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
public class Machine {

    protected static final Main instance = Main.getInstance();

    public static final List<Integer> craftSlots = Arrays.asList(0, 1, 2, 3, 9, 18, 27, 10, 11, 12, 19, 20, 21, 28, 29, 30);
    public static final List<Integer> resultSlots = Arrays.asList(5, 6, 7, 8, 17, 26, 35, 14, 15, 16, 23, 24, 25, 32, 33, 34);
    public static final List<Integer> progressSlots = Arrays.asList(36, 37, 38, 39, 40, 41, 42, 43, 44);

    protected String id;
    protected Location location;
    protected MachineType type;
    protected ItemStack item;
    protected Timer timer;
    protected Hologram armorStand;
    protected MachineCraftable wantsToCraft;

    public void tick() {}

    public void open(Player player) {}

    public void save() throws IOException {
        File file = new File("plugins/PixliesEarthCore/machines", id + ".yml");

        if (!file.exists())
            file.createNewFile();

        FileConfiguration conf = YamlConfiguration.loadConfiguration(file);
        conf.set("location", location);
        conf.set("type", type.name());
        if (wantsToCraft != null) {
            conf.set("wantsToCraft", wantsToCraft.name());
        } else {
            conf.set("wantsToCraft", null);
        }
        conf.set("item", item);
        if (timer != null) {
            conf.set("timer.expiry", timer.getExpiry());
            conf.set("timer.ended", timer.isEnded());
        } else {
            conf.set("timer", null);
        }
        conf.set("holo.location", armorStand.getLocation());
        conf.set("holo.text", getTitle());
        conf.save(file);
    }

    protected static Location holoLocation(Location location) {
        Location toSpawn = location.clone();
        toSpawn.setY(toSpawn.getY() + 1D);
        toSpawn.setX(toSpawn.getX() + 0.5);
        toSpawn.setZ(toSpawn.getZ() + 0.5);
        return toSpawn;
    }
    
    public String getTitle() {
        return "Machine";
    }

    public static void loadAll() {
        for (File file : new File("plugins/PixliesEarthCore/machines/").listFiles()) {
            FileConfiguration conf = YamlConfiguration.loadConfiguration(file);
            Hologram holo = HologramsAPI.createHologram(instance, conf.getLocation("holo.location"));
            holo.appendTextLine(conf.getString("holo.text"));
            Timer timer = conf.contains("timer.expiry") ? new Timer(conf.getLong("timer.expiry"), conf.getBoolean("timer.ended")) : null;
            MachineCraftable wantsToCraft = conf.contains("wantsToCraft") ? MachineCraftable.valueOf(conf.getString("wantsToCraft")) : null;
            if (conf.getString("type").equalsIgnoreCase(MachineType.CARPENTRY_MILL.name())) {
                instance.getUtilLists().machines.put(conf.getLocation("location"), new CarpentryMill(file.getName().replace(".yml", ""), conf.getLocation("location"), holo, timer, wantsToCraft));
            }
        }
    }

    public enum MachineType {

        CARPENTRY_MILL;

    }

    public enum MachineCraftable {

        CUT_WOOD(MachineType.CARPENTRY_MILL, new ItemBuilder(Material.OAK_LOG).setDisplayName("Cut Wood").addLoreLine("§a32 §7oak-log > §a4x64 §7oak-planks").build(), Arrays.asList(new ItemStack(Material.OAK_LOG, 32)), Arrays.asList(new ItemStack(Material.OAK_PLANKS, 64), new ItemStack(Material.OAK_PLANKS, 64), new ItemStack(Material.OAK_PLANKS, 64), new ItemStack(Material.OAK_PLANKS, 64)), 4);

        public MachineType type;
        public ItemStack icon;
        public List<ItemStack> ingredients;
        public List<ItemStack> results;
        public int seconds;

        MachineCraftable(MachineType type, ItemStack icon, List<ItemStack> ingredients, List<ItemStack> results, int seconds) {
            this.type = type;
            this.icon = icon;
            this.ingredients = ingredients;
            this.results = results;
            this.seconds = seconds;
        }

        public static boolean exists(String name) {
            for (MachineCraftable c : values())
                if (c.name().equalsIgnoreCase(name))
                    return true;
            return false;
        }

    }

}
