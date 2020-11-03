package eu.pixliesearth.utils;

import org.bukkit.entity.Player;

import lombok.Getter;
import lombok.Setter;

public class TabList {
	
	@Getter Player player;
	@Getter @Setter String header;
	@Getter @Setter String footer;
	@Getter @Setter String displayName;
	/**
	 * Initialises the class based on the player given
	 * 
	 * @param player The {@link Player} to load around
	 */
	public TabList(Player player) {
		this.player = player;
		this.header = player.getPlayerListHeader();
		this.footer = player.getPlayerListFooter();
		this.displayName = player.getPlayerListName();
	}
	/**
	 * Updates the players tab list
	 */
	public void update() {
		player.setPlayerListHeader(getHeader());
		player.setPlayerListFooter(getFooter());
		player.setPlayerListName(getDisplayName());
	}
	
}