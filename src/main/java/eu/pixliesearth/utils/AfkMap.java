package eu.pixliesearth.utils;

import eu.pixliesearth.core.objects.SimpleLocation;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AfkMap {

    private SimpleLocation location;
    private int minutes;

}
