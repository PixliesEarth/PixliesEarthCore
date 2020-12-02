package eu.pixliesearth.core.commands.player;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
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
                    player.setDisplayName(player.getName());
                    return false;
                }
                if (Bukkit.getOfflinePlayerIfCached(args[0]) != null) {
                    Lang.CANT_NICK_LIKE_A_PLAYER.send(player);
                    return false;
                }
                if (Profile.getByNickname(args[0]) != null) {
                    Lang.CANT_NICK_LIKE_A_PLAYER.send(player);
                    return false;
                }
                if (!player.hasPermission("earth.nick") && !player.hasPermission("earth.nick.colours")) {
                    Lang.NO_PERMISSIONS.send(player);
                    return false;
                }
                String nick = args[0];
                if (args[0].contains("&") && !player.hasPermission("earth.nick.colours")) nick = nick.replace("&", "");
                if (nick.length() > 15 || nick.length() < 3) {
                    Lang.INVALID_INPUT.send(player);
                    return false;
                }
                ChatColor.translateAlternateColorCodes('&', nick);
                profile.setNickname(nick);
                profile.save();
                profile.backup();
                player.setDisplayName(nick);
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
                    if (target.isOnline())
                        target.getAsOfflinePlayer().getPlayer().setDisplayName(target.getAsOfflinePlayer().getName());
                    return false;
                }
                if (getPlayer(args[0]).hasPlayedBefore()) {
                    Lang.CANT_NICK_LIKE_A_PLAYER.send(sender);
                    return false;
                }
                if (Profile.getByNickname(args[0]) != null) {
                    Lang.CANT_NICK_LIKE_A_PLAYER.send(sender);
                    return false;
                }
                String nick2 = args[0];
                ChatColor.translateAlternateColorCodes('&', nick2);
                target.setNickname(nick2);
                target.save();
                target.backup();
                if (target.isOnline())
                    target.getAsOfflinePlayer().getPlayer().setDisplayName(nick2);
                Lang.CHANGED_PLAYER_NICKNAME.send(sender, "%NICK%;" + nick2, "%PLAYER%;" + target.getAsOfflinePlayer().getName());
                break;
        }
        return false;
    }
    private OfflinePlayer getPlayer(String name) {
    	return Bukkit.getOfflinePlayer(Bukkit.getPlayerUniqueId(name));
    }
}
