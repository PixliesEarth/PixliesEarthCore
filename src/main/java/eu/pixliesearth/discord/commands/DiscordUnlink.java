package eu.pixliesearth.discord.commands;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.discord.DiscordCommand;
import org.javacord.api.event.message.MessageCreateEvent;

public class DiscordUnlink extends DiscordCommand {

    public DiscordUnlink() {
        super("unlink");
    }

    public void run(MessageCreateEvent event) {
        Profile profile = Profile.getByDiscord(event.getMessageAuthor().getIdAsString());
        if (profile == null) {
            event.getChannel().sendMessage("<@" + event.getMessageAuthor().getIdAsString() + ">, your discord and ingame accounts are not linked!");
            return;
        }
        profile.setDiscord("NONE");
        profile.save();
        profile.backup();
    }

}
