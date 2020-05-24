package eu.pixliesearth.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SuicideCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!(commandSender instanceof Player)){
            commandSender.sendMessage("This command is only executable by a player.");
            return false;
        }
        Player p = (Player) commandSender;
        if(!(p.hasPermission("earth.suicide"))){
            p.sendMessage("§aEARTH §8| §cInsufficient permissions.");
            return false;
        }
            p.setHealth(0);
            Bukkit.broadcastMessage("§aEARTH §8| §6" + p.getName() + " §7stabbed himself!");



        return false;
    }
}
