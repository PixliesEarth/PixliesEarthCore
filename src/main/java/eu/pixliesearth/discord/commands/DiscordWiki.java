package eu.pixliesearth.discord.commands;

import eu.pixliesearth.discord.DiscordCommand;
import org.javacord.api.event.message.MessageCreateEvent;

public class DiscordWiki extends DiscordCommand {

    public DiscordWiki() {
        super("wiki");
    }

    @Override
    public void run(MessageCreateEvent event) {
        String[] split = event.getMessageContent().split(" ");
        if (split.length != 2) {
            reply(event, "Usage: /wiki PAGE");
            return;
        }
        reply(event, "https://github.com/PixliesEarth/PixliesNet/wiki/" + split[1]);
    }

}
