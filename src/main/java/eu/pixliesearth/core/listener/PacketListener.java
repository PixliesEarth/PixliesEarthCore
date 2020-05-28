package eu.pixliesearth.core.listener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.destroystokyo.paper.event.entity.EntityAddToWorldEvent;
import eu.pixliesearth.Main;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

public class PacketListener  implements Listener{
    public ProtocolManager protocolManager;

    @EventHandler
    public void onPacket(ProjectileLaunchEvent e){

        if(!(e.getEntity() instanceof Arrow)) return;
        System.out.println("REACHED ENTITY SPAWN EVENT\nREACHED ENTITY SPAWN EVENT");
        System.out.println(e.getEntity().getCustomName());
        protocolManager = ProtocolLibrary.getProtocolManager();
        protocolManager.addPacketListener(new PacketAdapter(Main.getInstance(), ListenerPriority.NORMAL, PacketType.Play.Server.SPAWN_ENTITY) {
            @Override
            public void onPacketSending(PacketEvent event) {
                super.onPacketSending(event);
                if(event.getPacketType() == PacketType.Play.Server.SPAWN_ENTITY){
                    System.out.println("REACHED\nREACHED\nREACHED\nREACHED");
                    if(e.getEntity().getCustomName() == null) return;
                    if(Objects.equals(e.getEntity().getCustomName(), "§c7.62mm")){
                        event.setCancelled(true);
                    }
                }
            }
        });
    }
}
