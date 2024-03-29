package eu.pixliesearth.nations.entities.nation;

import eu.pixliesearth.nations.entities.chunk.NationChunk;
import io.papermc.paper.event.entity.EntityMoveEvent;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

public class FlagListener implements Listener {

    @EventHandler (priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onMonsterSpawn(CreatureSpawnEvent event) {
        if (!(event.getEntity() instanceof Monster)) return;
        NationChunk nc = NationChunk.get(event.getLocation().getChunk());
        if (nc == null) return;
        Nation nation = nc.getCurrentNation();
        if (nation.getFlags().contains(NationFlag.MONSTERS.name())) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        NationChunk nc = NationChunk.get(player.getChunk());
        if (nc == null) return;
        Nation nation = nc.getCurrentNation();
        if (nation.getFlags().contains(NationFlag.PEACEFUL.name())) event.setCancelled(true);
    }

    @EventHandler
    public void onEntityMove(EntityMoveEvent event) {
        if (!(event.getEntity() instanceof Monster)) return;
        if (event.getFrom().getChunk() != event.getTo().getChunk()) {
            Nation nation = NationChunk.getNationData(event.getTo().getChunk());
            if (nation == null) return;
            if (nation.getFlags().contains(NationFlag.MONSTERS.name())) return;
            event.getEntity().remove();
        }
    }

}
