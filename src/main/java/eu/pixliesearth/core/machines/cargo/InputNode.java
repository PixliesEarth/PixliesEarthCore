package eu.pixliesearth.core.machines.cargo;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import eu.pixliesearth.core.machines.Machine;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.Methods;
import eu.pixliesearth.utils.SkullCreator;
import eu.pixliesearth.utils.Timer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Hopper;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;

public class InputNode extends Machine {

    public static final ItemStack item = new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/31885b918104e6aa62b6ec9b8e82028dd9da7d2acdfe9c2155edd392285b57e1")).setDisplayName("§b§lInput Node").build();

    private Inventory storage;

    public InputNode(String id, Location location) {
        super(id, location, MachineType.INPUT_NODE, item, null, HologramsAPI.createHologram(instance, holoLocation(location)), null);
        // armorStand.appendTextLine(getTitle());
    }

    public InputNode(String id, Location location, Hologram armorStand, Timer timer, MachineCraftable wantsToCraft, Inventory storage) {
        super(id, location, MachineType.INPUT_NODE, item, timer, armorStand, wantsToCraft);
        this.storage = storage;
    }

    @Override
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
        conf.set("storage", null);
        for (int i = 0; i < storage.getSize(); i++) {
            if (storage.getItem(i) == null) continue;
            conf.set("storage." + i, storage.getItem(i));
        }
        conf.save(file);
    }

    @Override
    public void open(Player player) {
        if (storage == null) storage = Bukkit.createInventory(null, 9 * 6, "§b§lInput Node");
        player.openInventory(storage);
    }

    @Override
    public void remove() {
        File file = new File("plugins/PixliesEarthCore/machines/" + id + ".yml");
        instance.getUtilLists().machines.remove(location);
        armorStand.delete();
        if (!file.exists()) return;
        file.delete();
        for (ItemStack item : storage.getContents())
            if (item != null)
                location.getWorld().dropItemNaturally(location, item);
    }

    public boolean takeItem(ItemStack item) {
        if (!storage.containsAtLeast(item, item.getAmount())) return false;
        Methods.removeRequiredAmount(item, storage);
        return true;
    }

    public void addItem(ItemStack item) {
        storage.addItem(item);
    }

    @Override
    public String getTitle() {
        return "§b§lInput Node";
    }

    @Override
    public void tick() {
        try {
            int radius = 1;
            final Block block = location.getBlock();
            boolean stop = false;
            for (int x = -(radius); x <= radius; x++) {
                for (int z = -(radius); z <= radius; z++) {
                    final Block relative = block.getRelative(x, 0, z);
                    if (relative.getState() instanceof Chest) {
                        Chest chest = (Chest) relative.getState();
                        for (ItemStack item : chest.getInventory().getContents()) {
                            if (item == null) continue;
                            if (storage.firstEmpty() == -1) break;
                            storage.addItem(item);
                            Methods.removeRequiredAmount(item, chest.getInventory());
                            stop = true;
                            break;
                        }
                        if (stop) break;
                    }
                    if (relative.getState() instanceof Hopper) {
                        Hopper hopper = (Hopper) relative.getState();
                        for (ItemStack item : hopper.getInventory().getContents()) {
                            if (item == null) continue;
                            if (storage.firstEmpty() == -1) break;
                            storage.addItem(item);
                            Methods.removeRequiredAmount(item, hopper.getInventory());
                            stop = true;
                            break;
                        }
                        if (stop) break;
                    }
                    if (stop) break;
                }
                if (stop) break;
            }
        } catch (Exception e ) {}
    }

}
