package eu.pixliesearth.nations.commands.subcommand;

import eu.pixliesearth.Main;
import eu.pixliesearth.localization.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class SubCommand {

    protected final static Main instance = Main.getInstance();

    public String[] aliases() { return new String[]{}; }

    public Map<String, Integer> autoCompletion() { return new HashMap<>(); }

    public String getSyntax() {
        return "§cThere is no explanation for this command yet. If you feel that this is necessarily, please contact the Pixlies Network staff, so we can explain this command more.";
    }

    public boolean staff() { return false; }

    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) { return false; }

    protected boolean checkIfPlayer(CommandSender sender) {
        if (!(sender instanceof Player)) {
            Lang.ONLY_PLAYERS_EXEC.send(sender);
            return false;
        }
        return true;
    }

    public void sendSyntax(CommandSender sender, String command) {
        sender.sendMessage("§7Correct usage of §b" + command + "§7: ");
        sender.sendMessage(getSyntax());
    }

}
