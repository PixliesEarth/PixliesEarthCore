package eu.pixliesearth.core.custom.listeners;

import eu.pixliesearth.core.custom.CustomListener;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.nations.entities.chunk.NationChunk;
import eu.pixliesearth.nations.entities.nation.Nation;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.List;

public class SafeZoneListener extends CustomListener {

    @EventHandler
    public void onWalk(PlayerMoveEvent event) {
        if (event.getFrom().getX() != event.getTo().getX() || event.getFrom().getY() != event.getTo().getY() || event.getFrom().getZ() != event.getTo().getZ()) {
            Player player = event.getPlayer();
            Profile profile = instance.getProfile(player.getUniqueId());
            Block targetBlock = player.getTargetBlock(2);
            if (targetBlock.getType() != Material.AIR && targetBlock.getType() != Material.WATER && targetBlock.getType() != Material.LAVA) return;
            Nation targetNation = NationChunk.getNationData(targetBlock.getChunk());
            if (targetNation != null && targetNation.getNationId().equalsIgnoreCase("safezone") && profile.isInCombat()) {
                player.setVelocity(player.getFacing().getDirection().setY(0).multiply(-2));
                List<Block> blocks = new ArrayList<>();
                blocks.add(targetBlock);
                blocks.add(targetBlock.getRelative(BlockFace.WEST));
                blocks.add(targetBlock.getRelative(BlockFace.WEST).getRelative(BlockFace.UP));
                blocks.add(targetBlock.getRelative(BlockFace.WEST).getRelative(BlockFace.DOWN));
                blocks.add(targetBlock.getRelative(BlockFace.EAST));
                blocks.add(targetBlock.getRelative(BlockFace.EAST).getRelative(BlockFace.UP));
                blocks.add(targetBlock.getRelative(BlockFace.EAST).getRelative(BlockFace.DOWN));
                blocks.add(targetBlock.getRelative(BlockFace.DOWN));
                blocks.add(targetBlock.getRelative(BlockFace.UP));
                for (Block block : blocks) {
                    if (block.getType() != Material.AIR && block.getType() != Material.WATER && block.getType() != Material.LAVA) continue;
                    player.sendBlockChange(block.getLocation(), Material.RED_STAINED_GLASS.createBlockData());
                    Bukkit.getScheduler().runTaskLater(instance, () -> player.sendBlockChange(block.getLocation(), block.getType().createBlockData()), 40);
                }
            }
        }
    }

}
