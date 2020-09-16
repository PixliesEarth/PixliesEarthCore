package eu.pixliesearth.nations.commands.subcommand;

import eu.pixliesearth.Main;
import eu.pixliesearth.localization.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public interface SubCommand {

    Main instance = Main.getInstance();

    String[] aliases();

    Map<String, Integer> autoCompletion();

    boolean staff();

    boolean execute(CommandSender sender, String[] args);

    default boolean checkIfPlayer(CommandSender sender) {
        if (!(sender instanceof Player)) {
            Lang.ONLY_PLAYERS_EXEC.send(sender);
            return false;
        }
        return true;
    }

}
