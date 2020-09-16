package eu.pixliesearth.core.objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

@Data
@AllArgsConstructor
public class Home {

    private String name;
    private World world;
    private double x, y, z;
    private float pitch, yaw;

    public Home(String name, Location location) {
        this.name = name;
        this.world = location.getWorld();
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.pitch = location.getPitch();
        this.yaw = location.getYaw();
    }

    public String serialize() {
        return name + ";" + world.getName() + ";" + x + ";" + y + ";" + z + ";" + pitch + ";" + yaw;
    }

    public Location asLocation() {
        return new Location(world, x, y, z, pitch, yaw);
    }

    public static Home fromString(String serialized) {
        String[] split = serialized.split(";");
        return new Home(split[0], Bukkit.getWorld(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]), Double.parseDouble(split[4]), Float.parseFloat(split[5]), Float.parseFloat(split[6]));
    }

    public static Home getByName(Profile profile, String name) {
        for (String s : profile.getHomes()) {
            Home home = fromString(s);
            if (home.getName().equalsIgnoreCase(name))
                return home;
        }
        return null;
    }

}
