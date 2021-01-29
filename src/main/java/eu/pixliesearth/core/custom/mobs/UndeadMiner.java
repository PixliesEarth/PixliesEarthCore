package eu.pixliesearth.core.custom.mobs;

import eu.pixliesearth.utils.ItemBuilder;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Zombie;

public class UndeadMiner extends EntityZombie {

    public UndeadMiner(Location location) {
        super(EntityTypes.ZOMBIE, ((CraftWorld)location.getWorld()).getHandle());

        Zombie craftZombie = (Zombie) this.getBukkitEntity();

        craftZombie.setMaxHealth(120);

        this.setHealth(120);
        this.setItemInHand(EnumHand.MAIN_HAND, new ItemStack(Items.DIAMOND_PICKAXE));

        craftZombie.getEquipment().setHelmet(new ItemBuilder(Material.GOLDEN_HELMET).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build());

        this.setPosition(location.getX(), location.getY(), location.getZ());

        this.getWorld().addEntity(this);
    }

}
