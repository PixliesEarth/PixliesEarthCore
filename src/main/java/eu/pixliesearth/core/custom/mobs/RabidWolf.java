package eu.pixliesearth.core.custom.mobs;

import eu.pixliesearth.utils.Methods;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.Wolf;

public class RabidWolf extends EntityWolf {

    public RabidWolf(Location location) {
        super(EntityTypes.WOLF, ((CraftWorld)location.getWorld()).getHandle());

        Wolf craftWolf = (Wolf) this.getBukkitEntity();

        craftWolf.setMaxHealth(100);

        this.setHealth(100);
        this.setCustomName(new ChatMessage("§cRabid Wolf §8[" + Methods.getProgressBar(this.getHealth(), craftWolf.getMaxHealth(), 10, "|", "&c", "&7") + "§8]"));
        this.setCustomNameVisible(true);

        this.targetSelector.a(1, new PathfinderGoalNearestAttackableTarget<>(this, EntityPig.class, true));
        this.targetSelector.a(0, new PathfinderGoalNearestAttackableTarget<>(this, EntityPlayer.class, true));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget<>(this, EntityCow.class, true));
        this.targetSelector.a(3, new PathfinderGoalNearestAttackableTarget<>(this, EntitySheep.class, true));

        this.setPosition(location.getX(), location.getY(), location.getZ());

        craftWolf.setRemoveWhenFarAway(true);

        this.getWorld().addEntity(this);
    }



}
