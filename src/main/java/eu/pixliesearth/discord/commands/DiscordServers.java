package eu.pixliesearth.discord.commands;

import eu.pixliesearth.discord.DiscordCommand;
import eu.pixliesearth.discord.MiniMick;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.server.invite.RichInvite;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.List;

public class DiscordServers extends DiscordCommand {

    public DiscordServers() {
        super("servers");
    }

    @Override
    public void run(MessageCreateEvent event) {
        if (!event.getMessageAuthor().getIdAsString().equals("280798475946426369")) {
            event.getMessage().reply("Only mick can execute this command \uD83D\uDE20");
            return;
        }
        StringBuilder builder = new StringBuilder();
        for (Server server : MiniMick.getApi().getServers()) {
            builder.append("* ").append(server.getName()).append(" | ");
            try {
                builder.append(server.getInvites().get().iterator().next()).append("\n");
            } catch (Exception e) {
                builder.append("No invite :(\n");
            }
        }
        event.getMessage().reply(
                "```\n" +
                        builder +
                        "```"
        );
    }

}
