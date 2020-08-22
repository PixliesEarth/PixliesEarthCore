package eu.pixliesearth.vehicles;

import lombok.Data;
import org.bukkit.entity.ArmorStand;
import org.bukkit.util.EulerAngle;

@Data
public class PixliesVehicle {

    private ArmorStand driversSeat;

    public void something() {
        driversSeat.setHeadPose(new EulerAngle(0, driversSeat.getLocation().getYaw() * Math.PI / 180, 0));
        driversSeat.setVisible(false);
        driversSeat.setCollidable(false);
        driversSeat.setSmall(true);
    }

}
