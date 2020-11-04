package eu.pixliesearth.core.custom.listeners;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import eu.pixliesearth.core.custom.CustomListener;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
/**
 * 
 * @author BradBot_1
 * 
 * <h3>Handles events for custom TABLIST</h3>
 *
 * @apiNote TODO: notes
 */
public class TabListener extends CustomListener {
	
	private static final Map<Player, Integer> dataMap = new HashMap<>();
	
	@EventHandler
	public void PlayerJoinEvent(PlayerJoinEvent event) {
		update(event.getPlayer());
	}
	
	public static void update(Player player) {
		BaseComponent header = new TextComponent("\n§x§4§e§d§e§d§b§lP§x§4§d§c§9§c§7§li§x§4§1§b§0§a§e§lx§x§3§b§a§1§9§f§ll§x§3§4§8§c§8§b§li§x§3§0§7§a§7§a§le§x§2§9§6§9§6§9§ls§x§9§c§9§c§9§c§lN§x§a§8§a§8§a§8§le§x§a§d§a§d§a§d§lt§x§b§3§b§3§b§3§lw§x§b§d§b§d§b§d§lo§x§d§4§d§4§d§4§lr§f§lk §cMaintenance§7! {newline}§8§8>> §x§4§e§d§e§d§b§lP§x§4§d§c§9§c§7§li§x§4§1§b§0§a§e§lx§x§3§b§a§1§9§f§ll§x§3§4§8§c§8§b§li§x§3§0§7§a§7§a§le§x§2§9§6§9§6§9§ls\n");
		BaseComponent footer = new TextComponent("");
/*		switch (dataMap.getOrDefault(player, 0)) {
		case 0 :
			header.addExtra("§b§lPIXLIES");
			break;
		case 4 :
			header.addExtra("§f§lP§b§lIXLIES");
			break;
		case 8 :
			header.addExtra("§f§lPI§b§lXLIES");
			break;
		case 12 :
			header.addExtra("§b§lP§f§lIX§b§lLIES");
			break;
		case 16 :
			header.addExtra("§b§lPI§f§lXL§b§lIES");
			break;
		case 20 :
			header.addExtra("§b§lPIX§f§lLI§b§lES");
			break;
		case 24 :
			header.addExtra("§b§lPIXL§f§lIE§b§lS");
			break;
		case 28 :
			header.addExtra("§b§lPIXLI§f§lES");
			break;
		case 32 :
			header.addExtra("§b§lPIXLIE§f§lS");
			break;
		case 36 :
			header.addExtra("§b§lPIXLIES");
			break;
		case 40 :
			header.addExtra("§b§lPIXLIES");
			break;
		}*/
		if (dataMap.containsKey(player)) {
			int i = dataMap.get(player);
			dataMap.remove(player);
			dataMap.put(player, i+1);
		} else {
			dataMap.put(player, 0);
		}
		footer.addExtra("\n§b§l" + getOnline() + " §7players online\n");
		footer.addExtra("§7pixlies.net\n");
		player.setPlayerListHeaderFooter(header, footer);
		player.setPlayerListName(player.getDisplayName());
	}
	
	public static int getOnline() {
		return Bukkit.getOnlinePlayers().size();
	}
}
