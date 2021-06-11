package eu.pixliesearth.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Location;

@Data
@AllArgsConstructor
public class FastConf {

    private int maxClaimSize;
    private Location spawnLocation;

}
