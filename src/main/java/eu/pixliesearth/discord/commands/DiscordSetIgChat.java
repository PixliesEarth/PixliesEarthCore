package eu.pixliesearth.discord.commands;

import eu.pixliesearth.Main;
import eu.pixliesearth.discord.DiscordCommand;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.event.message.MessageCreateEvent;

public class DiscordSetIgChat extends DiscordCommand {

    public DiscordSetIgChat() {
        super("setigchat");
    }

    public boolean onlyPixlies() {
        return true;
    }

    @Override
    public void run(MessageCreateEvent event) {
        if (!event.getServer().get().hasPermission(event.getMessageAuthor().asUser().get(), PermissionType.ADMINISTRATOR)) {
            event.getChannel().sendMessage("<@" + event.getMessageAuthor().getIdAsString() + ">, Insufficient permissions.");
            return;
        }
        Main.getInstance().getConfig().set("chatchannel", event.getChannel().getIdAsString());
        Main.getInstance().saveConfig();
        Main.getInstance().reloadConfig();
        event.getChannel().sendMessage("<@" + event.getMessageAuthor().getIdAsString() + ">, successfully set the chat-channel.");
    }

}
