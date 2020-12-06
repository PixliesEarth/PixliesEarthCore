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
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import static org.bukkit.event.EventPriority.HIGHEST;

public class ProtectionManager implements Listener {

    private static final Main instance = Main.getInstance();

    @EventHandler(priority = HIGHEST)
    public void onBreak(BlockBreakEvent event) {
    	try {
	        boolean canBreak = canBreak(event);
	        Player player = event.getPlayer();
	        if (canBreak) return;
	        player.sendActionBar(Lang.CANT_INTERACT_TERRITORY.get(player));
	        event.setCancelled(true);
    	} catch (Exception ingore) {}
    }

    @EventHandler(priority = HIGHEST)
    public void onBlockDamage(BlockDamageEvent event) {
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
        NationChunk nc = NationChunk.get(c);
        Player player = event.getPlayer();
        Profile profile = instance.getProfile(player.getUniqueId());
        if (nc == null) return true;
        Nation host = nc.getCurrentNation();
        if (profile.isInWar() && (instance.getCurrentWar().getMainDefender().equals(host.getNationId()) || instance.getCurrentWar().getMainAggressor().equals(host.getNationId()))) return true;
        if (Permission.hasForeignPermission(profile, Permission.BUILD, host)) return true;
        if (Permission.hasAccessHere(profile, nc)) return true;
        if (!profile.isInNation()) return false;
        Nation guest = profile.getCurrentNation();
        if (host.getNationId().equals(guest.getNationId()) && Permission.hasNationPermission(profile, Permission.BUILD)) return true;
        return Permission.hasForeignPermission(guest, Permission.BUILD, host);
    }

    public static boolean canBreak(BlockDamageEvent event) {
        if (instance.getUtilLists().staffMode.contains(event.getPlayer().getUniqueId())) return true;
        Chunk c = event.getBlock().getChunk();
        NationChunk nc = NationChunk.get(c);
        Player player = event.getPlayer();
        Profile profile = instance.getProfile(player.getUniqueId());
        if (nc == null) return true;
        Nation host = nc.getCurrentNation();
        if (profile.isInWar() && (instance.getCurrentWar().getMainDefender().equals(host.getNationId()) || instance.getCurrentWar().getMainAggressor().equals(host.getNationId()))) return true;
        if (Permission.hasForeignPermission(profile, Permission.BUILD, host)) return true;
        if (Permission.hasAccessHere(profile, nc)) return true;
        if (!profile.isInNation()) return false;
        Nation guest = profile.getCurrentNation();
        if (host.getNationId().equals(guest.getNationId()) && Permission.hasNationPermission(profile, Permission.BUILD)) return true;
        return Permission.hasForeignPermission(guest, Permission.BUILD, host);
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

    @EventHandler(priority = HIGHEST)
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
        Player player = event.getPlayer();
        Profile profile = instance.getProfile(player.getUniqueId());
        if (nc == null) return true;
        Nation host = nc.getCurrentNation();
        if (profile.isInWar() && (instance.getCurrentWar().getMainDefender().equals(host.getNationId()) || instance.getCurrentWar().getMainAggressor().equals(host.getNationId()))) return true;
        if (Permission.hasForeignPermission(profile, Permission.BUILD, host)) return true;
        if (Permission.hasAccessHere(profile, nc)) return true;
        if (!profile.isInNation()) return false;
        Nation guest = profile.getCurrentNation();
        if (host.getNationId().equals(guest.getNationId()) && Permission.hasNationPermission(profile, Permission.BUILD)) return true;
        if (Permission.hasForeignPermission(guest, Permission.BUILD, host)) return true;
        return false;
    }

    @EventHandler(priority = HIGHEST)
    public void onInteract(PlayerInteractEvent event) {
        boolean can = canInteract(event);
        if (!can) {
            event.setCancelled(true);
            event.getPlayer().sendActionBar(Lang.CANT_INTERACT_TERRITORY.get(event.getPlayer()));
        }
    }

    public static boolean canInteract(PlayerInteractEvent event) {
        if (instance.getUtilLists().staffMode.contains(event.getPlayer().getUniqueId())) return true;
        if (event.getClickedBlock() == null) return true;
        Chunk c = event.getClickedBlock().getChunk();
        // if (!event.getClickedBlock().getType().isInteractable()) return true true true;
        NationChunk nc = NationChunk.get(c);
        Player player = event.getPlayer();
        Profile profile = instance.getProfile(player.getUniqueId());
        if (nc == null) return true;
        Nation host = nc.getCurrentNation();
        if (profile.isInWar() && (instance.getCurrentWar().getMainDefender().equals(host.getNationId()) || instance.getCurrentWar().getMainAggressor().equals(host.getNationId()))) return true;
        if (Permission.hasForeignPermission(profile, Permission.INTERACT, host)) return true;
        if (Permission.hasAccessHere(profile, nc)) return true;
        if (!profile.isInNation()) return false;
        Nation guest = profile.getCurrentNation();
        if (host.getNationId().equals(guest.getNationId()) && Permission.hasNationPermission(profile, Permission.INTERACT)) return true;
        if (Permission.hasForeignPermission(guest, Permission.INTERACT, host)) return true;
        return false;
    }

    @EventHandler(priority = HIGHEST)
    public void onBlockFromTo(BlockFromToEvent event) {
        if (event.getBlock().isLiquid()) {
            if (event.getToBlock().isEmpty()) {
                NationChunk from = NationChunk.get(event.getBlock().getChunk());
                NationChunk to = NationChunk.get(event.getToBlock().getChunk());
                if (to == null) return;
                if (from == null) {
                    event.setCancelled(true);
                    return;
                }
                if (to.getNationId().equals(from.getNationId())) return;
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = HIGHEST)
    public void onPistonExtend(BlockPistonExtendEvent event) {
        NationChunk from = NationChunk.get(event.getBlock().getChunk());
        NationChunk to = NationChunk.get(event.getBlock().getRelative(event.getDirection(),  event.getLength() + 1).getChunk());
        if (to == null) return;
        if (from == null) {
            event.setCancelled(true);
            return;
        }
        if (to.getNationId().equals(from.getNationId())) return;
        event.setCancelled(true);
    }

    @EventHandler(priority = HIGHEST)
    public void onPistonExtend(BlockPistonRetractEvent event) {
        if (!event.isSticky()) return;
        NationChunk from = NationChunk.get(event.getBlock().getChunk());
        NationChunk to = NationChunk.get(event.getRetractLocation().getChunk());
        if (to == null) return;
        if (from == null) {
            event.setCancelled(true);
            return;
        }
        if (to.getNationId().equals(from.getNationId())) return;
        event.setCancelled(true);
    }



    @EventHandler(priority = HIGHEST)
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

    @EventHandler(priority = HIGHEST)
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Monster) return;
        if (event.getDamager() instanceof Player) {
            if (instance.getUtilLists().staffMode.contains(event.getDamager().getUniqueId())) return;
            Chunk c = event.getEntity().getChunk();
            NationChunk nc = NationChunk.get(c);
            Player player = (Player) event.getDamager();
            Profile profile = instance.getProfile(player.getUniqueId());
            if (profile.isInWar() && event.getEntity() instanceof Player && instance.getProfile(event.getEntity().getUniqueId()).isInWar())
                return;
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
        } else if (event.getDamager() instanceof Projectile && ((Projectile) event.getDamager()).getShooter() instanceof Player) {
            Player player = (Player) ((Projectile) event.getDamager()).getShooter();
            if (player == null) return;
            if (instance.getUtilLists().staffMode.contains(player.getUniqueId())) return;
            Chunk c = event.getEntity().getChunk();
            NationChunk nc = NationChunk.get(c);
            Profile profile = instance.getProfile(player.getUniqueId());
            if (profile.isInWar() && event.getEntity() instanceof Player && instance.getProfile(event.getEntity().getUniqueId()).isInWar())
                return;
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
