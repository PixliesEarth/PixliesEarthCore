package eu.pixliesearth.discord.commands;

import eu.pixliesearth.discord.DiscordCommand;
import eu.pixliesearth.utils.Methods;
import org.bukkit.Bukkit;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class DiscordServer extends DiscordCommand {

    public DiscordServer() {
        super("server", "serverinfo", "earth");
    }

    @Override
    public void run(MessageCreateEvent event) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("PixliesNet - Earth");
        embed.setColor(Color.CYAN);
        embed.setThumbnail(event.getServer().get().getIcon().get());
        embed.addInlineField("Players", Bukkit.getOnlinePlayers().size()+"");
        embed.addInlineField("TPS", Methods.round(Bukkit.getTPS()[0], 2)+"");
        Runtime runtime = Runtime.getRuntime();
        System.gc();
        embed.addInlineField("Ram usage", (runtime.totalMemory() - runtime.freeMemory()) / 1048576L +  " MB / " + runtime.totalMemory() / 1048576L + " MB");

        long millis = 0;

        String time = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), // The change is in this line
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));

        embed.addInlineField("Uptime", time);
        embed.setFooter("Requested by " + event.getMessageAuthor().getDisplayName() + " (" + event.getMessageAuthor().getDiscriminatedName() + ")", event.getMessageAuthor().getAvatar());
        event.getChannel().sendMessage(embed);
    }

}
