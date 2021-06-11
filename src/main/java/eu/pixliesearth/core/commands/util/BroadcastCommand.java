package eu.pixliesearth.core.commands.util;

import eu.pixliesearth.discord.MiniMick;
import eu.pixliesearth.localization.Lang;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.awt.*;
import java.util.StringJoiner;

public class BroadcastCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender.hasPermission("earth.chat.broadcast"))){
            sender.sendMessage(Lang.NO_PERMISSIONS.get(sender));
            return false;
        }
        if (args.length == 0) {
            sender.sendMessage(Lang.WRONG_USAGE.get(sender).replace("%USAGE%", "/broadcast <message>"));
            return false;
        }

        StringJoiner sj = new StringJoiner(" ");
        for (String s1 : args)
            sj.add(s1);

        String message = sj.toString();
        Bukkit.broadcastMessage("§aBROADCAST §8| §7" + message);
        for(Player players : Bukkit.getOnlinePlayers())
            players.playSound(players.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1 );
        broadcastDiscord(message, sender);
        return false;
    }

    public static void broadcastDiscord(String message, CommandSender sender) {
        MiniMick.getApi().getTextChannelById("783409314118369280").get().sendMessage(new EmbedBuilder()
                .setTitle("BROADCAST")
                .setDescription(ChatColor.stripColor(message))
                .setFooter("MiniMick powered by PixliesEarth", "https://minotar.net/avatar/" + sender.getName())
                .setColor(Color.YELLOW)
                .setTimestampToNow()
        );
    }

}
