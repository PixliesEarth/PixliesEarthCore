package eu.pixliesearth.nations.commands.subcommand;

import eu.pixliesearth.Main;
import eu.pixliesearth.localization.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class SubCommand {

    protected final static Main instance = Main.getInstance();

    public String[] aliases() { return null; }

    public Map<String, Integer> autoCompletion() { return null; }

    public boolean staff() { return false; }

    public boolean execute(CommandSender sender, String[] args) { return false; }

    protected boolean checkIfPlayer(CommandSender sender) {
        if (!(sender instanceof Player)) {
            Lang.ONLY_PLAYERS_EXEC.send(sender);
            return false;
        }
        return true;
    }

}
