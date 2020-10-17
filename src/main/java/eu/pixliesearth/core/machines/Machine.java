package eu.pixliesearth.core.machines;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.google.gson.JsonObject;
import eu.pixliesearth.Main;
import eu.pixliesearth.core.custom.CustomItem;
import eu.pixliesearth.core.files.FileBase;
import eu.pixliesearth.core.files.FileDirectory;
import eu.pixliesearth.core.files.JSONFile;
import eu.pixliesearth.core.machines.autocrafters.AutoCrafterMachine;
import eu.pixliesearth.core.machines.autocrafters.FuelableAutoCrafterMachine;
import eu.pixliesearth.core.machines.autocrafters.compressor.Compressor;
import eu.pixliesearth.core.machines.autocrafters.forge.bronze.BronzeForge;
import eu.pixliesearth.core.machines.autocrafters.kiln.Forge;
import eu.pixliesearth.core.machines.autocrafters.machinecrafter.MachineCrafter;
import eu.pixliesearth.core.machines.autocrafters.pottery.Pottery;
import eu.pixliesearth.core.machines.autocrafters.tinkertable.TinkerTable;
import eu.pixliesearth.core.machines.cargo.CargoMachine;
import eu.pixliesearth.core.machines.cargo.InputNode;
import eu.pixliesearth.core.machines.cargo.OutputNode;
import eu.pixliesearth.core.machines.workbench.FarmingWorkbench;
import eu.pixliesearth.nations.entities.nation.Era;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.Timer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.SneakyThrows;
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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

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
    	JsonObject o = new JsonObject();
    	o.addProperty("id", id);
    	o.addProperty("class", this.getClass().getName());
    	o.addProperty("location", locationToSaveableString(location));
    	o.addProperty("type", type.name());
    	o.addProperty("fuel", "NULL"); // Set to null as its not a fuelable autocrafter
    	o.addProperty("energy", "NULL"); // Set to null as its not a energy machine
    	o.addProperty("storage", "NULL"); // Set to null as its does not have a storage
    	if (wantsToCraft != null) o.addProperty("wantsToCraft", wantsToCraft.name()); else o.addProperty("wantsToCraft", "NULL");
    	o.addProperty("item", serialize(item));
    	// if (timer != null) {JsonObject json = new JsonObject();json.addProperty("expiry", timer.getExpiry());json.addProperty("ended", timer.isEnded());o.addProperty("timer", json.toString());} else o.addProperty("timer", "NULL");
    	if (timer == null) o.addProperty("timer-expiry", "NULL"); else o.addProperty("timer-expiry", timer.getExpiry());
    	if (timer == null) o.addProperty("timer-ended", "NULL"); else o.addProperty("timer-ended", timer.isEnded());

    	o.addProperty("holo-text", getTitle());
    	o.addProperty("holo-location", locationToSaveableString(armorStand.getLocation()));
    	for (Map.Entry<String, Object> entry : extras().entrySet()) f.put(entry.getKey(), entry.getValue());
    	f.saveJsonToFile(o);
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
            try {
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
            } catch (Exception e) {
                System.out.println("Could not load machine " + f.getFileName());
                e.printStackTrace();
            }
        }
    }

    @SneakyThrows
    public static Machine load(JSONFile f) {
    	JsonObject o = f.toJsonOject();
    	Timer timer = null;
    	if (!o.get("timer-expiry").getAsString().equalsIgnoreCase("NULL"))
    	    timer = new Timer(o.get("timer-expiry").getAsLong(), o.get("timer-ended").getAsBoolean());
        Hologram holo = HologramsAPI.createHologram(instance, locationFromSaveableString(o.get("holo-location").getAsString()));
        MachineCraftable wantsToCraft = !o.get("wantsToCraft").getAsString().equalsIgnoreCase("NULL") ? MachineCraftable.valueOf(o.get("wantsToCraft").getAsString()) : null;
        MachineType type = MachineType.valueOf(o.get("type").getAsString());
        // Class<? extends Machine> clazz = type.getClazz();

        Inventory inventory;
        switch (type) {

            case TINKER_TABLE:
                holo.appendTextLine(o.get("holo-text").getAsString());
                return new TinkerTable(o.get("id").getAsString(), locationFromSaveableString(o.get("location").getAsString()), holo, timer, wantsToCraft, type);

            case FORGE:
                holo.appendTextLine(o.get("holo-text").getAsString());
                return new Forge(o.get("id").getAsString(), locationFromSaveableString(o.get("location").getAsString()), holo, timer, wantsToCraft, type, o.get("fuel").getAsInt());

            case POTTERY:
                holo.appendTextLine(o.get("holo-text").getAsString());
                return new Pottery(o.get("id").getAsString(), locationFromSaveableString(o.get("location").getAsString()), holo, timer, wantsToCraft, type);

            case BRONZE_FORGE:
                holo.appendTextLine(o.get("holo-text").getAsString());
                return new BronzeForge(o.get("id").getAsString(), locationFromSaveableString(o.get("location").getAsString()), holo, timer, wantsToCraft, type);

            case MACHINE_CRAFTER:
                holo.appendTextLine(o.get("holo-text").getAsString());
                return new MachineCrafter(o.get("id").getAsString(), locationFromSaveableString(o.get("location").getAsString()), holo, timer, wantsToCraft, type);

            case FARMING_WORKBENCH:
                holo.appendTextLine(o.get("holo-text").getAsString());
                return new FarmingWorkbench(o.get("id").getAsString(), locationFromSaveableString(o.get("location").getAsString()), holo, timer, wantsToCraft, type);

            case INPUT_NODE:
                inventory = Bukkit.createInventory(null, 9 * 6, type.getItem().getItemMeta().getDisplayName());
                if (!o.get("storage").getAsString().equalsIgnoreCase("NULL")) {
                    JsonObject js = o.get("storage").getAsJsonObject();
                    for (int i = 0; i < 9 * 6; i++)
                        inventory.setItem(i, (ItemStack) deserialize(js.get(Integer.toString(i)).getAsString()));
                }
                return new InputNode(o.get("id").getAsString(), locationFromSaveableString(o.get("location").getAsString()), holo, timer, wantsToCraft, inventory);
            case OUTPUT_NODE:
                inventory = Bukkit.createInventory(null, 9 * 6, type.getItem().getItemMeta().getDisplayName());
                if (!o.get("storage").getAsString().equalsIgnoreCase("NULL")) {
                    JsonObject js = o.get("storage").getAsJsonObject();
                    for (int i = 0; i < 9 * 6; i++)
                        inventory.setItem(i, (ItemStack) deserialize(js.get(Integer.toString(i)).getAsString()));
                }
                return new OutputNode(o.get("id").getAsString(), locationFromSaveableString(o.get("location").getAsString()), holo, timer, wantsToCraft, inventory);

        }
        return null;
    }

    public enum MachineType {

        TINKER_TABLE(TinkerTable.item, TinkerTable.class, AutoCrafterMachine.class, MachineCrafter.item, new ItemStack(Material.OAK_PLANKS, 16), new ItemStack(Material.IRON_INGOT, 4)),
        INPUT_NODE(InputNode.item, InputNode.class, CargoMachine.class, MachineCrafter.item),
        OUTPUT_NODE(OutputNode.item, OutputNode.class, CargoMachine.class, MachineCrafter.item),
        FORGE(Forge.item, Forge.class, FuelableAutoCrafterMachine.class, MachineCrafter.item, new ItemStack(Material.SMOOTH_STONE, 16), new ItemStack(Material.IRON_BLOCK, 2)),
        POTTERY(Pottery.item, Pottery.class, AutoCrafterMachine.class, MachineCrafter.item, new ItemStack(Material.FLOWER_POT, 4), new ItemStack(Material.IRON_INGOT, 2)),
        BRONZE_FORGE(BronzeForge.item, BronzeForge.class, AutoCrafterMachine.class, MachineCrafter.item, new ItemBuilder(CustomItemUtil.getItemStackFromUUID("Pixlies:Bronze_Ingot")).setAmount(4).build(), new ItemStack(Material.STONE_BRICKS, 16), new ItemStack(Material.LAVA_BUCKET)),
        MACHINE_CRAFTER(MachineCrafter.item, MachineCrafter.class, AutoCrafterMachine.class, new ItemStack(CRAFTING_TABLE)),
        COMPRESSOR(Compressor.item, Compressor.class, FuelableAutoCrafterMachine.class, MachineCrafter.item, new ItemBuilder(CustomItemUtil.getItemStackFromUUID("Pixlies:Copper_Wire")).setAmount(4).build(), new ItemBuilder(Material.PISTON).setAmount(4).build(), new ItemBuilder(Material.REDSTONE_BLOCK).setAmount(4).build()),
        FARMING_WORKBENCH(FarmingWorkbench.item, FarmingWorkbench.class, AutoCrafterMachine.class, MachineCrafter.item, new ItemStack(OAK_PLANKS, 4), new ItemStack(WHEAT_SEEDS), new ItemStack(IRON_INGOT, 2)),
        ;

        private @Getter final ItemStack item;
        private @Getter final Class<? extends Machine> clazz;
        private @Getter final Class<? extends Machine> parent;
        private @Getter final ItemStack whereToCraft;
        private @Getter final ItemStack[] recipe;

        MachineType (ItemStack item, Class<? extends Machine> clazz, Class<? extends Machine> parent, ItemStack whereToCraft, ItemStack... recipe) {
            this.item = item;
            this.clazz = clazz;
            this.parent = parent;
            this.whereToCraft = whereToCraft;
            this.recipe = recipe;
        }

    }

    public enum MachineCraftable {

        // TINKER TABLE
        CUT_WOOD(MachineType.TINKER_TABLE, new ItemBuilder(Material.OAK_LOG).setDisplayName("Cut Wood").build(), Collections.singletonList(new ItemStack(Material.OAK_LOG, 32)), Arrays.asList(new ItemStack(Material.OAK_PLANKS, 64), new ItemStack(Material.OAK_PLANKS, 64), new ItemStack(Material.OAK_PLANKS, 64), new ItemStack(Material.OAK_PLANKS, 64)), 5, Era.ANCIENT),
        CHARCOAL_CHUNK(MachineType.TINKER_TABLE, new ItemBuilder(Material.CHARCOAL).setGlow().setDisplayName("Charcoal chunk").build(), Arrays.asList(new ItemStack(Material.CHARCOAL), new ItemStack(Material.CHARCOAL), new ItemStack(Material.CHARCOAL), new ItemStack(Material.CHARCOAL), new ItemStack(Material.CHARCOAL), new ItemStack(Material.CHARCOAL), new ItemStack(Material.CHARCOAL), new ItemStack(Material.CHARCOAL), new ItemStack(Material.CHARCOAL)), Collections.singletonList(new ItemBuilder(Material.CHARCOAL).setGlow().setDisplayName("§c§lCharcoal chunk").build()), 4, Era.ANCIENT),

        // FORGE
        SMELT_IRON(MachineType.FORGE, new ItemBuilder(Material.IRON_ORE).setGlow().setDisplayName("Smelt iron").build(), Collections.singletonList(new ItemStack(Material.IRON_ORE, 16)), Collections.singletonList(new ItemStack(Material.IRON_INGOT, 32)), 10, Era.ANCIENT),
        SMELT_GOLD(MachineType.FORGE, new ItemBuilder(Material.GOLD_ORE).setGlow().setDisplayName("Smelt gold").build(), Collections.singletonList(new ItemStack(Material.GOLD_ORE, 16)), Collections.singletonList(new ItemStack(Material.GOLD_INGOT, 32)), 10, Era.ANCIENT),
        MAKE_BRONZE_INGOT(MachineType.FORGE, CustomItemUtil.getItemStackFromUUID("Pixlies:Bronze_Ingot"), Arrays.asList(CustomItemUtil.getItemStackFromUUID("Pixlies:Tin_Dust"), CustomItemUtil.getItemStackFromUUID("Pixlies:Copper_Dust")), Collections.singletonList(new ItemBuilder(CustomItemUtil.getItemStackFromUUID("Pixlies:Bronze_Ingot")).setAmount(4).build()), 10, Era.ANCIENT),
        MUD_BRICK_KILN(MachineType.FORGE, CustomItemUtil.getItemStackFromUUID("Pixlies:Mud_Brick"), Collections.singletonList(new ItemStack(CLAY, 9)), Collections.singletonList(CustomItemUtil.getItemStackFromUUID("Pixlies:Mud_Brick")), 4, Era.ANCIENT),
        BRICK_KILN(MachineType.FORGE, new ItemStack(BRICK, 4), Collections.singletonList(new ItemBuilder(CustomItemUtil.getItemStackFromUUID("Pixlies:Mud_Brick")).setAmount(1).build()), Collections.singletonList(new ItemStack(BRICK, 4)), 4, Era.ANCIENT),
        POT_KILN(MachineType.FORGE, new ItemStack(FLOWER_POT), Collections.singletonList(new ItemBuilder(CustomItemUtil.getItemStackFromUUID("Pixlies:Unfired_Pot")).setAmount(1).build()), Collections.singletonList(new ItemStack(FLOWER_POT)), 3, Era.ANCIENT),
        ARSENIC_BRONZE_INGOT(MachineType.FORGE, CustomItemUtil.getItemStackFromUUID("Pixlies:Arsenic_Bronze_Ingot"), Arrays.asList(CustomItemUtil.getItemStackFromUUID("Pixlies:Arsenic_Ingot"), CustomItemUtil.getItemStackFromUUID("Pixlies:Copper_Dust")), Collections.singletonList(CustomItemUtil.getItemStackFromUUID("Pixlies:Arsenic_Bronze_Ingot")), 4, Era.ANCIENT),
        MAKE_STEEL_INGOT(MachineType.FORGE, CustomItemUtil.getItemStackFromUUID("Pixlies:Steel_Ingot"), Arrays.asList(CustomItemUtil.getItemStackFromUUID("Pixlies:Iron_Dust").asQuantity(2), new ItemStack(COAL)), Collections.singletonList(CustomItemUtil.getItemStackFromUUID("Pixlies:Steel_Ingot")), 4, Era.MEDIEVAL),
        FIRED_BRICK(MachineType.FORGE, CustomItemUtil.getItemStackFromUUID("Pixlies:Fired_Brick"), Collections.singletonList(CustomItemUtil.getItemStackFromUUID("Pixlies:Mud_Brick")), Collections.singletonList(CustomItemUtil.getItemStackFromUUID("Pixlies:Fired_Brick")), 8, Era.ANCIENT),
        COPPER_INGOT(MachineType.FORGE, CustomItemUtil.getItemStackFromUUID("Pixlies:Copper_Ingot"), Collections.singletonList(CustomItemUtil.getItemStackFromUUID("Pixlies:Copper_Dust")), Collections.singletonList(CustomItemUtil.getItemStackFromUUID("Pixlies:Copper_Ingot")), 8, Era.ANCIENT),
        TIN_INGOT(MachineType.FORGE, CustomItemUtil.getItemStackFromUUID("Pixlies:Tin_Ingot"), Collections.singletonList(CustomItemUtil.getItemStackFromUUID("Pixlies:Tin_Dust")), Collections.singletonList(CustomItemUtil.getItemStackFromUUID("Pixlies:Tin_Ingot")), 8, Era.ANCIENT),
        ARSENIC_BRONZE_CHESTPLATE(MachineType.FORGE, CustomItemUtil.getItemStackFromUUID("Pixlies:Arsenic_Bronze_Chestplate"), Collections.singletonList(CustomItemUtil.getItemStackFromUUID("Pixlies:Arsenic_Bronze_Ingot").asQuantity(8)), Collections.singletonList(CustomItemUtil.getItemStackFromUUID("Pixlies:Arsenic_Bronze_Chestplate")), 8, Era.ANCIENT),
        ARSENIC_BRONZE_LEGGINGS(MachineType.FORGE, CustomItemUtil.getItemStackFromUUID("Pixlies:Arsenic_Bronze_Leggings"), Collections.singletonList(CustomItemUtil.getItemStackFromUUID("Pixlies:Arsenic_Bronze_Ingot").asQuantity(7)), Collections.singletonList(CustomItemUtil.getItemStackFromUUID("Pixlies:Arsenic_Bronze_Leggings")), 8, Era.ANCIENT),
        ARSENIC_BRONZE_HELMET(MachineType.FORGE, CustomItemUtil.getItemStackFromUUID("Pixlies:Arsenic_Bronze_Helmet"), Collections.singletonList(CustomItemUtil.getItemStackFromUUID("Pixlies:Arsenic_Bronze_Ingot").asQuantity(5)), Collections.singletonList(CustomItemUtil.getItemStackFromUUID("Pixlies:Arsenic_Bronze_Helmet")), 8, Era.ANCIENT),
        ARSENIC_BRONZE_BOOTS(MachineType.FORGE, CustomItemUtil.getItemStackFromUUID("Pixlies:Arsenic_Bronze_Boots"), Collections.singletonList(CustomItemUtil.getItemStackFromUUID("Pixlies:Arsenic_Bronze_Ingot").asQuantity(4)), Collections.singletonList(CustomItemUtil.getItemStackFromUUID("Pixlies:Arsenic_Bronze_Boots")), 8, Era.ANCIENT),

        // BRONZE FORGE
        FORGE_BRONZE_SWORD(MachineType.BRONZE_FORGE, new ItemBuilder(Material.GOLDEN_SWORD).setGlow().setDisplayName("Forge bronze sword").build(), Arrays.asList(CustomItemUtil.getItemStackFromUUID("Pixlies:Bronze_Ingot").asQuantity(2), new ItemStack(Material.STICK)), Collections.singletonList(CustomItemUtil.getItemStackFromUUID("Pixlies:Bronze_Sword")), 60, Era.ANCIENT),
        FORGE_BRONZE_HELMET(MachineType.BRONZE_FORGE, new ItemBuilder(Material.GOLDEN_HELMET).setGlow().setDisplayName("Forge bronze helmet").build(), Collections.singletonList(new ItemBuilder(CustomItemUtil.getItemStackFromUUID("Pixlies:Bronze_Ingot")).setAmount(5).build()), Collections.singletonList(CustomItemUtil.getItemStackFromUUID("Pixlies:Bronze_Helmet")), 60, Era.ANCIENT),
        FORGE_BRONZE_CHESTPLATE(MachineType.BRONZE_FORGE, new ItemBuilder(Material.GOLDEN_CHESTPLATE).setGlow().setDisplayName("Forge bronze chestplate").build(), Collections.singletonList(new ItemBuilder(CustomItemUtil.getItemStackFromUUID("Pixlies:Bronze_Ingot")).setAmount(8).build()), Collections.singletonList(CustomItemUtil.getItemStackFromUUID("Pixlies:Bronze_Chestplate")), 60, Era.ANCIENT),
        FORGE_BRONZE_LEGGINGS(MachineType.BRONZE_FORGE, new ItemBuilder(Material.GOLDEN_LEGGINGS).setGlow().setDisplayName("Forge bronze leggings").build(), Collections.singletonList(new ItemBuilder(CustomItemUtil.getItemStackFromUUID("Pixlies:Bronze_Ingot")).setAmount(7).build()), Collections.singletonList(CustomItemUtil.getItemStackFromUUID("Pixlies:Bronze_Leggings")), 60, Era.ANCIENT),
        FORGE_BRONZE_BOOTS(MachineType.BRONZE_FORGE, new ItemBuilder(Material.GOLDEN_BOOTS).setGlow().setDisplayName("Forge bronze boots").build(), Collections.singletonList(new ItemBuilder(CustomItemUtil.getItemStackFromUUID("Pixlies:Bronze_Ingot")).setAmount(4).build()), Collections.singletonList(CustomItemUtil.getItemStackFromUUID("Pixlies:Bronze_Boots")), 60, Era.ANCIENT),
        
        
        // POTTERY
        MUD_BRICK_POTTERY(MachineType.POTTERY, CustomItemUtil.getItemStackFromUUID("Pixlies:Mud_Brick"), Arrays.asList(new ItemStack(Material.WATER_BUCKET), new ItemStack(Material.CLAY, 4)), Collections.singletonList(CustomItemUtil.getItemStackFromUUID("Pixlies:Mud_Brick")), 4, Era.TRIBAL),
        UNFIRED_POT(MachineType.POTTERY, new ItemBuilder(CustomItemUtil.getItemStackFromUUID("Pixlies:Unfired_Pot")).setAmount(4).build(), Collections.singletonList(new ItemBuilder(CustomItemUtil.getItemStackFromUUID("Pixlies:Mud_Brick")).setAmount(4).build()), Collections.singletonList(CustomItemUtil.getItemStackFromUUID("Pixlies:Mud_Brick")), 6, Era.TRIBAL),
        TABLET_POTTERY(MachineType.POTTERY, CustomItemUtil.getItemStackFromUUID("Pixlies:Tablet"), Collections.singletonList(new ItemBuilder(CustomItemUtil.getItemStackFromUUID("Pixlies:Mud_Brick")).setAmount(4).build()), Collections.singletonList(CustomItemUtil.getItemStackFromUUID("Pixlies:Tablet")), 6, Era.TRIBAL),
        CLAY_DAGGER_POTTERY(MachineType.POTTERY, CustomItemUtil.getItemStackFromUUID("Pixlies:Clay_Dagger"), Arrays.asList(new ItemStack(CLAY_BALL, 2), new ItemStack(STICK)), Collections.singletonList(CustomItemUtil.getItemStackFromUUID("Pixlies:Clay_Dagger")), 3, Era.TRIBAL),

        // MACHINE CRAFTER
        TINKER_TABLE(MachineType.MACHINE_CRAFTER, new ItemBuilder(MachineType.TINKER_TABLE.getItem()).build(), Arrays.asList(new ItemStack(Material.OAK_PLANKS, 16), new ItemStack(Material.IRON_INGOT, 4)), Collections.singletonList(MachineType.TINKER_TABLE.getItem()), 20, Era.TRIBAL),
    	FORGE(MachineType.MACHINE_CRAFTER, new ItemBuilder(MachineType.FORGE.getItem()).build(), Arrays.asList(new ItemStack(IRON_INGOT, 4), new ItemStack(Material.IRON_BLOCK)), Collections.singletonList(MachineType.FORGE.getItem()), 20, Era.TRIBAL),
    	POTTERY(MachineType.MACHINE_CRAFTER, new ItemBuilder(MachineType.POTTERY.getItem()).build(), Arrays.asList(new ItemStack(Material.FLOWER_POT, 4), new ItemStack(Material.IRON_INGOT, 2)), Collections.singletonList(MachineType.POTTERY.getItem()), 20, Era.TRIBAL),
    	BRONZE_FORGE(MachineType.MACHINE_CRAFTER, new ItemBuilder(MachineType.BRONZE_FORGE.getItem()).build(), Arrays.asList(new ItemBuilder(CustomItemUtil.getItemStackFromUUID("Pixlies:Bronze_Ingot")).setAmount(4).build(), new ItemStack(Material.STONE_BRICKS, 16), new ItemStack(Material.LAVA_BUCKET)), Collections.singletonList(MachineType.BRONZE_FORGE.getItem()), 30, Era.TRIBAL),
    	COMPRESSOR(MachineType.MACHINE_CRAFTER, new ItemBuilder(MachineType.COMPRESSOR.getItem()).build(), Arrays.asList(new ItemBuilder(CustomItemUtil.getItemStackFromUUID("Pixlies:Copper_Wire")).setAmount(4).build(), new ItemBuilder(Material.PISTON).setAmount(4).build(), new ItemBuilder(Material.REDSTONE_BLOCK).setAmount(4).build()), Collections.singletonList(MachineType.COMPRESSOR.getItem()), 30, Era.VICTORIAN),
    	
    	// COMPRESSOR
    	COMPRESSOR_TIN_BLOCK(MachineType.COMPRESSOR, CustomItemUtil.getItemStackFromUUID("Pixlies:Tin_Block"), Arrays.asList(new ItemBuilder(CustomItemUtil.getItemStackFromUUID("Pixlies:Tin_Ingot")).setAmount(9).build()), Collections.singletonList(CustomItemUtil.getItemStackFromUUID("Pixlies:Tin_Block")), 3, Era.VICTORIAN),
    	
    	
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
