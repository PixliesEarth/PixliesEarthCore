package eu.pixliesearth.core.objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Location;

@Data
@AllArgsConstructor
public class Home {

    private String name;
    private double x, y, z;
    private float pitch, yaw;

    public Home(String name, Location location) {
        this.name = name;
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.pitch = location.getPitch();
        this.yaw = location.getYaw();
    }

    public String serialize() {
        return name + ";" + x + ";" + y + ";" + z + ";" + pitch + ";" + yaw;
    }

    public static Home fromString(String serialized) {
        String[] split = serialized.split(";");
        return new Home(split[0], Double.parseDouble(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]), Float.parseFloat(split[4]), Float.parseFloat(split[5]));
    }

}
