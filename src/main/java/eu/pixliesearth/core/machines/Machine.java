package eu.pixliesearth.core.machines;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.util.Map;

@Data
@AllArgsConstructor
public class Machine {

    private Location location;
    private Material center;
    private Map<BlockFace, Material> others;

    public void tick() {}

    public void open(Player player) {}

}
