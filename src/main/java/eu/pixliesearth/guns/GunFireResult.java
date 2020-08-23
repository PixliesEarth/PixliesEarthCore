package eu.pixliesearth.guns;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

@Data
@AllArgsConstructor
public class GunFireResult {

    private LivingEntity entity;
    private boolean headshot;
    private Location positionLocation;

}