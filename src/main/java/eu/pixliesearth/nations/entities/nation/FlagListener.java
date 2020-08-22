package eu.pixliesearth.nations.entities.nation;

import eu.pixliesearth.nations.entities.chunk.NationChunk;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class FlagListener implements Listener {

    @EventHandler (priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onMonsterSpawn(EntitySpawnEvent event) {
        if (!(event.getEntity() instanceof Monster)) return;
        NationChunk nc = NationChunk.get(event.getLocation().getChunk());
        if (nc == null) return;
        Nation nation = nc.getCurrentNation();
        if (nation.getFlags().contains(NationFlag.MONSTERS.name())) return;
        event.setCancelled(true);
    }

}
