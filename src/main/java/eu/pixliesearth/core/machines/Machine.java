package eu.pixliesearth.core.machines;

import java.io.File;
import java.io.IOException;
import java.util.*;

import eu.pixliesearth.core.machines.autocrafters.AutoCrafterMachine;
import eu.pixliesearth.core.machines.autocrafters.FuelableAutoCrafterMachine;
import eu.pixliesearth.core.machines.autocrafters.forge.bronze.BronzeForge;
import eu.pixliesearth.core.machines.cargo.CargoMachine;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.customitems.ci.weapons.melee.ItemBronzeSword;
import eu.pixliesearth.core.machines.autocrafters.kiln.Kiln;
import eu.pixliesearth.core.machines.autocrafters.pottery.Pottery;
import eu.pixliesearth.core.machines.autocrafters.tinkertable.TinkerTable;
import eu.pixliesearth.core.machines.cargo.InputNode;
import eu.pixliesearth.core.machines.cargo.OutputNode;
import eu.pixliesearth.nations.entities.nation.Era;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.Timer;
import lombok.AllArgsConstructor;
import lombok.Data;

import static org.bukkit.Material.*;

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

    protected Map<String, Object> extras() {
        return new HashMap<>();
    }

    public void save() throws IOException {
        File file = new File("plugins/PixliesEarthCore/machines", id + ".yml");

        if (!file.exists())
            file.createNewFile();

        FileConfiguration conf = YamlConfiguration.loadConfiguration(file);
        conf.set("class", this.getClass().getName());
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
        for (Map.Entry<String, Object> entry : extras().entrySet())
            conf.set(entry.getKey(), entry.getValue());
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

    @SneakyThrows
    public static Machine load(File file, FileConfiguration conf) {
        Hologram holo = HologramsAPI.createHologram(instance, conf.getLocation("holo.location"));
        Timer timer = conf.contains("timer.expiry") ? new Timer(conf.getLong("timer.expiry"), conf.getBoolean("timer.ended")) : null;
        MachineCraftable wantsToCraft = conf.contains("wantsToCraft") ? MachineCraftable.valueOf(conf.getString("wantsToCraft")) : null;
        Class<? extends Machine> clazz = (Class<? extends Machine>) Class.forName(conf.getString("class"));
        MachineType type = MachineType.valueOf(conf.getString("type"));

        if (clazz.isAssignableFrom(FuelableAutoCrafterMachine.class)) {
            holo.appendTextLine(conf.getString("holo.text"));
            return clazz.getConstructor(String.class, Location.class, Hologram.class, Timer.class, MachineCraftable.class, int.class).newInstance(file.getName().replace(".yml", ""), conf.getLocation("location"), holo, timer, wantsToCraft, conf.getInt("fuel"));
        } else if (clazz.isAssignableFrom(AutoCrafterMachine.class)) {
            holo.appendTextLine(conf.getString("holo.text"));
            return clazz.getConstructor(String.class, Location.class, Hologram.class, Timer.class, MachineCraftable.class).newInstance(file.getName().replace(".yml", ""), conf.getLocation("location"), holo, timer, wantsToCraft);
        } else if (clazz.isAssignableFrom(CargoMachine.class)) {
            Inventory inventory = Bukkit.createInventory(null, 9 * 6, type.getItem().getItemMeta().getDisplayName());
            if (conf.contains("storage")) {
                for (String s : conf.getConfigurationSection("storage").getKeys(false))
                    inventory.setItem(Integer.parseInt(s), conf.getItemStack("storage." + s));
            }
            return clazz.getConstructor(String.class, Location.class, Hologram.class, Timer.class, MachineCraftable.class, Inventory.class).newInstance(file.getName().replace(".yml", ""), conf.getLocation("location"), holo, timer, wantsToCraft, inventory);
        }

        return null;
    }

    public enum MachineType {

        TINKER_TABLE(TinkerTable.item, TinkerTable.class),
        INPUT_NODE(InputNode.item, InputNode.class),
        OUTPUT_NODE(OutputNode.item, OutputNode.class),
        KILN(Kiln.item, Kiln.class),
        POTTERY(Pottery.item, Pottery.class),
        BRONZE_FORGE(BronzeForge.item, BronzeForge.class),
        ;

        private @Getter final ItemStack item;
        private @Getter final Class<? extends Machine> clazz;

        MachineType (ItemStack item, Class<? extends Machine> clazz) {
            this.item = item;
            this.clazz = clazz;
        }

    }

    public enum MachineCraftable {

        // TINKER TABLE
        CUT_WOOD(MachineType.TINKER_TABLE, new ItemBuilder(Material.OAK_LOG).setDisplayName("Cut Wood").addLoreLine("§a32 §7oak-log > §a4x64 §7oak-planks").addLoreLine("§7Time: §b5 sec").build(), Collections.singletonList(new ItemStack(Material.OAK_LOG, 32)), Arrays.asList(new ItemStack(Material.OAK_PLANKS, 64), new ItemStack(Material.OAK_PLANKS, 64), new ItemStack(Material.OAK_PLANKS, 64), new ItemStack(Material.OAK_PLANKS, 64)), 5, Era.ANCIENT),
        CHARCOAL_CHUNK(MachineType.TINKER_TABLE, new ItemBuilder(Material.CHARCOAL).setGlow().setDisplayName("Charcoal chunk").addLoreLine("§a9 §7charcoal > §a1 §7charcoal chunk").addLoreLine("§7Time: §b4 sec").build(), Arrays.asList(new ItemStack(Material.CHARCOAL), new ItemStack(Material.CHARCOAL), new ItemStack(Material.CHARCOAL), new ItemStack(Material.CHARCOAL), new ItemStack(Material.CHARCOAL), new ItemStack(Material.CHARCOAL), new ItemStack(Material.CHARCOAL), new ItemStack(Material.CHARCOAL), new ItemStack(Material.CHARCOAL)), Collections.singletonList(new ItemBuilder(Material.CHARCOAL).setGlow().setDisplayName("§c§lCharcoal chunk").build()), 4, Era.ANCIENT),

        //KILN
        SMELT_IRON(MachineType.KILN, new ItemBuilder(Material.IRON_ORE).setGlow().setDisplayName("Smelt iron").addLoreLine("§a16 §7iron-ores > §a32 §7iron-ingots").addLoreLine("§7Time: §b10 sec").build(), Collections.singletonList(new ItemStack(Material.IRON_ORE, 16)), Collections.singletonList(new ItemStack(Material.IRON_INGOT, 32)), 10, Era.ANCIENT),
        SMELT_GOLD(MachineType.KILN, new ItemBuilder(Material.GOLD_ORE).setGlow().setDisplayName("Smelt gold").addLoreLine("§a16 §7gold-ores > §a32 §7gold-ingots").addLoreLine("§7Time: §b10 sec").build(), Collections.singletonList(new ItemStack(Material.GOLD_ORE, 16)), Collections.singletonList(new ItemStack(Material.GOLD_INGOT, 32)), 10, Era.ANCIENT),
        MAKE_BRONZE_INGOT(MachineType.KILN, ConstIngredients.BRONZE_INGOT.cloneBuilder().addLoreLine("§a3 §7gold-ingot, §a2 iron-ingot & §a1 §7magma-block > §a4 §7Bronze-Ingot").addLoreLine("§7Time: §b10 sec").build(), Arrays.asList(new ItemStack(GOLD_INGOT), new ItemStack(GOLD_INGOT), new ItemStack(GOLD_INGOT), new ItemStack(IRON_INGOT), new ItemStack(IRON_INGOT), new ItemStack(MAGMA_BLOCK)), Collections.singletonList(ConstIngredients.BRONZE_INGOT.cloneBuilder().setAmount(4).build()), 10, Era.ANCIENT),
        
        // BRONZE FORGE
        FORGE_BRONZE_SWORD(MachineType.BRONZE_FORGE, new ItemBuilder(Material.GOLDEN_SWORD).setGlow().setDisplayName("Forge bronze sword").addLoreLine("§a2 §7bronze-ingots & §a1 §7stick > §a1 §7bronze-sword").addLoreLine("§7Time: §b60 sec").build(), Arrays.asList(ConstIngredients.BRONZE_INGOT.cloneBuilder().setAmount(2).build(), new ItemStack(Material.STICK)), Collections.singletonList(new ItemBronzeSword().getItem()), 60, Era.ANCIENT),

        // POTTERY
        MUD_BRICK(MachineType.POTTERY, ConstIngredients.MUD_BRICK.cloneBuilder().addLoreLine("§a1 §7water-bucket & §a4 §7clay > §a1 §7Mud Brick").addLoreLine("§7Time: §b4 sec").build(), Arrays.asList(new ItemStack(Material.WATER_BUCKET), new ItemStack(Material.CLAY, 4)), Collections.singletonList(ConstIngredients.MUD_BRICK.build()), 4, Era.TRIBAL),
        UNFIRED_POT(MachineType.POTTERY, ConstIngredients.UNFIRED_POT.cloneBuilder().addLoreLine("§a4 §7Mud-brick > §a1 §7Unfired Pot").addLoreLine("§7Time: 6 sec").build(), Collections.singletonList(ConstIngredients.MUD_BRICK.cloneBuilder().setAmount(4).build()), Collections.singletonList(ConstIngredients.MUD_BRICK.build()), 6, Era.TRIBAL),
        ;

        public MachineType type;
        public ItemStack icon;
        public List<ItemStack> ingredients;
        public List<ItemStack> results;
        public int seconds;
        public Era eraNeeded;

        MachineCraftable(MachineType type, ItemStack icon, List<ItemStack> ingredients, List<ItemStack> results, int seconds, Era eraNeeded) {
            this.type = type;
            this.icon = icon;
            this.ingredients = ingredients;
            this.results = results;
            this.seconds = seconds;
            this.eraNeeded = eraNeeded;
        }

        public static boolean exists(String name) {
            for (MachineCraftable c : values())
                if (c.name().equalsIgnoreCase(name))
                    return true;
            return false;
        }

    }

    protected void setProgressBarItems(final Inventory inventory, final long current_progress, final long required_progress) {
        final int progress_percentage = (int)(current_progress * 100.0 / required_progress + 0.5);
        int bar_length = 9;
        for (int i = 0; i < bar_length; i++) {
            if (i * progress_percentage < bar_length * required_progress) {
                inventory.setItem(Machine.progressSlots.get(i), new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setNoName().build());
            } else {
                inventory.setItem(Machine.progressSlots.get(i), new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setNoName().build());
            }
        }
    }

}
