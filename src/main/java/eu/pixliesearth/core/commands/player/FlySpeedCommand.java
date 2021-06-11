package eu.pixliesearth.core.commands.player;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlySpeedCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!(commandSender instanceof Player)){
            commandSender.sendMessage("§aEARTH §8| §7Only executable by a player.");
            return false;
        }
        Player p = (Player) commandSender;

        if(!(p.hasPermission("earth.flyspeed"))) {
            p.sendMessage("§aEARTH §8| §cInsufficient permissions");
            return false;
        }
        if(args.length == 0){
            p.sendMessage("§aEARTH §8| §cYou did not specify a valid number!");
        }
        if(args.length == 1){
            if(!(isFloat(args[0]))){
                p.sendMessage("§aEARTH §8| §b" + args[0] + " §cis  not a valid number!");
                return false;
            }
            if(Float.parseFloat(args[0]) > 10 || Float.parseFloat(args[0]) < 0){
                p.sendMessage("§aEARTH §8| §b" + args[0] + " §cis not a valid number!");
                return false;
            }

            float flyspeed = Float.parseFloat(args[0]) / 10;
            p.setFlySpeed((flyspeed));
            if(!(p.getAllowFlight())) p.setAllowFlight(true);

            p.sendMessage("§aEARTH §8| §7You set your flyspeed to §b" + args[0] + "§7!");
        }else if(args.length == 2){
            if(Bukkit.getPlayer(args[0]) == null) {
                p.sendMessage("§aEARTH §8| §6" + args[0] + " §f§cis not online!");
                return false;
            }
            if(!(isFloat(args[1]))){
                p.sendMessage("§aEARTH §8| §b" + args[0] + " §c is not a valid number!");
                return false;
            }
            if(Float.parseFloat(args[1]) > 10 || Float.parseFloat(args[1]) < 0){
                p.sendMessage("§aEARTH §8| §b" + args[0] + " §cis not a valid number!");
                return false;
            }

            Player player = Bukkit.getPlayer(args[0]);
            float flyspeed = Float.parseFloat(args[1]) / 10;
            player.setFlySpeed(flyspeed);
            if(!(player.getAllowFlight())) player.setAllowFlight(true);
            p.sendMessage("§aEARTH §8| §7You set §6" + player.getName() + " §7flyspeed to §b" + args[1] + "§7!");
            player.sendMessage("§aEARTH §8| §6" + p.getName() + " §7set your flyspeed to §b" + args[1] + "§7!");

        }
        return false;
    }

    public static boolean isFloat(String s) {
        try {
            Float.parseFloat(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        return true;
    }
}
