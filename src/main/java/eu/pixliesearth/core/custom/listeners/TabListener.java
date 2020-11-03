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
 * <h3>Handles events for custom armours</h3>
 *
 * @apiNote TODO: notes
 */
public class TabListener extends CustomListener {
	
	private static Map<Player, Integer> dataMap = new HashMap<>();
	
	@EventHandler
	public void PlayerJoinEvent(PlayerJoinEvent event) {
		update(event.getPlayer());
	}
	
	public static void update(Player player) {
		BaseComponent header = new TextComponent("");
		BaseComponent footer = new TextComponent("");
		switch (dataMap.getOrDefault(player, 0)) {
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
		}
		if (dataMap.containsKey(player)) {
			int i = dataMap.get(player);
			dataMap.remove(player);
			dataMap.put(player, i+1);
		} else {
			dataMap.put(player, 0);
		}
		footer.addExtra("§aOnline: "+getOnline()+"\n");
		footer.addExtra("§bpixlies.net");
		player.setPlayerListHeaderFooter(header, footer);
		player.setPlayerListName(player.getDisplayName());
	}
	
	public static int getOnline() {
		int i = 0;
		for (@SuppressWarnings("unused") Player p : Bukkit.getServer().getOnlinePlayers())
			i++;
		return i;
	}
}
