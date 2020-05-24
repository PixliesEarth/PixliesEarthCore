package eu.pixliesearth.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!(commandSender instanceof Player)){
            commandSender.sendMessage("§aEARTH §8| §7Only executable by a player.");
            return false;
        }
        Player p = (Player) commandSender;
        if(!(p.hasPermission("earth.fly"))){
            p.sendMessage("§aEARTH §8| §cInsufficient permissions");
            return false;
        }
        if(args.length == 0) {
            if (p.getAllowFlight() || p.isFlying()) {
                if(p.getGameMode().equals(GameMode.SPECTATOR)){
                    p.sendMessage("§aEARTH §8| §cYou cant disable fly while in spectator for your own safety...");
                    return false;
                }
                p.setAllowFlight(false);
                p.setFlying(false);
                p.sendMessage("§aEARTH §8| §cDisabled §7fly!");
            } else {
                p.setAllowFlight(true);
                p.setFlying(true);
                p.sendMessage("§aEARTH §8| §aEnabled §7fly!");
            }
        }
            if(args.length == 1){
                if(Bukkit.getPlayer(args[0]) == null){
                p.sendMessage("§aEARTH §8| §6" + args[0] + " §cis not online!");
                return false;
            }
            Player player = Bukkit.getPlayer(args[0]);
            if(player.getAllowFlight() || player.isFlying()){
                if(player.getGameMode().equals(GameMode.SPECTATOR)){
                    p.sendMessage("§aEARTH §8| §cYou cant disable §6" + player.getName() + "'s §cfly while he is in spectator!");
                    return false;
                }
                player.setAllowFlight(false);
                player.setFlying(false);
                p.sendMessage("§aEARTH §8| §cDisabled §7fly for §6" + player.getName() + "§7!");
                player.sendMessage("§aEARTH §8| §6" + p.getName() + " §cdisabled §7fly for you!");
            }else{
                player.setAllowFlight(true);
                player.setFlying(true);
                p.sendMessage("§aEARTH §8| §aEnabled §7 fly for §6" + player.getName());
                player.sendMessage("§aEARTH §8| §6" + p.getName() + " §aenabled §7 fly for you!");
            }

            }
            return false;
        }

    }

