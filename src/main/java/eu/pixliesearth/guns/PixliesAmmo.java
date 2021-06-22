package eu.pixliesearth.guns;

import eu.pixliesearth.core.custom.interfaces.Constants;
import eu.pixliesearth.guns.ammo.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import java.util.Collection;

@Data
@AllArgsConstructor
public class PixliesAmmo implements Constants {
	
    private Location location;
    private CustomGun gun;
    private double damage;

    public GunFireResult trace(Player player) {
        int maxSearchDistance = gun.getRange();

        Block block = player.getTargetBlock(null, maxSearchDistance);
        if (block.getType().isSolid())
            maxSearchDistance = (int) Math.min(gun.getRange(), block.getLocation().distance(location));

        Collection<Entity> entityList = player.getWorld().getNearbyEntities(location, maxSearchDistance, maxSearchDistance, maxSearchDistance);
        if (entityList.isEmpty())
            return null;

        final Vector origin = this.location.toVector();
        player.getWorld().playEffect(player.getEyeLocation().add(1, -1, 1), Effect.SMOKE, 2);
        for(double distance = 0.0; distance <= maxSearchDistance; distance += gun.getAccuracy()) {
            Vector position = origin.clone().add(this.location.getDirection().clone().multiply(distance));
            Location positionLocation = position.toLocation(player.getWorld());

            BoundingBox locationBoundingBox = new BoundingBox(position.getX(), position.getY(), position.getZ(), position.getX(), position.getY(), position.getZ());
            for(Entity entity : entityList) {
                if(entity == null || entity.isDead() || entity.getEntityId() == player.getEntityId())
                    continue;

                BoundingBox entityBoundingBox = entity.getBoundingBox();
                if(entityBoundingBox.overlaps(locationBoundingBox))
                    return new GunFireResult((LivingEntity) entity, positionLocation.distance(((LivingEntity) entity).getEyeLocation()) <= 0.5, positionLocation);
            }
        }

        return null;
    }

    public PixliesAmmo createNewOne(Location location, CustomGun gun) {
        return new PixliesAmmo(location, gun, 0);
    }

    public ItemStack getItem() {
        return new ItemStack(Material.STICK);
    }

    @Getter
    public enum AmmoType {

        RIFLE_AMMO(new RifleAmmo(null, null)),
        NINEMM(new NineMMAmmo(null, null)),
        NATO762x51(new Ammo762x51mm(null, null)),
        COBBLESTONE(new CobbleStoneAmmo(null, null)),
        ROCKET(new RocketAmmo(null, null)),
        ;

        private final PixliesAmmo ammo;

        AmmoType(PixliesAmmo ammo) {
            this.ammo = ammo;
        }

        public static boolean contains(String test) {
            for (AmmoType c : values()) {
                if (c.name().equals(test)) {
                    return true;
                }
            }
            return false;
        }

    }

}
