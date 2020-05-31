package eu.pixliesearth.core.listener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.destroystokyo.paper.event.entity.EntityAddToWorldEvent;
import eu.pixliesearth.Main;
import eu.pixliesearth.events.ShootEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

public class PacketListener  implements Listener{
    public ProtocolManager protocolManager;

    @EventHandler
    public void onPacket(ShootEvent e){

        //if(!(e.getEntity() instanceof Arrow)) return;


            protocolManager = ProtocolLibrary.getProtocolManager();
            protocolManager.addPacketListener(new PacketAdapter(Main.getInstance(), ListenerPriority.NORMAL, PacketType.Play.Server.SPAWN_ENTITY) {
                @Override
                public void onPacketSending(PacketEvent event) {
                    //DONT ADD THIS BACK IT DO CRING NAE NAE
                    //super.onPacketSending(event);
                    if(event.getPacketType() == PacketType.Play.Server.SPAWN_ENTITY){
                        //if(e.getEntity().getCustomName() == null) return;
                        //if(e.getEntity().getCustomName().equalsIgnoreCase("§c7.62mm")) {
                          //  System.out.println(e.getEntity().getCustomName());
                        if(e.getAmmoName() != null) {
                            for(Player players : Bukkit.getOnlinePlayers()) {
                                event.setCancelled(true);
                            }
                            protocolManager.removePacketListener(this);
                        }
                    }
                }
            });
    }
}
