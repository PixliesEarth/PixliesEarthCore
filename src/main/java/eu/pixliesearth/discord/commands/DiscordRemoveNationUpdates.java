package eu.pixliesearth.discord.commands;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.discord.DiscordCommand;
import eu.pixliesearth.discord.MiniMick;
import eu.pixliesearth.discord.MiniMickServerConfig;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;

public class DiscordRemoveNationUpdates extends DiscordCommand {

    public DiscordRemoveNationUpdates() {
        super("removenation");
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
                            .setTitle("You need to be the leader of your nation to disconnect it's update channel.")
                            .setColor(Color.RED)
                            .setAuthor(author)
            );
            return;
        }
        MiniMick.getConfigs().remove(profile.getNationId());
        event.getMessage().reply(
                new EmbedBuilder()
                        .setTitle("Successfully removed " + profile.getCurrentNation().getName() + "'s update channel.")
                        .setColor(Color.RED)
                        .setAuthor(author)
        );
    }

}
