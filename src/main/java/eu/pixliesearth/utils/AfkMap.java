package eu.pixliesearth.utils;

import eu.pixliesearth.core.objects.SimpleLocation;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Location;

@Data
@AllArgsConstructor
public class AfkMap {

    private Location location;
    private int minutes;

}
