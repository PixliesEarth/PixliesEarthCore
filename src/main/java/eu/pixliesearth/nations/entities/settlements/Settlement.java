package eu.pixliesearth.nations.entities.settlements;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.core.objects.SimpleLocation;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@Data
@AllArgsConstructor
public class Settlement {

    private String name;
    private String location;
    private boolean capital;

    public void teleport(Player player) {
        Main instance = Main.getInstance();
        Profile profile = instance.getProfile(player.getUniqueId());
        Location location = SimpleLocation.fromString(getLocation()).toLocation();
        profile.teleport(location, name);
    }

}
