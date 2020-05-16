package eu.pixliesearth.nations.commands.subcommand;

import eu.pixliesearth.Main;
import org.bukkit.command.CommandSender;

public interface SubCommand {

    Main instance = Main.getInstance();

    String[] aliases();

    boolean staff();

    boolean execute(CommandSender sender, String[] args);

}
