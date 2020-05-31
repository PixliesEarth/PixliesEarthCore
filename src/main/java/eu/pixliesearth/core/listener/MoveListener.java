package eu.pixliesearth.core.listener;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.entities.chunk.ChunkBank;
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
        if (event.getFrom().getX() != event.getTo().getX() && event.getFrom().getY() != event.getTo().getY() && event.getFrom().getZ() != event.getTo().getZ()) {
            Player player = event.getPlayer();
            Profile profile = Main.getInstance().getProfile(player.getUniqueId());
            if (profile.getTimers().containsKey("Teleport")) {
                profile.getTimers().remove("Teleport");
                profile.save();
                event.getPlayer().sendMessage(Lang.TELEPORTATION_FAILURE.get(event.getPlayer()));
            }
            if (Main.getInstance().getPlayerLists().afk.contains(event.getPlayer().getUniqueId())) {
                Main.getInstance().getPlayerLists().afk.remove(event.getPlayer().getUniqueId());
                Bukkit.broadcastMessage("§8Player §7" + event.getPlayer().getDisplayName() + " §8is §aback§8.");
            }

            // CHUNK TITLES FOR NATIONS
            if (event.getFrom().getChunk() != event.getTo().getChunk()) {
                Chunk fc = event.getFrom().getChunk();
                Chunk tc = event.getTo().getChunk();
                Nation fbelongs = Nation.getById(ChunkBank.table.get(fc.getX(), fc.getZ()));
                Nation tbelongs = Nation.getById(ChunkBank.table.get(tc.getX(), tc.getZ()));
                if (fbelongs != tbelongs) {
                    // if (!profile.isInNation()) {
                        if (tbelongs == null) {
                            player.sendTitle("§c" + Lang.WILDERNESS.get(player), Lang.WILDERNESS_SUBTITLE.get(player), 20, 20 * 3, 20);
                        } else if (tbelongs.getNationId().equals("safezone")) {
                            player.sendTitle("§6SafeZone", Lang.SAFEZONE_SUBTITLE.get(player), 20, 20 * 3, 20);
                        } else {
                            player.sendTitle(tbelongs.getName(), tbelongs.getDescription(), 20, 20 * 3, 20);
                        }
                    // }
                }
            }

        }
    }

}
