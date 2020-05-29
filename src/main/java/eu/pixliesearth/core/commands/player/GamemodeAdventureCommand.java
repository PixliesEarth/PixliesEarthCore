package eu.pixliesearth.core.commands.player;

import eu.pixliesearth.localization.Lang;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeAdventureCommand implements CommandExecutor {

    GameMode gamemode = GameMode.ADVENTURE;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("This comamnd can only be executed as a player!");
            return false;
        }
        Player p = (Player) sender;
        if(!(p.hasPermission("earth." + gamemode.name().toLowerCase()))){
            p.sendMessage(Lang.NO_PERMISSIONS.get(sender));
            return false;
        }
        if(args.length == 0){
            p.setGameMode(gamemode);
            p.sendMessage(Lang.GAMEMODE_CHANGED.get(sender).replace("%GAMEMODE%", gamemode.name()));
            return false;
        }
        if(args.length == 1){
            if(Bukkit.getPlayer(args[0]) == null) {
                p.sendMessage(Lang.PLAYER_DOES_NOT_EXIST.get(sender));
                return false;
            }
            Player player = Bukkit.getPlayer(args[0]);
            player.setGameMode(GameMode.CREATIVE);
            p.sendMessage(Lang.GAMEMODE_CHANGED_OTHER.get(sender).replace("%GAMEMODE%", gamemode.name()).replace("%PLAYER%", player.getName()));
            player.sendMessage(Lang.GAMEMODE_CHANGED_BY_OTHER.get(player).replace("%PLAYER%", p.getName()).replace("%GAMEMODE%", gamemode.name()));
        }
        return false;
    }

}
