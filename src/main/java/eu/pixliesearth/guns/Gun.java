package eu.pixliesearth.guns;

import eu.pixliesearth.Main;
import net.minecraft.server.v1_15_R1.Material;
import net.minecraft.server.v1_15_R1.PacketPlayOutEntityDestroy;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Gun {

    public static void shoot(Player player){
        Arrow arrow = (Arrow) player.getWorld().spawn(player.getEyeLocation(), Arrow.class);
        arrow.setShooter(((LivingEntity) player));
        arrow.setVelocity(player.getEyeLocation().getDirection().multiply(2));
       //customeffects create a special arrow, dont make the arrow invis
        // arrow.getCustomEffects().add(new PotionEffect(PotionEffectType.INVISIBILITY, 100, 2));
        //arrow.addCustomEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100, 2), false);
        arrow.setSilent(true);
        arrow.setCustomName("§c7.62mm");
        //NOT RECOMMENDED NMS WAY
        //((CraftEntity) arrow).getHandle().setInvisible(true);
        player.getWorld().playEffect(arrow.getLocation(), Effect.WITHER_SHOOT, 1);
        //DOESNT WORK ARROW STILL VISIBLE UNEPIC BRUH MOMENT

        //Literally useless and doesnt work
        //for(Player p : Bukkit.getServer().getOnlinePlayers()) {
            //PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(arrow.getEntityId());
            //((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        //}
        new BukkitRunnable(){

            @Override
            public void run(){
                if(!(arrow.isOnGround()) && !arrow.isDead() && arrow.isValid()){
             //       arrow.getWorld().playEffect(arrow.getLocation(), Effect.STEP_SOUND, Material.WOOL,  1);
                    arrow.getWorld().spawnParticle(Particle.LAVA, arrow.getLocation(), 1);
                }
                else if(arrow.isOnGround() || !arrow.isValid() || arrow.isDead() || arrow.isInBlock()){
                    arrow.remove();
                    this.cancel();
                }
            }

        }.runTaskTimer(Main.getInstance(), 0L, 1L);
    }
}
