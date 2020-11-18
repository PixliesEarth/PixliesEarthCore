package eu.pixliesearth.core.listener;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.entities.chunk.NationChunk;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.entities.nation.ranks.Permission;
import org.bukkit.Chunk;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import static org.bukkit.event.EventPriority.MONITOR;

public class ProtectionManager implements Listener {

    private static final Main instance = Main.getInstance();

    @EventHandler(priority = MONITOR)
    public void onBreak(BlockBreakEvent event) {
    	try {
	        boolean canBreak = canBreak(event);
	        Player player = event.getPlayer();
	        if (canBreak) return;
	        player.sendActionBar(Lang.CANT_INTERACT_TERRITORY.get(player));
	        event.setCancelled(true);
    	} catch (Exception ingore) {}
    }

    public static boolean canBreak(BlockBreakEvent event) {
        if (instance.getUtilLists().staffMode.contains(event.getPlayer().getUniqueId())) return true;
        Chunk c = event.getBlock().getChunk();
        if (c == null) return true;
        NationChunk nc = NationChunk.get(c);
        Player player = event.getPlayer();
        Profile profile = instance.getProfile(player.getUniqueId());
        if (nc == null) return true;
        Nation host = nc.getCurrentNation();
        if (Permission.hasForeignPermission(profile, Permission.BUILD, host)) return true;
        if (Permission.hasAccessHere(profile, nc)) return true;
        if (!profile.isInNation()) return false;
        Nation guest = profile.getCurrentNation();
        if (host.getNationId().equals(guest.getNationId()) && Permission.hasNationPermission(profile, Permission.BUILD)) return true;
        if (Permission.hasForeignPermission(guest, Permission.BUILD, host)) return true;
        return false;
    }

    public static boolean canBreak(Block block, Player player) {
        if (player.getGameMode() != GameMode.CREATIVE
                && (block.getType() == Material.BEDROCK
                || block.getType() == Material.COMMAND_BLOCK
                || block.getType() == Material.CHAIN_COMMAND_BLOCK
                || block.getType() == Material.REPEATING_COMMAND_BLOCK
                || block.getType() == Material.WATER
                || block.getType() == Material.LAVA
                || block.getType() == Material.BARRIER
                || block.getType() == Material.ARMOR_STAND
                || block.getType() == Material.AIR
                || block.getType() == Material.OBSIDIAN)) return false;

        if (instance.getUtilLists().staffMode.contains(player.getUniqueId())) return true;
        Chunk c = block.getChunk();
        if (c == null) return true;
        NationChunk nc = NationChunk.get(c);
        Profile profile = instance.getProfile(player.getUniqueId());
        if (nc == null) return true;
        Nation host = nc.getCurrentNation();
        if (Permission.hasForeignPermission(profile, Permission.BUILD, host)) return true;
        if (Permission.hasAccessHere(profile, nc)) return true;
        if (!profile.isInNation()) return false;
        Nation guest = profile.getCurrentNation();
        if (host.getNationId().equals(guest.getNationId()) && Permission.hasNationPermission(profile, Permission.BUILD)) return true;
        if (Permission.hasForeignPermission(guest, Permission.BUILD, host)) return true;
        return false;
    }

    @EventHandler(priority = MONITOR)
    public void onPlace(BlockPlaceEvent event) {
    	try {
	        boolean canPlace = canPlace(event);
	        Player player = event.getPlayer();
	        if (canPlace) return;
	        player.sendActionBar(Lang.CANT_INTERACT_TERRITORY.get(player));
	        event.setCancelled(true);
    	} catch (Exception ingore) {}
    }

    public static boolean canPlace(BlockPlaceEvent event) {
        if (instance.getUtilLists().staffMode.contains(event.getPlayer().getUniqueId())) return true;
        Chunk c = event.getBlock().getChunk();
        NationChunk nc = NationChunk.get(c);
        if (event.getPlayer()==null) return true;
        Player player = event.getPlayer();
        Profile profile = instance.getProfile(player.getUniqueId());
        if (nc == null) return true;
        Nation host = nc.getCurrentNation();
        if (Permission.hasForeignPermission(profile, Permission.BUILD, host)) return true;
        if (Permission.hasAccessHere(profile, nc)) return true;
        if (!profile.isInNation()) return false;
        Nation guest = profile.getCurrentNation();
        if (host.getNationId().equals(guest.getNationId()) && Permission.hasNationPermission(profile, Permission.BUILD)) return true;
        if (Permission.hasForeignPermission(guest, Permission.BUILD, host)) return true;
        return false;
    }

    @EventHandler(priority = MONITOR)
    public void onInteract(PlayerInteractEvent event) {
        if (instance.getUtilLists().staffMode.contains(event.getPlayer().getUniqueId())) return;
        if (event.getClickedBlock() == null) return;
        Chunk c = event.getClickedBlock().getChunk();
        if (!event.getClickedBlock().getType().isInteractable()) return;
        NationChunk nc = NationChunk.get(c);
        Player player = event.getPlayer();
        Profile profile = instance.getProfile(player.getUniqueId());
        if (nc == null) return;
        Nation host = nc.getCurrentNation();
        if (Permission.hasForeignPermission(profile, Permission.INTERACT, host)) return;
        if (Permission.hasAccessHere(profile, nc)) return;
        if (!profile.isInNation()) {
            player.sendActionBar(Lang.CANT_INTERACT_TERRITORY.get(player));
            event.setCancelled(true);
            return;
        }
        Nation guest = profile.getCurrentNation();
        if (host.getNationId().equals(guest.getNationId()) && Permission.hasNationPermission(profile, Permission.INTERACT)) return;
        if (Permission.hasForeignPermission(guest, Permission.INTERACT, host)) return;

        player.sendActionBar(Lang.CANT_INTERACT_TERRITORY.get(player));
        event.setCancelled(true);
    }

    @EventHandler(priority = MONITOR)
    public void onEnityInteract(PlayerInteractEntityEvent event) {
        if (instance.getUtilLists().staffMode.contains(event.getPlayer().getUniqueId())) return;
        if (event.getRightClicked() instanceof Player) return;
        Chunk c = event.getRightClicked().getChunk();
        NationChunk nc = NationChunk.get(c);
        Player player = event.getPlayer();
        Profile profile = instance.getProfile(player.getUniqueId());
        if (nc == null) return;
        Nation host = nc.getCurrentNation();
        if (Permission.hasForeignPermission(profile, Permission.INTERACT, host)) return;
        if (Permission.hasAccessHere(profile, nc)) return;
        if (!profile.isInNation()) {
            player.sendActionBar(Lang.CANT_INTERACT_TERRITORY.get(player));
            event.setCancelled(true);
            return;
        }
        Nation guest = profile.getCurrentNation();
        if (host.getNationId().equals(guest.getNationId()) && Permission.hasNationPermission(profile, Permission.INTERACT)) return;
        if (Permission.hasForeignPermission(guest, Permission.INTERACT, host)) return;

        player.sendActionBar(Lang.CANT_INTERACT_TERRITORY.get(player));
        event.setCancelled(true);
    }

    @EventHandler(priority = MONITOR)
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            if (instance.getUtilLists().staffMode.contains(event.getDamager().getUniqueId())) return;
            Chunk c = event.getEntity().getChunk();
            NationChunk nc = NationChunk.get(c);
            Player player = (Player) event.getDamager();
            Profile profile = instance.getProfile(player.getUniqueId());
            if (nc == null) return;
            Nation host = nc.getCurrentNation();
            if (Permission.hasForeignPermission(profile, Permission.INTERACT, host)) return;
            if (Permission.hasAccessHere(profile, nc)) return;
            if (!profile.isInNation()) {
                player.sendActionBar(Lang.CANT_INTERACT_TERRITORY.get(player));
                event.setCancelled(true);
                return;
            }
            Nation guest = profile.getCurrentNation();
            if (host.getNationId().equals(guest.getNationId()) && Permission.hasNationPermission(profile, Permission.INTERACT)) return;
            if (Permission.hasForeignPermission(guest, Permission.INTERACT, host)) return;

            player.sendActionBar(Lang.CANT_INTERACT_TERRITORY.get(player));
            event.setCancelled(true);
        }
    }

}
