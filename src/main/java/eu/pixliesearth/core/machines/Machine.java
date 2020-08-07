package eu.pixliesearth.core.machines;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import eu.pixliesearth.Main;
import eu.pixliesearth.core.machines.cargo.InputNode;
import eu.pixliesearth.core.machines.cargo.OutputNode;
import eu.pixliesearth.core.machines.carpentrymill.CarpentryMill;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.Timer;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
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

    public void remove() {
        File file = new File("plugins/PixliesEarthCore/machines/" + id + ".yml");
        instance.getUtilLists().machines.remove(location);
        armorStand.delete();
        if (!file.exists()) return;
        file.delete();
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
            Machine machine = load(file, conf);
            instance.getUtilLists().machines.put(machine.getLocation(), machine);
        }
    }

    public static Machine load(File file, FileConfiguration conf) {
        Hologram holo = HologramsAPI.createHologram(instance, conf.getLocation("holo.location"));
        Timer timer = conf.contains("timer.expiry") ? new Timer(conf.getLong("timer.expiry"), conf.getBoolean("timer.ended")) : null;
        MachineCraftable wantsToCraft = conf.contains("wantsToCraft") ? MachineCraftable.valueOf(conf.getString("wantsToCraft")) : null;
        if (conf.getString("type").equalsIgnoreCase(MachineType.CARPENTRY_MILL.name())) {
            holo.appendTextLine(conf.getString("holo.text"));
            return new CarpentryMill(file.getName().replace(".yml", ""), conf.getLocation("location"), holo, timer, wantsToCraft);
        } else if (conf.getString("type").equalsIgnoreCase(MachineType.OUTPUT_NODE.name())) {
            Inventory inventory = Bukkit.createInventory(null, 9 * 6, "§b§lOutput Node");
            if (conf.contains("storage")) {
                for (String s : conf.getConfigurationSection("storage").getKeys(false))
                    inventory.setItem(Integer.parseInt(s), conf.getItemStack("storage." + s));
            }
            return new OutputNode(file.getName().replace(".yml", ""), conf.getLocation("location"), holo, timer, wantsToCraft, inventory);
        } else if (conf.getString("type").equalsIgnoreCase(MachineType.INPUT_NODE.name())) {
            Inventory inventory = Bukkit.createInventory(null, 9 * 6, "§c§lInput Node");
            if (conf.contains("storage")) {
                for (String s : conf.getConfigurationSection("storage").getKeys(false))
                    inventory.setItem(Integer.parseInt(s), conf.getItemStack("storage." + s));
            }
            return new InputNode(file.getName().replace(".yml", ""), conf.getLocation("location"), holo, timer, wantsToCraft, inventory);
        }
        return null;
    }

    public enum MachineType {

        CARPENTRY_MILL,
        INPUT_NODE,
        OUTPUT_NODE
        ;

    }

    public enum MachineCraftable {

        // CARPENTRY MILL
        CUT_WOOD(MachineType.CARPENTRY_MILL, new ItemBuilder(Material.OAK_LOG).setDisplayName("Cut Wood").addLoreLine("§a32 §7oak-log > §a4x64 §7oak-planks").addLoreLine("§7Time: §b5 sec").build(), Collections.singletonList(new ItemStack(Material.OAK_LOG, 32)), Arrays.asList(new ItemStack(Material.OAK_PLANKS, 64), new ItemStack(Material.OAK_PLANKS, 64), new ItemStack(Material.OAK_PLANKS, 64), new ItemStack(Material.OAK_PLANKS, 64)), 5),
        MAKE_CHESTS(MachineType.CARPENTRY_MILL, new ItemBuilder(Material.CHEST).setDisplayName("Make Chests").addLoreLine("§a64 §7oak-logs > §a64 §7chests").addLoreLine("§7Time: §b10 sec").build(), Collections.singletonList(new ItemStack(Material.OAK_LOG, 64)), Collections.singletonList(new ItemStack(Material.CHEST, 64)), 10),
        MAKE_FENCES(MachineType.CARPENTRY_MILL, new ItemBuilder(Material.OAK_FENCE).setGlow().setDisplayName("Make Fences").addLoreLine("§a64 §7oak-log > §a3x64 §7oak-fences").addLoreLine("§7Time: §b20 sec").build(), Collections.singletonList(new ItemStack(Material.OAK_LOG, 64)), Arrays.asList(new ItemStack(Material.OAK_FENCE, 64), new ItemStack(Material.OAK_FENCE, 64), new ItemStack(Material.OAK_FENCE, 64)), 20),
        STICKS_TO_PLANKS(MachineType.CARPENTRY_MILL, new ItemBuilder(Material.STICK).setDisplayName("Sticks to Planks").addLoreLine("§a2x64 §7sticks > §a64 oak-planks").addLoreLine("§7Time: §b5 sec").build(), Arrays.asList(new ItemStack(Material.STICK, 64), new ItemStack(Material.STICK, 64)), Collections.singletonList(new ItemStack(Material.OAK_PLANKS, 64)), 5),
        MAKE_ITEMFRAMES(MachineType.CARPENTRY_MILL, new ItemBuilder(Material.ITEM_FRAME).setGlow().setDisplayName("Make Itemframes").addLoreLine("§a1 §7white-wool, §a16 §7sticks > §a16 §7item-frames").addLoreLine("§7Time: §b9 seconds").build(), Arrays.asList(new ItemStack(Material.WHITE_WOOL), new ItemStack(Material.STICK, 16)), Collections.singletonList(new ItemStack(Material.ITEM_FRAME, 16)), 9),


        ;

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

    protected void setProgressBarItems(final Inventory inventory, long current_progress, long required_progress) {
        int progress_percentage = (int)(current_progress * 100.0 / required_progress + 0.5);
        int bar_length = 9;
        for (int i = 0; i < bar_length; i++) {
            if (i < bar_length * progress_percentage) {
                inventory.setItem(Machine.progressSlots.get(i), new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setNoName().build());
            } else {
                inventory.setItem(Machine.progressSlots.get(i), new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setNoName().build());
            }
        }
    }

}
