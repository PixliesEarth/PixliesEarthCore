package eu.pixliesearth.nations.entities.settlements;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Energy;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.core.objects.SimpleLocation;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.utils.Methods;
import eu.pixliesearth.utils.Timer;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@Data
@AllArgsConstructor
public class Settlement {

    private String name;
    private String location;

    public void teleport(Player player) {
        Main instance = Main.getInstance();
        Profile profile = instance.getProfile(player.getUniqueId());
        Location location = SimpleLocation.fromString(getLocation()).toLocation();
        profile.teleport(location, name);
    }

}
