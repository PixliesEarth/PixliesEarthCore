package eu.pixliesearth.discord.commands;

import eu.pixliesearth.discord.DiscordCommand;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

public class DiscordInvite extends DiscordCommand {

    public DiscordInvite() {
        super("invite");
    }

    @Override
    public void run(MessageCreateEvent event) {
        event.getMessage().reply(new EmbedBuilder()
                .setTitle("Click me to invite me to your discord")
                .setUrl(event.getApi().createBotInvite())
                .setAuthor(event.getMessageAuthor())
        );
    }

}
