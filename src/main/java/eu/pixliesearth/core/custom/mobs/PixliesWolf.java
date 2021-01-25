package eu.pixliesearth.core.custom.mobs;

import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.Wolf;

public class PixliesWolf extends EntityWolf {

    public PixliesWolf(Location location) {
        super(EntityTypes.WOLF, ((CraftWorld)location.getWorld()).getHandle());

        Wolf craftWolf = (Wolf) this.getBukkitEntity();

        craftWolf.setMaxHealth(50);

        this.setHealth(50);
        this.setCustomName(new ChatMessage("§cHostile Wolf"));
        this.setCustomNameVisible(true);

        this.targetSelector.a(0, new PathfinderGoalNearestAttackableTarget<>(this, EntityPig.class, false));
        this.targetSelector.a(0, new PathfinderGoalNearestAttackableTarget<>(this, EntityPlayer.class, false));
        this.targetSelector.a(0, new PathfinderGoalNearestAttackableTarget<>(this, EntityCow.class, false));
        this.targetSelector.a(0, new PathfinderGoalNearestAttackableTarget<>(this, EntitySheep.class, false));

        this.setPosition(location.getX(), location.getY(), location.getZ());

        this.getWorld().addEntity(this);
    }



}
