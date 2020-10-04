package eu.pixliesearth.core.listener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import eu.pixliesearth.Main;
import eu.pixliesearth.events.ShootEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PacketListener  implements Listener{
    public ProtocolManager protocolManager;

    @EventHandler
    public void onPacket(ShootEvent e){

            protocolManager = ProtocolLibrary.getProtocolManager();
            protocolManager.addPacketListener(new PacketAdapter(Main.getInstance(), ListenerPriority.NORMAL, PacketType.Play.Server.SPAWN_ENTITY) {
                @Override
                public void onPacketSending(PacketEvent event) {
                    if(event.getPacketType() == PacketType.Play.Server.SPAWN_ENTITY) {
                        if(e.getAmmoName() != null) {
                            event.setCancelled(true);
                            protocolManager.removePacketListener(this);
                        }
                    }
                }
            });
    }
}
