package eu.pixliesearth.core.commands.player;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class HealCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
    if(!(commandSender instanceof Player)){
        commandSender.sendMessage("§aEARTH §8| §7Only executable by a player.");
        return false;
    }
        Player p = (Player) commandSender;
        if(!(p.hasPermission("earth.heal"))){
            p.sendMessage("§aEARTH §8| §cInsufficient permissions");
            return false;
        }

        if(args.length == 0){
            double healed = 20 - p.getHealth();
            double preresult = healed / 2;
            String result = String.format(preresult + "","##.##");
            p.sendMessage("§aEARTH §8| §7You healed §b" + result + " §7heart(s)!");
            p.setHealth(20.0);
            p.setFoodLevel(20);
        }
        if(args.length == 1){
            if(Bukkit.getPlayer(args[0]) == null){
                p.sendMessage("§aEARTH §8| §6" + args[0] + " §f§cis not online!");
                return false;
            }else{
                if(Objects.requireNonNull(Bukkit.getPlayer(args[0])).isDead()){
                    p.sendMessage("§aEARTH §8| §7You §ccant §7resurrect people...");
                }else{
                    double healed = 20 - Objects.requireNonNull(Bukkit.getPlayer(args[0])).getHealth();
                    double preresult = healed / 2;
                    String result = String.format(preresult + "","##.##");
                    Objects.requireNonNull(Bukkit.getPlayer(args[0])).setHealth(20.0);
                    Objects.requireNonNull(Bukkit.getPlayer(args[0])).setFoodLevel(20);
                    p.sendMessage("§aEARTH §8| §7You healed §6" + Objects.requireNonNull(Bukkit.getPlayer(args[0])).getName() + "§7 for §f§b" + result + " §7heart(s)!");
                    Objects.requireNonNull(Bukkit.getPlayer(args[0])).sendMessage("§aEARTH §8| §6" + p.getName() + " §7healed §b" + result + " §7heart(s) for you!");

                    return false;
                }
            }
        }

        return false;
    }
}
