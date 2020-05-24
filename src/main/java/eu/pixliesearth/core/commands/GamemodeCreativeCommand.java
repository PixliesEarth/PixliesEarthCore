package eu.pixliesearth.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCreativeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!(commandSender instanceof Player)){
            commandSender.sendMessage("This comamnd can only be executed as a player!");
            return false;
        }
        Player p = (Player) commandSender;
        if(!(p.hasPermission("earth.creative"))){
            p.sendMessage("§aEARTH §8| §cInsufficient permissions");
            return false;
        }
        if(args.length == 0){
            p.setGameMode(GameMode.CREATIVE);
            p.sendMessage("§aEARTH §8| §7You changed your gamemode to §aCreative§7!");
            return false;
        }
        if(args.length == 1){
            if(Bukkit.getPlayer(args[0]) == null) {
                p.sendMessage("§aEARTH §8| §6" + args[0] + " §f§cis not online!");
                return false;
            }
            Player player = Bukkit.getPlayer(args[0]);
            player.setGameMode(GameMode.CREATIVE);
            p.sendMessage("§aEARTH §8| §7You changed §6" + player.getName() + " §7gamemode to §aCreative§7!");
            player.sendMessage("§aEARTH §8| §6" + p.getName() + " §7set your gamemode to §aCreative§7!");
        }
    return false;
    }
}
