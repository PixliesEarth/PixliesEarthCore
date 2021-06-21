package eu.pixliesearth.nations.entities.nation;

import eu.pixliesearth.Main;
import eu.pixliesearth.nations.entities.chunk.NationChunk;
import io.papermc.paper.event.entity.EntityMoveEvent;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

public class FlagListener implements Listener {

    @SneakyThrows
    @EventHandler (priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onMonsterSpawn(EntitySpawnEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            if (!(event.getEntity() instanceof Monster)) return;
            NationChunk nc = NationChunk.get(event.getLocation().getChunk());
            if (nc == null) return;
            Nation nation = nc.getCurrentNation();
            if (nation.getFlags().contains(NationFlag.MONSTERS.name())) return;
            Bukkit.getScheduler().runTask(Main.getInstance(), () -> event.setCancelled(true));
        });
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

    @EventHandler
    public void onEntityMove(EntityMoveEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            if (!(event.getEntity() instanceof Monster)) return;
            if (event.getFrom().getChunk() != event.getTo().getChunk()) {
                Nation nation = NationChunk.getNationData(event.getTo().getChunk());
                if (nation == null) return;
                if (nation.getFlags().contains(NationFlag.MONSTERS.name())) return;
                Bukkit.getScheduler().runTask(Main.getInstance(), () -> event.setCancelled(true));
            }
        });
    }

}
