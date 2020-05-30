package eu.pixliesearth.core.commands.player;

import eu.pixliesearth.localization.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WalkSpeedCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(Lang.ONLY_PLAYERS_EXEC.get(sender));
            return false;
        }
        Player p = (Player) sender;

        if(!(p.hasPermission("earth.walkspeed"))) {
            p.sendMessage(Lang.NO_PERMISSIONS.get(sender));
            return false;
        }
        if(args.length == 0){
            p.sendMessage("§aEARTH §8| §cYou did not specify a valid number!");
        }
        if(args.length == 1){
            if(args[0].equalsIgnoreCase("default") || args[0].equalsIgnoreCase("normal")){
                p.setWalkSpeed(0.2F);
                p.sendMessage("§aEARTH §8| §7You set your walkspeed to the default value!");
                return false;
            }
            if(!(isFloat(args[0]))){
                p.sendMessage("§aEARTH §8| §b" + args[0] + " §cis  not a valid number!");
                return false;
            }
            if(Float.parseFloat(args[0]) > 10 || Float.parseFloat(args[0]) < 0){
                p.sendMessage("§aEARTH §8| §b" + args[0] + " §cis not a valid number!");
                return false;
            }

            float walkspeed = Float.parseFloat(args[0]) / 10;
            p.setWalkSpeed((walkspeed));
            p.sendMessage("§aEARTH §8| §7You set your walkspeed to §b" + args[0] + "§7!");
        }else if(args.length == 2){
            if(Bukkit.getPlayer(args[0]) == null) {
                p.sendMessage("§aEARTH §8| §6" + args[0] + " §f§cis not online!");
                return false;
            }
            Player player = Bukkit.getPlayer(args[0]);
            if(args[1].equalsIgnoreCase("default") || args[1].equalsIgnoreCase("normal")){
                player.setWalkSpeed(0.2F);
                p.sendMessage("§aEARTH §8| You set §6" + player.getName() + " §7walkspeed to the default value!");
                player.sendMessage("§aEARTH §8| §6" + p.getName() + " §7set your walkspeed to the default value!");
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


            float walkspeed = Float.parseFloat(args[1]) / 10;
            player.setWalkSpeed(walkspeed);
            p.sendMessage("§aEARTH §8| §7You set §6" + player.getName() + " §7walkspeed to §b" + args[1] + "§7!");
            player.sendMessage("§aEARTH §8| §6" + p.getName() + " §7set your walkspeed to §b" + args[1] + "§7!");

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
