package eu.pixliesearth.core.commands;

import eu.pixliesearth.localization.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.ThreadLocalRandom;

public class SuicideCommand implements CommandExecutor {

    private final Lang[] messages = new Lang[]{
            Lang.SMSG_1,
            Lang.SMSG_2,
            Lang.SMSG_3
    };

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("This command is only executable by a player.");
            return false;
        }
        Player p = (Player) sender;
        if(!(p.hasPermission("earth.suicide"))){
            p.sendMessage(Lang.NO_PERMISSIONS.get(sender));
            return false;
        }

        int randomNum = ThreadLocalRandom.current().nextInt(0, messages.length);
        Lang message = messages[randomNum];
        p.setHealth(0);
        for (Player player : Bukkit.getOnlinePlayers())
            player.sendMessage(message.get(player).replace("%PLAYER%", sender.getName()));

        return false;
    }
}
