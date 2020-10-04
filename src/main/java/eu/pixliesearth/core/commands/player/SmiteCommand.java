package eu.pixliesearth.core.commands.player;

import eu.pixliesearth.localization.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SmiteCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player && !sender.hasPermission("earth.smite")) {
            sender.sendMessage(Lang.NO_PERMISSIONS.get(sender));
            return false;
        }

            if(args.length == 0){
                sender.sendMessage(Lang.WRONG_USAGE.get(sender).replace("%USAGE%", "/smite <player>"));
                return false;
            }
            if(Bukkit.getPlayer(args[0]) == null){
                sender.sendMessage(Lang.PLAYER_DOES_NOT_EXIST.get(sender));
                return false;
            }
            Player player = Bukkit.getPlayer(args[0]);
        player.getWorld().strikeLightning(player.getLocation());

        sender.sendMessage(Lang.STRIKED_PLAYER.get(sender).replace("%PLAYER%", player.getName()));



        return false;
    }
}
