package eu.pixliesearth.core.lib.me.joeleoli.frame.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import eu.pixliesearth.core.lib.me.joeleoli.frame.FrameAdapter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ExampleFrameAdapter implements FrameAdapter {

	@Override
	public String getTitle(Player player) {
		return ChatColor.GOLD.toString() + "Frame Board Example";
	}

	@Override
	public List<String> getLines(Player player) {
		final int rand = ThreadLocalRandom.current().nextInt(500);
		final List<String> toReturn = new ArrayList<>();

		toReturn.add("&eThis is an example board.");
		toReturn.add("&dAs you can see, it supports");
		toReturn.add("&dup to 32 characters per line.");
		toReturn.add("&aDon't forget... Colors count");
		toReturn.add("&btowards the character count.");
		toReturn.add("Random number: " + rand);

		return toReturn;
	}

}
