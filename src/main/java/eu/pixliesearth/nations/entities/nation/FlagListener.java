package eu.pixliesearth.nations.entities.nation;

import eu.pixliesearth.nations.entities.chunk.NationChunk;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
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

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        NationChunk nc = NationChunk.get(player.getChunk());
        if (nc == null) return;
        Nation nation = nc.getCurrentNation();
        if (nation.getFlags().contains(NationFlag.PEACEFUL.name())) event.setCancelled(true);
    }

}
