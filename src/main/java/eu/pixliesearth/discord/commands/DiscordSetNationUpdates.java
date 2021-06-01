package eu.pixliesearth.discord.commands;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.discord.DiscordCommand;
import eu.pixliesearth.discord.MiniMick;
import eu.pixliesearth.discord.MiniMickServerConfig;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;

public class DiscordSetNationUpdates extends DiscordCommand {

    public DiscordSetNationUpdates() {
        super("setnation");
    }

    @Override
    public void run(MessageCreateEvent event) {
        MessageAuthor author = event.getMessageAuthor();
        Profile profile = Profile.getByDiscord(author.getIdAsString());
        if (profile == null) {
            event.getMessage().reply(
                    new EmbedBuilder()
                            .setTitle("You have to connect your discord to your minecraft account to perform this command.")
                            .setColor(Color.RED)
                            .setDescription("Perform /link on pixlies.net")
                            .setAuthor(author)
            );
            return;
        }
        if (!profile.isLeader()) {
            event.getMessage().reply(
                    new EmbedBuilder()
                            .setTitle("You need to be the leader of your nation to connect it to this channel.")
                            .setColor(Color.RED)
                            .setAuthor(author)
            );
            return;
        }
        MiniMick.getConfigs().put(profile.getNationId(), new MiniMickServerConfig(event.getServer().get().getIdAsString(), event.getChannel().getIdAsString(), profile.getNationId()));
        event.getMessage().reply(
                new EmbedBuilder()
                        .setTitle("Successfully set this channel as the updates channel for the nation " + profile.getCurrentNation().getName() + ".")
                        .setColor(Color.CYAN)
                        .setAuthor(author)
        );
    }

}
