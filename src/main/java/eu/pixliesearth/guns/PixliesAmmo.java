package eu.pixliesearth.guns;

import java.util.Collection;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import eu.pixliesearth.core.custom.interfaces.Constants;
import eu.pixliesearth.guns.ammo.Ammo762x51mm;
import eu.pixliesearth.guns.ammo.CobbleStoneAmmo;
import eu.pixliesearth.guns.ammo.NineMMAmmo;
import eu.pixliesearth.guns.ammo.RifleAmmo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import net.minecraft.server.v1_16_R3.AxisAlignedBB;

@Data
@AllArgsConstructor
public class PixliesAmmo implements Constants {
	
    private Location location;
    private PixliesGun gun;
    private double damage;

    public GunFireResult trace(Player player) {
        int maxSearchDistance = gun.getMaxRange();

        Block block = player.getTargetBlock(null, maxSearchDistance);
        if (block.getType().isSolid())
            maxSearchDistance = (int) Math.min(gun.getMaxRange(), block.getLocation().distance(location));

        Collection<LivingEntity> entityList = player.getWorld().getNearbyLivingEntities(location, maxSearchDistance);
        if (entityList.isEmpty())
            return null;

        final Vector origin = this.location.toVector();
        player.getWorld().playEffect(player.getEyeLocation().add(1, -1, 1), Effect.SMOKE, 2);
        for(double distance = 0.0; distance <= maxSearchDistance; distance += gun.getAccuracy()) {
            Vector position = origin.clone().add(this.location.getDirection().clone().multiply(distance));
            Location positionLocation = position.toLocation(player.getWorld());

            AxisAlignedBB locationBoundingBox = new AxisAlignedBB(position.getX(), position.getY(), position.getZ(), position.getX(), position.getY(), position.getZ());
            for(LivingEntity entity : entityList) {
                if(entity == null || entity.isDead() || entity.getEntityId() == player.getEntityId())
                    continue;

                AxisAlignedBB entityBoundingBox = ((CraftLivingEntity) entity).getHandle().getBoundingBox();
                if(entityBoundingBox.intersects(locationBoundingBox))
                    return new GunFireResult(entity, positionLocation.distance(entity.getEyeLocation()) <= 0.5, positionLocation);
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
        NINEMM(new NineMMAmmo(null, null)),
        NATO762x51(new Ammo762x51mm(null, null)),
        COBBLESTONE(new CobbleStoneAmmo(null, null)),
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
