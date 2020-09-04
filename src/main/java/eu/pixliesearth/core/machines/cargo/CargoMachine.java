package eu.pixliesearth.core.machines.cargo;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import eu.pixliesearth.core.machines.Machine;
import eu.pixliesearth.utils.Methods;
import eu.pixliesearth.utils.Timer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class CargoMachine extends Machine {

    protected Inventory storage;

    public CargoMachine(String id, Location location, Machine.MachineType type, ItemStack item, Timer timer, Hologram armorStand, Machine.MachineCraftable wantsToCraft) {
        super(id, location, type, item, timer, armorStand, wantsToCraft);
    }

    public Map<String, Object> extras() {
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < storage.getSize(); i++) {
            if (storage.getItem(i) == null) continue;
            map.put("storage." + i, storage.getItem(i));
        }
        return map;
    }

    @Override
    public void open(Player player) {
        if (storage == null) storage = Bukkit.createInventory(null, 9 * 6, getTitle());
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
        if (storage == null) return false;
        if (!storage.containsAtLeast(item, item.getAmount())) return false;
        Methods.removeRequiredAmount(item, storage);
        return true;
    }

    public boolean addItem(ItemStack item) {
        if (storage == null) return false;
        if (storage.firstEmpty() == -1) return false;
        storage.addItem(item);
        return true;
    }

}
