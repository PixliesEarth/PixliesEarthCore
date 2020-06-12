package eu.pixliesearth.core.commands.player;

import eu.pixliesearth.localization.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpHereCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player)){
           sender.sendMessage(Lang.ONLY_PLAYERS_EXEC.get(sender));
           return false;
        }
        Player p = (Player) sender;
        if(!p.hasPermission("earth.tphere")){
            p.sendMessage(Lang.NO_PERMISSIONS.get(p));
            return false;
        }
        if(args.length == 0){
            p.sendMessage(Lang.WRONG_USAGE.get(p).replace("%USAGE%", "/tphere <player>"));
            return false;
        }
        if(Bukkit.getPlayer(args[0]) == null){
            p.sendMessage(Lang.PLAYER_DOES_NOT_EXIST.get(p));
            return false;
        }
        Player player = Bukkit.getPlayer(args[0]);
        assert player != null;
        player.teleport(p.getLocation());
        p.sendMessage(Lang.TP_HERE.get(p).replace("%PLAYER%", player.getName()));



        return false;
    }
}
