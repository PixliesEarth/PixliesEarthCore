package eu.pixliesearth.discord.commands;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.discord.DiscordCommand;
import eu.pixliesearth.discord.DiscordIngameRank;
import org.bson.Document;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.event.message.MessageCreateEvent;

public class DiscordLink extends DiscordCommand {

    public DiscordLink() {
        super("link");
    }

    public void run(MessageCreateEvent event) {
        String[] split = event.getMessageContent().split(" ");
        event.deleteMessage();
        if (split.length == 1) {
            event.getChannel().sendMessage("<@" + event.getMessageAuthor().getIdAsString() + ">, you have to give me a code so I can verify your account.");
            return;
        }
        Document user = new Document("discord", event.getMessageAuthor().getIdAsString());
        Document found = Main.getPlayerCollection().find(user).first();
        if (found != null) {
            event.getChannel().sendMessage("<@" + event.getMessageAuthor().getIdAsString() + ">, this discord account is already linked to an in-game account.");
            return;
        }
        if (Main.getInstance().getUtilLists().discordcodes.containsKey(split[1])) {
            Profile profile = Main.getInstance().getProfile(Main.getInstance().getUtilLists().discordcodes.get(split[1]));
            profile.setDiscord(event.getMessageAuthor().getIdAsString());
            profile.backup();
            event.getServer().get().addRoleToUser(event.getMessageAuthor().asUser().get(), event.getServer().get().getRoleById("709463355529887854").get());
            Main.getInstance().getUtilLists().discordcodes.remove(split[1]);
            Role rank = api.getServerById("589958750866112512").get().getRoleById(DiscordIngameRank.groupRoleMap().get(profile.getRank().getName())).get();
            rank.addUser(event.getMessageAuthor().asUser().get());
            event.getChannel().sendMessage("<@" + event.getMessageAuthor().getIdAsString() + ">, your account successfully got verified.");
        } else {
            event.getChannel().sendMessage("<@" + event.getMessageAuthor().getIdAsString() + ">, that code is invalid.");
        }
    }

}
