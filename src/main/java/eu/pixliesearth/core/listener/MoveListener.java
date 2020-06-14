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

public class MoveListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (event.getFrom().getX() != event.getTo().getX() || event.getFrom().getY() != event.getTo().getY() || event.getFrom().getZ() != event.getTo().getZ()) {
            Main instance = Main.getInstance();
            Player player = event.getPlayer();
            Profile profile = Main.getInstance().getProfile(player.getUniqueId());
            if (profile.getTimers().containsKey("Teleport")) {
                profile.getTimers().remove("Teleport");
                profile.save();
                event.getPlayer().sendMessage(Lang.TELEPORTATION_FAILURE.get(event.getPlayer()));
            }
            if (Main.getInstance().getUtilLists().afk.contains(event.getPlayer().getUniqueId())) {
                Main.getInstance().getUtilLists().afk.remove(event.getPlayer().getUniqueId());
                Bukkit.broadcastMessage("§8Player §7" + event.getPlayer().getDisplayName() + " §8is §aback§8.");
            }

            // CHUNK TITLES FOR NATIONS
            if (event.getFrom().getChunk() != event.getTo().getChunk()) {
                Chunk c = event.getTo().getChunk();
                if (Main.getInstance().getUtilLists().claimAuto.containsKey(player.getUniqueId())) {
                    if (profile.getCurrentNation() == null)
                        Main.getInstance().getUtilLists().claimAuto.remove(player.getUniqueId());
                    if (NationChunk.get(event.getTo().getChunk()) != null) {
                        player.sendMessage(Lang.ALREADY_CLAIMED.get(player));
                    } else {
                        NationChunk nc = new NationChunk(instance.getUtilLists().claimAuto.get(player.getUniqueId()), event.getTo().getChunk().getWorld().getName(), event.getTo().getChunk().getX(), event.getTo().getChunk().getZ());
                        TerritoryChangeEvent territoryEvent = new TerritoryChangeEvent(player, nc, TerritoryChangeEvent.ChangeType.CLAIM_AUTO_SELF);
                        Bukkit.getPluginManager().callEvent(territoryEvent);
                        if (!event.isCancelled()) {
                            nc.claim();
                            for (Player members : profile.getCurrentNation().getOnlineMemberSet())
                                members.sendMessage(Lang.PLAYER_CLAIMED.get(members).replace("%PLAYER%", player.getDisplayName()).replace("%X%", event.getTo().getChunk().getX() + "").replace("%Z%", event.getTo().getChunk().getZ() + ""));
                            System.out.println("§bChunk claimed at §e" + nc.getX() + "§8, §e" + nc.getZ() + " §bfor §e" + nc.getCurrentNation().getName());
                        }
                    }
                } else if (Main.getInstance().getUtilLists().unclaimAuto.containsKey(player.getUniqueId())) {
                    if (profile.getCurrentNation() == null)
                        Main.getInstance().getUtilLists().unclaimAuto.remove(player.getUniqueId());
                    NationChunk nc = NationChunk.get(c);
                    boolean allowed = false;
                    if (instance.getUtilLists().staffMode.contains(player.getUniqueId())) allowed = true;
                    if (profile.getNationId().equals(nc.getNationId())) allowed = true;
                    if (!allowed) {
                        Lang.CHUNK_NOT_YOURS.send(player);
                    } else {
                        TerritoryChangeEvent territoryEvent = new TerritoryChangeEvent(player, nc, TerritoryChangeEvent.ChangeType.UNCLAIM_AUTO_SELF);
                        Bukkit.getPluginManager().callEvent(territoryEvent);
                        if (!event.isCancelled()) {
                            nc.unclaim();
                            for (Player members : profile.getCurrentNation().getOnlineMemberSet())
                                members.sendMessage(Lang.PLAYER_UNCLAIMED.get(members).replace("%PLAYER%", player.getDisplayName()).replace("%X%", c.getX() + "").replace("%Z%", c.getZ() + ""));
                            System.out.println("§bChunk unclaimed at §e" + nc.getX() + "§8, §e" + nc.getZ());
                        }
                    }
                }
                Chunk fc = event.getFrom().getChunk();
                Chunk tc = event.getTo().getChunk();
                NationChunk fchunk = NationChunk.get(fc);
                NationChunk tchunk = NationChunk.get(tc);
                if (fchunk != tchunk) {
                    if (tchunk == null) {
                        player.sendTitle("§c" + Lang.WILDERNESS.get(player), Lang.WILDERNESS_SUBTITLE.get(player), 20, 20 * 3, 20);
                    } else {
                        if (!fchunk.getNationId().equals(tchunk.getNationId())) {
                            if (tchunk.getNationId().equals("safezone")) {
                                player.sendTitle("§6SafeZone", Lang.SAFEZONE_SUBTITLE.get(player), 20, 20 * 3, 20);
                            } else {
                                Nation n = Nation.getById(tchunk.getNationId());
                                player.sendTitle(n.getName(), n.getDescription(), 20, 20 * 3, 20);
                            }
                        }
                    }
                }
            }

        }
    }

}
