package eu.pixliesearth.discord.commands;

import com.google.gson.GsonBuilder;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.discord.DiscordCommand;
import org.javacord.api.event.message.MessageCreateEvent;

public class DiscordGiveMyData extends DiscordCommand {

    public DiscordGiveMyData() {
        super("givemydata");
    }

    @Override
    public void run(MessageCreateEvent event) {
        Profile profile = Profile.getByDiscord(event.getMessageAuthor().getIdAsString());
        if (profile == null) {
            event.getChannel().sendMessage("<@" + event.getMessageAuthor().getIdAsString() + ">, we don't have any data stored about you in our database.");
            return;
        }
        event.getMessageAuthor().asUser().get().openPrivateChannel().join().sendMessage("**This is the data we currently have in our database:**\n```json\n" + new GsonBuilder().setPrettyPrinting().create().toJson(profile) + "\n```");
    }

}
