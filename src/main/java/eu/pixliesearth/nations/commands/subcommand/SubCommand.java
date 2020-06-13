package eu.pixliesearth.nations.commands.subcommand;

import eu.pixliesearth.Main;
import org.bukkit.command.CommandSender;

import java.util.Map;

public interface SubCommand {

    Main instance = Main.getInstance();

    String[] aliases();

    Map<String, Integer> autoCompletion();

    boolean staff();

    boolean execute(CommandSender sender, String[] args);

}
