package eu.pixliesearth.discord;

import eu.pixliesearth.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;

public class ChatEvent implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        TextChannel channel = MiniMick.getApi().getTextChannelById(Main.getInstance().getConfig().getString("chatchannel")).get();
        if (channel != null)
            channel.sendMessage(new EmbedBuilder()
                                .setAuthor(event.getPlayer().getName(), "", "https://minotar.net/avatar/" + event.getPlayer().getName())
                                .setTitle(event.getMessage()));
    }

}
