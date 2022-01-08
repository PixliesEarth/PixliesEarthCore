package eu.pixliesearth.discord.commands;

import eu.pixliesearth.discord.DiscordCommand;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.channel.ServerThreadChannel;
import org.javacord.api.event.message.MessageCreateEvent;

public class DiscordCloseSuggestion extends DiscordCommand {

    public DiscordCloseSuggestion() {
        super("close");
    }

    @Override
    public void run(MessageCreateEvent event) {
        if (event.getServerThreadChannel().isPresent()) {
            ServerThreadChannel thread = event.getServerThreadChannel().get();
            final ServerTextChannel channel = thread.getParent();
            if (thread.getParent().getId() == instance.getMiniMick().getSuggestionChannel().getId()) {
                if (event.getMessageAuthor().isServerAdmin()) {
                    thread.delete();
                    channel.delete();
                }
            }
        }
    }

}
