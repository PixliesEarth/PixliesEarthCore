package eu.pixliesearth.core.commands.util;

import eu.pixliesearth.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffCommand implements CommandExecutor {

    private final Main instance = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command is only executable by a player.");
            return false;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("earth.admin")) {
            player.sendMessage("§aEARTH §8| §cInsufficient permissions.");
            return false;
        }
        if (instance.getUtilLists().staffMode.contains(player.getUniqueId())) {
            instance.getUtilLists().staffMode.remove(player.getUniqueId());
            player.sendMessage("§aEARTH §8| §cDisabled §7staffmode.");
        } else {
            instance.getUtilLists().staffMode.add(player.getUniqueId());
            player.sendMessage("§aEARTH §8| §aenabled §7staffmode.");
        }
        return false;
    }

}
