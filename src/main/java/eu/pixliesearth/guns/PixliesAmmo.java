package eu.pixliesearth.guns;

import eu.pixliesearth.guns.ammo.RifleAmmo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import net.minecraft.server.v1_16_R2.AxisAlignedBB;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Collection;

@Data
@AllArgsConstructor
public class PixliesAmmo {

    private Location location;
    private PixliesGun gun;
    private double damage;

    public GunFireResult trace(Player player) {
        int maxSearchDistance = gun.getMaxRange();

        Block block = player.getTargetBlock(null, maxSearchDistance);
        if(block != null && block.getType().isSolid())
            maxSearchDistance = (int) Math.min(gun.getMaxRange(), block.getLocation().distance(location));

        Collection<LivingEntity> entityList = player.getWorld().getNearbyLivingEntities(location, maxSearchDistance);
        if(entityList.isEmpty())
            return null;

        Vector origin = this.location.toVector();
        for(double distance = 0.0; distance <= maxSearchDistance; distance += gun.getAccuracy()) {
            Vector position = origin.clone().add(this.location.getDirection().clone().multiply(distance));
            Location positionLocation = position.toLocation(player.getWorld());

            positionLocation.getWorld().spawnParticle(Particle.SMOKE_NORMAL, positionLocation, 2);

            AxisAlignedBB locationBoundingBox = new AxisAlignedBB(position.getX(), position.getY(), position.getZ(), position.getX(), position.getY(), position.getZ());
            for(LivingEntity entity : entityList) {
                if(entity == null || entity.isDead() || entity.getEntityId() == player.getEntityId()) {
                    continue;
                }

                AxisAlignedBB entityBoundingBox = ((CraftLivingEntity) entity).getHandle().getBoundingBox();
                if(entityBoundingBox.intersects(locationBoundingBox))
                    return new GunFireResult(entity, (location.distance(entity.getEyeLocation()) <= 0.5), positionLocation);
            }
        }

        return null;
    }

    public PixliesAmmo createNewOne(Location location, PixliesGun gun) {
        return new PixliesAmmo(location, gun, 0);
    }

    public ItemStack getItem() {
        //TODO: Actual AMMO ITEMS
        return new ItemStack(Material.STICK);
    }

    @Getter
    public enum AmmoType {

        RIFLE_AMMO(new RifleAmmo(null, null)),
        ;

        private final PixliesAmmo ammo;

        AmmoType(PixliesAmmo ammo) {
            this.ammo = ammo;
        }

    }

}
