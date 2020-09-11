package eu.pixliesearth.core.machines;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.output.ByteArrayOutputStream;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.customitems.ci.weapons.melee.ItemBronzeSword;
import eu.pixliesearth.core.files.FileBase;
import eu.pixliesearth.core.files.FileDirectory;
import eu.pixliesearth.core.files.JSONFile;
import eu.pixliesearth.core.machines.autocrafters.AutoCrafterMachine;
import eu.pixliesearth.core.machines.autocrafters.FuelableAutoCrafterMachine;
import eu.pixliesearth.core.machines.autocrafters.forge.bronze.BronzeForge;
import eu.pixliesearth.core.machines.autocrafters.kiln.Kiln;
import eu.pixliesearth.core.machines.autocrafters.pottery.Pottery;
import eu.pixliesearth.core.machines.autocrafters.tinkertable.TinkerTable;
import eu.pixliesearth.core.machines.cargo.CargoMachine;
import eu.pixliesearth.core.machines.cargo.InputNode;
import eu.pixliesearth.core.machines.cargo.OutputNode;
import eu.pixliesearth.nations.entities.nation.Era;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.Timer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.SneakyThrows;

import static org.bukkit.Material.*;

@Data
@AllArgsConstructor
public class Machine {

	protected static final @Getter String MachineSavePath = Main.getInstance().getDataFolder().getAbsolutePath()+"/machines/";
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
    
    public static final String serialize(Object o) {
	    try {
	        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
	        BukkitObjectOutputStream out = new BukkitObjectOutputStream(bytesOut);
	        out.writeObject(o);
	        out.flush();
	        out.close();
	        return Base64Coder.encodeLines(bytesOut.toByteArray());
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        return null;
	    }
	}

    public static final Object deserialize(String base64) {
	    try {
	        byte[] data = Base64Coder.decodeLines(base64);
	        ByteArrayInputStream bytesIn = new ByteArrayInputStream(data);
	        BukkitObjectInputStream in = new BukkitObjectInputStream(bytesIn);
	        Object o = in.readObject();
	        in.close();
	        return o;
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        return null;
	    }
	}
    
	
	private static final String locationToSaveableString(Location l) {
		return l.getWorld().getUID().toString().concat(":").concat(Double.toString(l.getX())).concat(":").concat(Double.toString(l.getY())).concat(":").concat(Double.toString(l.getZ())).concat(":").concat(Float.toString(l.getYaw())).concat(":").concat(Float.toString(l.getPitch()));
	}
	
	private static final Location locationFromSaveableString(String s) {
		String[] data = s.split(":");
		return new Location(Bukkit.getWorld(UUID.fromString(data[0])), Double.parseDouble(data[1]), Double.parseDouble(data[2]), Double.parseDouble(data[3]), Float.parseFloat(data[4]), Float.parseFloat(data[5]));
	}
    
    public void save() {
    	JSONFile f;
    	try {
        	f = new JSONFile(getMachineSavePath(), id); // Create or load file
        	f.clearFile();
    	} catch (Exception e) {
    		new FileDirectory(getMachineSavePath()); // Create directory as exception was caused by directory not existing
			f = new JSONFile(getMachineSavePath(), id);
			try {f.clearFile();} catch (IOException e2) {e2.printStackTrace();} // clear old data
    	}
        f.put("id", id);
    	f.put("class", this.getClass().getName());
    	f.put("location", locationToSaveableString(location));
    	f.put("type", type.name());
    	f.put("fuel", "NULL"); // Set to null as its not a fuelable autocrafter
    	f.put("storage", "NULL"); // Set to null as its does not have a storage
    	if (wantsToCraft != null) f.put("wantsToCraft", wantsToCraft.name()); else f.put("wantsToCraft", "NULL");
    	f.put("item", serialize(item));
    	if (timer != null) {JsonObject json = new JsonObject();json.addProperty("expiry", timer.getExpiry());json.addProperty("ended", timer.isEnded());f.put("timer", json);} else f.put("timer", "NULL");
    	JsonObject json = new JsonObject();
    	json.addProperty("location", locationToSaveableString(armorStand.getLocation()));
    	json.addProperty("text", getTitle());
    	f.put("holo", json.toString());
    	for (Map.Entry<String, Object> entry : extras().entrySet()) f.put(entry.getKey(), entry.getValue());
    	f.saveJsonToFile();
        
        /*
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
        conf.save(file); */
    }

    public void remove() {
    	JSONFile file = new JSONFile(getMachineSavePath(), id);
        instance.getUtilLists().machines.remove(location);
        armorStand.delete();
        file.deleteFile();
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
        for (FileBase f : new FileDirectory("plugins/PixliesEarthCore/machines/").getFilesInDirectory()) {
            if (!f.getFile().getName().endsWith(".json")) {
                f.deleteFile();
                continue;
            }
        	JSONFile jf = new JSONFile(f.getFilePath(), f.getFileName());
            //FileConfiguration conf = YamlConfiguration.loadConfiguration(file);
            Machine machine = load(jf);
            if (machine == null) {
                instance.getLogger().warning("Could not load machine " + jf.getFileConstruct());
                continue;
            }
            instance.getUtilLists().machines.put(machine.getLocation(), machine);
        }
    }

    @SneakyThrows
    public static Machine load(JSONFile f) {
    	JsonParser jp = new JsonParser();
    	JsonObject jh = jp.parse(f.get("holo").replaceAll("\\", "")).getAsJsonObject();
    	Timer timer = null;
    	if (!f.get("timer").equalsIgnoreCase("NULL")) {
    		JsonObject jt = jp.parse(f.get("timer").replaceAll("\\", "")).getAsJsonObject();
    		timer = new Timer(jt.get("expiry").getAsLong(), jt.get("ended").getAsBoolean());
    	}
        Hologram holo = HologramsAPI.createHologram(instance, locationFromSaveableString(jh.get("location").getAsString()));
        MachineCraftable wantsToCraft = !f.get("wantsToCraft").equalsIgnoreCase("NULL") ? MachineCraftable.valueOf(f.get("wantsToCraft")) : null;
        MachineType type = MachineType.valueOf(f.get("type"));
        Class<? extends Machine> clazz = type.getClazz();

        if (clazz.isAssignableFrom(FuelableAutoCrafterMachine.class)) {
            holo.appendTextLine(jh.get("text").getAsString());
            return clazz.getConstructor(String.class, Location.class, Hologram.class, Timer.class, MachineCraftable.class, MachineType.class, int.class).newInstance(f.get("id"), locationFromSaveableString(f.get("location")), holo, timer, wantsToCraft, type, Integer.parseInt(f.get("fuel")));
        } else if (clazz.isAssignableFrom(AutoCrafterMachine.class)) {
            holo.appendTextLine(jh.get("text").getAsString());
            return clazz.getConstructor(String.class, Location.class, Hologram.class, Timer.class, MachineCraftable.class, MachineType.class).newInstance(f.get("id"), locationFromSaveableString(f.get("location")), holo, timer, wantsToCraft, type);
        } else if (clazz.isAssignableFrom(CargoMachine.class)) {
            Inventory inventory = Bukkit.createInventory(null, 9 * 6, type.getItem().getItemMeta().getDisplayName());
            if (!f.get("storage").equalsIgnoreCase("NULL")) {
            	JsonObject js = jp.parse(f.get("storage")).getAsJsonObject();
                for (Map.Entry<String, JsonElement> entry : js.entrySet()) {
                	if (!entry.getValue().getAsString().equalsIgnoreCase("EMPTY")) inventory.setItem(Integer.parseInt(entry.getKey()), (ItemStack) deserialize(entry.getValue().getAsString()));
                }
            }
            return clazz.getConstructor(String.class, Location.class, Hologram.class, Timer.class, MachineCraftable.class, Inventory.class, MachineType.class).newInstance(f.get("id"), locationFromSaveableString(f.get("location")), holo, timer, wantsToCraft, inventory, type);
        }
        return null;
    }

    public enum MachineType {

        TINKER_TABLE(TinkerTable.item, TinkerTable.class, AutoCrafterMachine.class),
        INPUT_NODE(InputNode.item, InputNode.class, CargoMachine.class),
        OUTPUT_NODE(OutputNode.item, OutputNode.class, CargoMachine.class),
        KILN(Kiln.item, Kiln.class, FuelableAutoCrafterMachine.class),
        POTTERY(Pottery.item, Pottery.class, AutoCrafterMachine.class),
        BRONZE_FORGE(BronzeForge.item, BronzeForge.class, AutoCrafterMachine.class),
        ;

        private @Getter final ItemStack item;
        private @Getter final Class<? extends Machine> clazz;
        private @Getter final Class<? extends Machine> parent;

        MachineType (ItemStack item, Class<? extends Machine> clazz, Class<? extends Machine> parent) {
            this.item = item;
            this.clazz = clazz;
            this.parent = parent;
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
        MUD_BRICK_KILN(MachineType.KILN, ConstIngredients.MUD_BRICK.cloneBuilder().addLoreLine("§a9 §7clay > §a1 §7Mud Brick").addLoreLine("§7Time: §b4 sec").build(), Collections.singletonList(new ItemStack(CLAY, 9)), Collections.singletonList(ConstIngredients.MUD_BRICK.build()), 4, Era.ANCIENT),
        BRICK_KILN(MachineType.KILN, new ItemBuilder(BRICK).setAmount(4).addLoreLine("§a1 §7Mud Brick > §a4 §7Brick").addLoreLine("§7Time: §b4 sec").build(), Collections.singletonList(ConstIngredients.MUD_BRICK.setAmount(1).build()), Collections.singletonList(new ItemStack(BRICK, 4)), 4, Era.ANCIENT),
        POT_KILN(MachineType.KILN, new ItemBuilder(FLOWER_POT).setAmount(1).addLoreLine("§a1 §7Unfired Pot > §a1 §7Pot").addLoreLine("§7Time: §b3 sec").build(), Collections.singletonList(ConstIngredients.UNFIRED_POT.setAmount(1).build()), Collections.singletonList(new ItemStack(FLOWER_POT)), 3, Era.ANCIENT),
        
        // BRONZE FORGE
        FORGE_BRONZE_SWORD(MachineType.BRONZE_FORGE, new ItemBuilder(Material.GOLDEN_SWORD).setGlow().setDisplayName("Forge bronze sword").addLoreLine("§a2 §7bronze-ingots & §a1 §7stick > §a1 §7bronze-sword").addLoreLine("§7Time: §b60 sec").build(), Arrays.asList(ConstIngredients.BRONZE_INGOT.cloneBuilder().setAmount(2).build(), new ItemStack(Material.STICK)), Collections.singletonList(new ItemBronzeSword().getItem()), 60, Era.ANCIENT),

        // POTTERY
        MUD_BRICK_POTTERY(MachineType.POTTERY, ConstIngredients.MUD_BRICK.cloneBuilder().addLoreLine("§a1 §7water-bucket & §a4 §7clay > §a1 §7Mud Brick").addLoreLine("§7Time: §b4 sec").build(), Arrays.asList(new ItemStack(Material.WATER_BUCKET), new ItemStack(Material.CLAY, 4)), Collections.singletonList(ConstIngredients.MUD_BRICK.build()), 4, Era.TRIBAL),
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
