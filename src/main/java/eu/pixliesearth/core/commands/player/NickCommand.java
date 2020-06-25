package eu.pixliesearth.core.commands.player;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class NickCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        switch (args.length) {
            case 1:
                if (!(sender instanceof Player)) {
                    Lang.ONLY_PLAYERS_EXEC.send(sender);
                    return false;
                }
                Player player = (Player) sender;
                Profile profile = Main.getInstance().getProfile(player.getUniqueId());
                if (args[0].equalsIgnoreCase("off")) {
                    Lang.NICKNAME_TURNED_OFF.send(player);
                    profile.setNickname("");
                    profile.save();
                    return false;
                }
                if (Bukkit.getPlayerUniqueId(args[0]) != null) {
                    Lang.CANT_NICK_LIKE_A_PLAYER.send(player);
                    return false;
                }
                if (!player.hasPermission("earth.nick") && !player.hasPermission("earth.nick.colours")) {
                    Lang.NO_PERMISSIONS.send(player);
                    return false;
                }
                String nick = args[0];
                if (args[0].contains("&") && !player.hasPermission("earth.nick.colours")) nick = nick.replace("&", "");
                if (nick.length() > 15 || nick.length() < 3 || !StringUtils.isAlphanumeric(nick)) {
                    Lang.INVALID_INPUT.send(player);
                    return false;
                }
                ChatColor.translateAlternateColorCodes('&', nick);
                profile.setNickname(nick);
                profile.save();
                Lang.CHANGED_NICKNAME.send(player, "%NICK%;" + nick);
                break;
            case 2:
                if (!sender.hasPermission("earth.nick.others")) {
                    Lang.NO_PERMISSIONS.send(sender);
                    return false;
                }
                UUID targetUUID = Bukkit.getPlayerUniqueId(args[1]);
                if (targetUUID == null) {
                    Lang.PLAYER_DOES_NOT_EXIST.send(sender);
                    return false;
                }
                Profile target = Main.getInstance().getProfile(targetUUID);
                if (args[0].equalsIgnoreCase("off")) {
                    Lang.NICKNAME_TURNED_OFF.send(sender);
                    target.setNickname("");
                    target.save();
                    return false;
                }
                if (Bukkit.getPlayerUniqueId(args[0]) != null) {
                    Lang.CANT_NICK_LIKE_A_PLAYER.send(sender);
                    return false;
                }
                String nick2 = args[0];
                if (nick2.length() > 15 || nick2.length() < 3 || !StringUtils.isAlphanumeric(nick2)) {
                    Lang.INVALID_INPUT.send(sender);
                    return false;
                }
                ChatColor.translateAlternateColorCodes('&', nick2);
                target.setNickname(nick2);
                target.save();
                Lang.CHANGED_NICKNAME.send(sender, "%NICK%;" + nick2);
                break;
        }
        return false;
    }

}
