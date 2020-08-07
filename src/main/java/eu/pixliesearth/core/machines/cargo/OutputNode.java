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
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;

public class OutputNode extends Machine {

    public static final ItemStack item = new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/7e80cebc2eb0a13496b274a908158d6105bdaf1a51d82e5d2417bb55dd20f4da")).setDisplayName("§c§lOutput Node").build();

    private Inventory storage;

    public OutputNode(String id, Location location) {
        super(id, location, MachineType.OUTPUT_NODE, item, null, HologramsAPI.createHologram(instance, holoLocation(location)), null);
        // armorStand.appendTextLine(getTitle());
    }

    public OutputNode(String id, Location location, Hologram armorStand, Timer timer, MachineCraftable wantsToCraft, Inventory storage) {
        super(id, location, MachineType.CARPENTRY_MILL, item, timer, armorStand, wantsToCraft);
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
        for (int i = 0; i < storage.getSize(); i++) {
            if (storage.getItem(i) == null) continue;
            conf.set("storage." + i, storage.getItem(i));
        }
        conf.save(file);
    }

    @Override
    public void remove() {
        for (ItemStack item : storage.getContents())
            location.getWorld().dropItemNaturally(location, item);
    }

    @Override
    public void open(Player player) {
        if (storage == null) storage = Bukkit.createInventory(null, 9 * 6, "§c§lOutput Node");
        player.openInventory(storage);
    }

    public boolean takeItem(ItemStack item) {
        if (!storage.containsAtLeast(item, item.getAmount())) return false;
        Methods.removeRequiredAmount(item, storage);
        return true;
    }

    public boolean addItem(ItemStack item) {
        if (storage.firstEmpty() == -1) return false;
        storage.addItem(item);
        return true;
    }

    @Override
    public String getTitle() {
        return "§c§lOutput Node";
    }

}
