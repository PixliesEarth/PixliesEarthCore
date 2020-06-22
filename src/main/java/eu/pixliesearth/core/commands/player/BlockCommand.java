package eu.pixliesearth.core.commands.player;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BlockCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            Lang.ONLY_PLAYERS_EXEC.send(sender);
            return false;
        }
        Player player = (Player) sender;
        Profile profile = Main.getInstance().getProfile(player.getUniqueId());
        UUID targetUUID = Bukkit.getPlayerUniqueId(args[0]);
        if (targetUUID == null) {
            Lang.PLAYER_DOES_NOT_EXIST.send(player);
            return false;
        }
        if (args[0].equalsIgnoreCase(player.getName())) {
            Lang.YOU_CANT_BLOCK_YOURSELF.send(player);
            return false;
        }
        if (profile.getBlocked().contains(targetUUID.toString())) {
            Lang.PLAYER_ALREADY_BLOCKED.send(player);
            return false;
        }
        profile.getBlocked().add(targetUUID.toString());
        profile.save();
        Lang.BLOCKED_PLAYER.send(player, "%PLAYER%;" + args[0]);
        return false;
    }

}
