package eu.pixliesearth.core.objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.Location;

@Data
@AllArgsConstructor
public class SimpleLocation {

    private String world;
    private double x, y, z;
    private float pitch, yaw;

    public SimpleLocation(Location location) {
        this.world = location.getWorld().getName();
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.pitch = location.getPitch();
        this.yaw = location.getYaw();
    }

    public static SimpleLocation fromString(String location) {
        String[] split = location.split(";");
        String world = split[0];
        double x = Double.parseDouble(split[1]);
        double y = Double.parseDouble(split[2]);
        double z = Double.parseDouble(split[3]);
        float pitch = Float.parseFloat(split[4]);
        float yaw = Float.parseFloat(split[5]);
        return new SimpleLocation(world, x, y, z, pitch, yaw);
    }

    public String parseString() {
        return world + ";" + x + ";" + y + ";" + z + ";" + pitch + ";" + yaw;
    }

    public Location toLocation() {
        return new Location(Bukkit.getWorld(world), x, y, z, pitch, yaw);
    }

}
