package eu.pixliesearth.core.commands.player;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class RealNameCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Profile target = Profile.getByNickname(ChatColor.stripColor(args[0]));
        if (!sender.hasPermission("earth.realname")) {
            Lang.NO_PERMISSIONS.send(sender);
            return false;
        }
        if (target == null) {
            Lang.PLAYER_DOES_NOT_EXIST.send(sender);
            return false;
        }
        sender.sendMessage("§6" + target.getNickname() + "§7's real name: " + target.getAsOfflinePlayer().getName());
        return false;
    }

}
