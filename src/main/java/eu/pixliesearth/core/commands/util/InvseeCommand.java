package eu.pixliesearth.core.commands.util;

import eu.pixliesearth.localization.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InvseeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            Lang.ONLY_PLAYERS_EXEC.send(sender);
            return false;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("earth.invsee")) {
            Lang.NO_PERMISSIONS.send(player);
            return false;
        }
        if (args.length == 0) {
            Lang.WRONG_USAGE.send(player, "%USAGE%;/invsee <player>");
            return false;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            Lang.PLAYER_DOES_NOT_EXIST.send(player);
            return false;
        }
        player.openInventory(target.getInventory());
        return false;
    }

}
