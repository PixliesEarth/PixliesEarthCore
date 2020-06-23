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
                if (args[0].equalsIgnoreCase("off")) {
                    Lang.NICKNAME_TURNED_OFF.send(player);
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
                Profile profile = Main.getInstance().getProfile(player.getUniqueId());
                profile.setNickname(nick);
                profile.save();
                Lang.CHANGED_NICKNAME.send(player, "%NICK%;" + nick);
                break;
                //TODO OTHERS
        }
        return false;
    }

}
