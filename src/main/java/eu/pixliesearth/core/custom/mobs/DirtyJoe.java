package eu.pixliesearth.core.custom.mobs;

import eu.pixliesearth.utils.Methods;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.Villager;

public class DirtyJoe extends EntityVillager {

    public DirtyJoe(Location location) {
        super(EntityTypes.VILLAGER, ((CraftWorld)location.getWorld()).getHandle());

        Villager craftVillager = (Villager) this.getBukkitEntity();

        craftVillager.setMaxHealth(20);

        this.setHealth(20);

        this.setCustomName(new ChatMessage("§eDirty Joe §8[" + Methods.getProgressBar(this.getHealth(), craftVillager.getMaxHealth(), 10, "|", "&c", "&7") + "§8]"));
        this.setCustomNameVisible(true);

        this.targetSelector.a(0, new PathfinderGoalAvoidTarget<>(this, EntityPlayer.class, 15, 1.0, 1.0));
        this.targetSelector.a(1, new PathfinderGoalPanic(this, 2.0));
        this.targetSelector.a(2, new PathfinderGoalRandomStrollLand(this, 0.6));
        this.targetSelector.a(2, new PathfinderGoalRandomLookaround(this));

        this.setPosition(location.getX(), location.getY(), location.getZ());

        this.getWorld().addEntity(this);
    }

}
