package eu.pixliesearth.guns;

import eu.pixliesearth.Main;
import eu.pixliesearth.events.ShootEvent;
import net.minecraft.server.v1_15_R1.Material;
import net.minecraft.server.v1_15_R1.PacketPlayOutEntityDestroy;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Gun {

    public static Arrow a;

    public static void shoot(Player player){
        Bukkit.getPluginManager().callEvent(new ShootEvent(player, "§c7.62"));
        player.getWorld().spawn(player.getEyeLocation(), Arrow.class, arrow -> {
            arrow.setShooter(((LivingEntity) player));
            arrow.setVelocity(player.getEyeLocation().getDirection().multiply(2));
            arrow.setSilent(true);
            arrow.setCustomName("§c7.62mm");
            arrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
            
            a = arrow;
        });
        // arrow.setShooter(((LivingEntity) player));
        // arrow.setVelocity(player.getEyeLocation().getDirection().multiply(2));
       //customeffects create a special arrow, dont make the arrow invis
        // arrow.getCustomEffects().add(new PotionEffect(PotionEffectType.INVISIBILITY, 100, 2));
        //arrow.addCustomEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100, 2), false);
        // arrow.setSilent(true);
        // arrow.setCustomName("§c7.62mm");
        //NOT RECOMMENDED NMS WAY
        //((CraftEntity) arrow).getHandle().setInvisible(true);
        player.getWorld().playEffect(a.getLocation(), Effect.WITHER_SHOOT, 1);
        //DOESNT WORK ARROW STILL VISIBLE UNEPIC BRUH MOMENT

        //Literally useless and doesnt work
        //for(Player p : Bukkit.getServer().getOnlinePlayers()) {
            //PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(arrow.getEntityId());
            //((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        //}
        new BukkitRunnable(){

            @Override
            public void run(){
                if(!(a.isOnGround()) && !a.isDead() && a.isValid()){
             //       arrow.getWorld().playEffect(arrow.getLocation(), Effect.STEP_SOUND, Material.WOOL,  1);
                    a.getWorld().spawnParticle(Particle.LAVA, a.getLocation(), 1);
                }
                else if(a.isOnGround() || !a.isValid() || a.isDead() || a.isInBlock()){
                    a.remove();
                    a.getWorld().getEntities().remove(a);
                    this.cancel();
                }
            }

        }.runTaskTimer(Main.getInstance(), 0L, 1L);
    }
}
