package eu.pixliesearth.core.custom.listeners;

import eu.pixliesearth.core.custom.CustomListener;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.Map;
/**
 * 
 * @author BradBot_1
 * 
 * <h3>Handles events for custom TABLIST</h3>
 *
 */
public class TabListener extends CustomListener {
	
	private static final Map<Player, Integer> dataMap = new HashMap<>();
	
	@EventHandler
	public void PlayerJoinEvent(PlayerJoinEvent event) {
		update(event.getPlayer());
	}
	
	public static void update(Player player) {
		StringBuilder footer = new StringBuilder();
		if (dataMap.containsKey(player)) {
			int i = dataMap.get(player);
			dataMap.remove(player);
			dataMap.put(player, i+1);
		} else {
			dataMap.put(player, 0);
		}
		String playerName = player.isAfk() ? "§8[§cAFK§8] " + PlaceholderAPI.setPlaceholders(player, "%vault_prefix%" + player.getDisplayName()) : PlaceholderAPI.setPlaceholders(player, "%vault_prefix%" + player.getDisplayName());
		footer.append("\n§b§l").append(getOnline()).append((getOnline()<=1) ? " §7earth player\n" : " §7earth players\n").append(PlaceholderAPI.setPlaceholders(player, "§b§l%bungee_total% §7global player(s)\n"));
		footer.append("§3pixlies.net\n");
		player.setPlayerListHeaderFooter("\n   §7»  §x§4§e§d§e§d§b§lP§x§4§d§c§9§c§7§li§x§4§1§b§0§a§e§lx§x§3§b§a§1§9§f§ll§x§3§4§8§c§8§b§li§x§3§0§7§a§7§a§le§x§2§9§6§9§6§9§ls§x§9§c§9§c§9§c§lN§x§a§8§a§8§a§8§le§x§a§d§a§d§a§d§lt§x§b§3§b§3§b§3§lw§x§b§d§b§d§b§d§lo§x§d§4§d§4§d§4§lr§f§lk  §7«   \n", footer.toString());
		player.setPlayerListName(playerName);
	}
	
	public static int getOnline() {
		return Bukkit.getOnlinePlayers().size();
	}
}
