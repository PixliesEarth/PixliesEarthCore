package eu.pixliesearth.core.commands;

import eu.pixliesearth.discord.MiniMick;
import org.bukkit.Bukkit;
import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static jdk.nashorn.internal.objects.NativeArray.join;

public class BroadcastCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!(commandSender.hasPermission("earth.broadcast"))){
            commandSender.sendMessage("§aEARTH §8| §cInsufficient permissions.");
            return false;
        }

        String message = join(args, ' ');
        Bukkit.broadcastMessage("§aBROADCAST §8| §7" + message);
        for(Player players : Bukkit.getOnlinePlayers()){
            players.playSound(players.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1 );
        }
        MiniMick.getApi().getChannelById("589958750866112512").get().asTextChannel().get().sendMessage("BROADCAST | " + message);
        return false;
    }
}
