package eu.pixliesearth.core.commands.player;

import eu.pixliesearth.localization.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class SudoCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!sender.hasPermission("earth.sudo")){
            sender.sendMessage(Lang.NO_PERMISSIONS.get(sender));
            return false;
        }
        if(args.length == 0){
            sender.sendMessage(Lang.WRONG_USAGE.get(sender).replace("%USAGE%", "/sudo <player> <command>"));
            return false;
        }
        if(args.length == 1){
            sender.sendMessage(Lang.WRONG_USAGE.get(sender).replace("%USAGE%", "/sudo <player> <command>"));
            return false;
        }
        if(args.length >= 2){
            if(Bukkit.getPlayer(args[0]) == null){
                sender.sendMessage(Lang.PLAYER_DOES_NOT_EXIST.get(sender));
                return false;
            }
            Player player = Bukkit.getPlayerExact(args[0]);
            List<String> list = new ArrayList<>(Arrays.asList(args));
            list.remove(args[0]);
            String[] commands = list.toArray(new String[0]);
            StringJoiner sj = new StringJoiner(" ");
            for(String strings : commands){
                sj.add(strings);
            }
            String execommand = sj.toString();

            assert player != null;
            Bukkit.dispatchCommand(player, execommand);
            sender.sendMessage(Lang.SUDO.get(sender).replace("%PLAYER%", player.getName()).replace("%COMMAND%", "/" +execommand));
        }
        return false;
    }
}
