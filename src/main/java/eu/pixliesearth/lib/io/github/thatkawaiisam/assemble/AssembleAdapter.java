package eu.pixliesearth.lib.io.github.thatkawaiisam.assemble;

import org.bukkit.entity.Player;

import java.util.List;

public interface AssembleAdapter {

	String getTitle(Player player);

	List<String> getLines(Player player);

}
