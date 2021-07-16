package eu.pixliesearth.core.custom.listeners;

import eu.pixliesearth.core.custom.CustomListener;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.nations.entities.chunk.NationChunk;
import eu.pixliesearth.nations.entities.nation.Nation;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

//TODO: FINISH
public class SafeZoneListener extends CustomListener {

    @EventHandler
    public void onWalk(PlayerMoveEvent event) {
        if (event.getFrom().getX() != event.getTo().getX() || event.getFrom().getY() != event.getTo().getY() || event.getFrom().getZ() != event.getTo().getZ()) {
            Player player = event.getPlayer();
            Profile profile = instance.getProfile(player.getUniqueId());
            Block targetBlock = player.getTargetBlock(5);
            if (targetBlock.getType() != Material.AIR && targetBlock.getType() != Material.WATER && targetBlock.getType() != Material.LAVA) return;
            Nation targetNation = NationChunk.getNationData(targetBlock.getChunk());
            if (targetNation != null && targetNation.getNationId().equalsIgnoreCase("safezone") && profile.isInCombat()) {
                player.sendBlockChange(targetBlock.getLocation(), Material.RED_STAINED_GLASS_PANE.createBlockData());


            }
        }
    }

}
