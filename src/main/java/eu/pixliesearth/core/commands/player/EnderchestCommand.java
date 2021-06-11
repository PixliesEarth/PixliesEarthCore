package eu.pixliesearth.core.commands.player;

import eu.pixliesearth.localization.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EnderchestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Lang.ONLY_PLAYERS_EXEC.get(sender));
            return false;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("earth.enderchest")) {
            player.sendMessage(Lang.NO_PERMISSIONS.get(player));
            return false;
        }
        player.openInventory(player.getEnderChest());
        return false;
    }

}
