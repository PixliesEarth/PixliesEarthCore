package eu.pixliesearth.discord.commands;

import eu.pixliesearth.discord.DiscordCommand;
import eu.pixliesearth.utils.Methods;
import org.bukkit.Bukkit;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DiscordServer extends DiscordCommand {

    public DiscordServer() {
        super("server", "serverinfo");
    }

    @Override
    public void run(MessageCreateEvent event) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("PixliesNet");
        embed.setThumbnail(event.getServer().get().getIcon().get());
        embed.setDescription("Think different, think new.");
        embed.addInlineField("Players", Bukkit.getOnlinePlayers().size()+"");
        embed.addInlineField("TPS", Methods.round(Bukkit.getTPS()[0], 2)+"");
        Runtime runtime = Runtime.getRuntime();
        System.gc();
        embed.addInlineField("Ram usage", (runtime.totalMemory() - runtime.freeMemory()) / 1048576L +  " MB / " + runtime.totalMemory() / 1048576L + " MB");

        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTimeInMillis(instance.getUptime());
        int hours = calendar.get(Calendar.HOUR);
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);

        embed.addInlineField("Uptime", hours + " hours, " + minutes + " minutes, and " + seconds + " seconds");
        embed.setFooter("Requested by " + event.getMessageAuthor().getDisplayName() + " (" + event.getMessageAuthor().getDiscriminatedName() + ")", event.getMessageAuthor().getAvatar());
        event.getChannel().sendMessage(embed);
    }

}
