package eu.pixliesearth.guns;

import eu.pixliesearth.Main;
import eu.pixliesearth.events.ShootEvent;
import net.minecraft.server.v1_15_R1.Material;
import net.minecraft.server.v1_15_R1.PacketPlayOutEntityDestroy;
import org.bukkit.*;
import org.bukkit.block.data.type.Snow;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Gun {

    public static void shoot(Player player){
        Bukkit.getPluginManager().callEvent(new ShootEvent(player, "§c7.62"));
        player.getWorld().spawn(player.getEyeLocation(), Snowball.class, snowball -> {
            snowball.setShooter(player);
            snowball.setVelocity(player.getEyeLocation().getDirection());
            snowball.setSilent(true);
            snowball.setCustomName("§c7.62mm");
            snowball.setBounce(false);
            snowball.setGravity(false);
            Main.getInstance().getPlayerLists().ammos.add(snowball);
        });
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, SoundCategory.NEUTRAL, 10, 1);
    }
}
