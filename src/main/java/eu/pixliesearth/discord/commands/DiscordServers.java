package eu.pixliesearth.discord.commands;

import eu.pixliesearth.discord.DiscordCommand;
import eu.pixliesearth.discord.MiniMick;
import lombok.SneakyThrows;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.server.invite.Invite;
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
                builder.append(server.getInvites().get().iterator().next().getUrl().toString()).append("\n");
            } catch (Exception e) {
                try {
                    Invite invite = server.getChannels().get(0).createInviteBuilder().create().get();
                    builder.append(invite.getUrl().toString()).append("\n");
                } catch (Exception e1) {
                    builder.append("No invite :(\n");
                }
            }
        }
        event.getMessage().reply(
                "```\n" +
                        builder +
                        "```"
        );
    }

}
