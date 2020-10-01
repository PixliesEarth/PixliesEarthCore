package eu.pixliesearth.core.listener;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.events.TerritoryChangeEvent;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.entities.chunk.NationChunk;
import eu.pixliesearth.nations.entities.nation.Nation;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class MoveListener implements Listener {

    private static final Main instance = Main.getInstance();
    
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (event.getFrom().getX() != event.getTo().getX() || event.getFrom().getY() != event.getTo().getY() || event.getFrom().getZ() != event.getTo().getZ()) {
            Player player = event.getPlayer();
            Profile profile = instance.getProfile(player.getUniqueId());

            if (profile.getTimers().containsKey("Teleport")) {
                profile.getTimers().remove("Teleport");
                profile.save();
                event.getPlayer().sendMessage(Lang.TELEPORTATION_FAILURE.get(event.getPlayer()));
            }
            if (instance.getUtilLists().afk.contains(event.getPlayer().getUniqueId())) {
                instance.getUtilLists().afk.remove(event.getPlayer().getUniqueId());
                Bukkit.broadcastMessage("§8Player §7" + event.getPlayer().getDisplayName() + " §8is §aback§8.");
            }

            // CHUNK TO CHUNK MOVEMENTS
            Chunk fc = event.getFrom().getChunk();
            Chunk tc = event.getTo().getChunk();
            if (fc != tc) {
                // CLAIM/UNCLAIM AUTOS
                if (instance.getUtilLists().claimAuto.containsKey(player.getUniqueId())) {
                    if (Nation.getById(instance.getUtilLists().claimAuto.get(player.getUniqueId())) == null)
                        instance.getUtilLists().claimAuto.remove(player.getUniqueId());
                    if (NationChunk.get(tc) != null) {
                        player.sendMessage(Lang.ALREADY_CLAIMED.get(player));
                    } else {
                        TerritoryChangeEvent.ChangeType changeType = instance.getUtilLists().claimAuto.get(player.getUniqueId()).equals(profile.getNationId()) ? TerritoryChangeEvent.ChangeType.CLAIM_AUTO_SELF : TerritoryChangeEvent.ChangeType.CLAIM_AUTO_OTHER;
                        NationChunk.claim(player, tc.getWorld().getName(), tc.getX(), tc.getZ(), changeType, instance.getUtilLists().claimAuto.get(player.getUniqueId()));
                    }
                } else if (instance.getUtilLists().unclaimAuto.containsKey(player.getUniqueId())) {
                    if (profile.getCurrentNation() == null)
                        instance.getUtilLists().unclaimAuto.remove(player.getUniqueId());
                    NationChunk nc = NationChunk.get(tc);
                    boolean allowed = false;
                    if (nc == null) allowed = true;
                    if (instance.getUtilLists().staffMode.contains(player.getUniqueId())) allowed = true;
                    if (profile.getNationId().equals(nc.getNationId())) allowed = true;
                    if (!allowed) {
                        Lang.CHUNK_NOT_YOURS.send(player);
                    } else {
                        TerritoryChangeEvent.ChangeType changeType = instance.getUtilLists().unclaimAuto.get(player.getUniqueId()).equals(profile.getNationId()) ? TerritoryChangeEvent.ChangeType.UNCLAIM_AUTO_SELF : TerritoryChangeEvent.ChangeType.UNCLAIM_AUTO_OTHER;
                        NationChunk.unclaim(player, tc.getWorld().getName(), tc.getX(), tc.getZ(), changeType);
                    }
                }
                // CHUNK TITLES
                Nation fn = NationChunk.getNationData(fc);
                Nation tn = NationChunk.getNationData(tc);
                if (fn != tn) {
                    if (tn == null) {  // WILDERNESS
                        player.sendTitle("§c§l" + Lang.WILDERNESS.get(player), Lang.WILDERNESS_SUBTITLE.get(player), 20, 20 * 2, 20);
                    } else {
                        if (tn.getNationId().equals(profile.getNationId())) { // YOUR NATION
                            player.sendTitle("§b§l" + tn.getName(), "§7" + tn.getDescription(), 20, 20 * 2, 20);
                        } else if (tn.getNationId().equals("safezone")) { // SAFEZONE
                            player.sendTitle("§a§lSafeZone", "§7" + Lang.SAFEZONE_SUBTITLE.get(player), 20, 20 * 2, 20);
                        } else if (tn.getNationId().equals("warzone")) { // WARZONE
                            player.sendTitle("§c§lWarZone", "§7" + Lang.WARZONE_SUBTITLE.get(player), 20, 20 * 2, 20);
                        } else if (tn.isAlliedWith(profile.getNationId())) { // ALLIES
                            player.sendTitle("§d§l" + tn.getName(), "§7" + tn.getDescription(), 20, 20 * 2, 20);
                        } else { // ANY OTHER NATION
                            player.sendTitle("§l" + tn.getName(), "§7" + tn.getDescription(), 20, 20 * 2, 20);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        if (!event.getFrom().getWorld().getName().equals(event.getTo().getWorld().getName())) {
            Main.getInstance().getUtilLists().claimAuto.remove(player.getUniqueId());
            Main.getInstance().getUtilLists().unclaimAuto.remove(player.getUniqueId());
        }
    }

}
