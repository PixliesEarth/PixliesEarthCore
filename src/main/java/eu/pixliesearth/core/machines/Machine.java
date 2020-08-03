package eu.pixliesearth.core.machines;

import eu.pixliesearth.Main;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Data
@AllArgsConstructor
public class Machine {

    protected static final Main instance = Main.getInstance();

    private Location location;
    private ItemStack item;

    public void tick() {}

    public void open(Player player) {}

}
