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
        if (Energy.calculateNeeded(player.getLocation(), location) > profile.getEnergy()) {
            player.sendMessage(Lang.NOT_ENOUGH_ENERGY.get(player));
            return;
        }
        long cooldown = (long) Energy.calculateTime(player.getLocation(), location);
        if (cooldown < 1.0)
            cooldown = (long) 1.0;
        Timer timer = new Timer(cooldown * 1000);
        profile.getTimers().put("Teleport", timer);
        profile.save();
        player.sendMessage(Lang.YOU_WILL_BE_TPD.get(player).replace("%LOCATION%", name).replace("%TIME%", Methods.getTimeAsString(cooldown * 1000, true)));
        Bukkit.getScheduler().runTaskLater(instance, () -> {
            if (profile.getTimers().containsKey("Teleport")) {
                profile.getTimers().remove("Teleport");
                profile.save();
                player.teleport(location);
                Energy.take(instance.getProfile(player.getUniqueId()), Energy.calculateNeeded(player.getLocation(), location));
                player.sendMessage(Lang.TELEPORTATION_SUCESS.get(player).replace("%LOCATION%", name));
            }
        }, cooldown * 20);
    }

}
